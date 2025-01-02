package dev.tomr.hcloud.service.server;

import dev.tomr.hcloud.HetznerCloud;
import dev.tomr.hcloud.http.HetznerCloudHttpClient;
import dev.tomr.hcloud.http.converter.ServerConverterUtil;
import dev.tomr.hcloud.http.model.Action;
import dev.tomr.hcloud.http.model.ActionWrapper;
import dev.tomr.hcloud.http.model.ServerDTO;
import dev.tomr.hcloud.http.model.ServerDTOList;
import dev.tomr.hcloud.resources.server.Server;
import dev.tomr.hcloud.service.ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static dev.tomr.hcloud.http.RequestVerb.*;
import static dev.tomr.hcloud.service.action.Action.*;

public class ServerService {
    protected static final Logger logger = LogManager.getLogger();

    private final HetznerCloudHttpClient client = HetznerCloudHttpClient.getInstance();
    private final ServiceManager serviceManager;

    private final ConcurrentHashMap<String, Object> updatedFields = new ConcurrentHashMap<>();
    private Server updatedServer;
    private CompletableFuture<Void> updatedServerFuture;

    private Map<Date, Server> remoteServers = new HashMap<>();
    private Date lastFullRefresh;

    /**
     * Creates a new {@code ServerService} instance
     */
    public ServerService() {
        this.serviceManager = HetznerCloud.getInstance().getServiceManager();
    }

    public ServerService(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    public void forceRefreshServersCache() {
        if (HetznerCloud.getInstance().hasApiKey()) {
            updateAllRemoteServers();
        } else {
            logger.warn("No API Key supplied, not refreshing servers. Consider refreshing again, when you have a key!");
        }
    }

    /**
     * Send a PUT regarding an update to a Server's name or label. This is used internally, and to prevent desync shouldn't be called directly.
     * @param field Name of the field that's changed (name/labels)
     * @param value New value for that field
     * @param server Related {@code Server} Object
     */
    public void serverNameOrLabelUpdate(String field, Object value, Server server) {
        updatedFields.put(field, value);
        updatedServer = server;
        if (updatedServerFuture == null) {
            List<String> hostAndKey = HetznerCloud.getInstance().getHttpDetails();
            updatedServerFuture = scheduleHttpRequest(hostAndKey.get(0), hostAndKey.get(1));
            updatedServerFuture.thenRun(() -> {
                logger.info("Server updated");
                updatedServer = null;
                updatedFields.clear();
                updatedServerFuture = null;
                serviceManager.closeExecutor();
            }).exceptionally((e) -> {
                logger.error(e);
                logger.warn("Server update failed");
                logger.warn("This can be ignored if you cancelled the request");
                logger.warn("It's likely that the Server object is now out of sync. Forcing a refresh");
                // todo implement force refresh get
                updatedServer = null;
                updatedFields.clear();
                updatedServerFuture = null;
                serviceManager.closeExecutor();
                return null;
            });
        }
    }

    /**
     * Cancel any requests waiting to be sent to the Hetzner API. Running this could cause a desync between the local and remote objects, so will force a GET for the targeted Server
     */
    public void cancelServerNameOrLabelUpdate() {
        logger.info("Cancelling server name/label update");
        updatedFields.clear();
    }

    public void deleteServerFromHetzner(Server server) {
        List<String> hostAndKey = HetznerCloud.getInstance().getHttpDetails();
        String httpUrl = String.format("%sservers/%d", hostAndKey.get(0), server.getId());
        AtomicReference<String> exceptionMsg = new AtomicReference<>();
        try {
            Action action = client.sendHttpRequest(ActionWrapper.class, httpUrl, DELETE, hostAndKey.get(1)).getAction();
            CompletableFuture<Action> completedActionFuture = serviceManager.getActionService().waitForActionToComplete(action).thenApplyAsync((completedAction) -> {
                if (completedAction == null) {
                    throw new NullPointerException();
                }
                logger.info("Server confirmed deleted at {}", completedAction.getFinished());
                return completedAction;
            }).exceptionally((e) -> {
                logger.error("Server delete failed");
                logger.error(e.getMessage());
                exceptionMsg.set(e.getMessage());
                return null;
            });
            if (completedActionFuture.get() == null) {
                throw new RuntimeException(exceptionMsg.get());
            }
        } catch (Exception e) {
            logger.error("Failed to delete the Server");
            throw new RuntimeException(e);
        }
    }

    public void shutdownServer(Server server) {
        sendServerAction(server, SHUTDOWN);
    }

    public void powerOffServer(Server server) {
        sendServerAction(server, POWEROFF);
    }

    public void powerOnServer(Server server) {
        sendServerAction(server, POWERON);
    }

    public void rebootServer(Server server) {
        sendServerAction(server, REBOOT);
    }

    public void resetServer(Server server) {
        sendServerAction(server, RESET);
    }

    private void sendServerAction(Server server, dev.tomr.hcloud.service.action.Action givenAction) {
        List<String> hostAndKey = HetznerCloud.getInstance().getHttpDetails();
        String httpUrl = String.format("%sservers/%d/actions/%s", hostAndKey.get(0), server.getId(), givenAction.path);
        AtomicReference<String> exceptionMsg = new AtomicReference<>();
        try {
            Action action = client.sendHttpRequest(ActionWrapper.class, httpUrl, POST, hostAndKey.get(1), "").getAction();
            CompletableFuture<Action> completedActionFuture = serviceManager.getActionService().waitForActionToComplete(action).thenApplyAsync((completedAction) -> {
                if (completedAction == null) {
                    throw new NullPointerException();
                }
                logger.info("Server {} at {}", givenAction.toString(), completedAction.getFinished());
                return completedAction;
            }).exceptionally((e) -> {
                logger.error("Server {} failed", givenAction.toString());
                logger.error(e.getMessage());
                exceptionMsg.set(e.getMessage());
                return null;
            });
            if (completedActionFuture.get() == null) {
                throw new RuntimeException(exceptionMsg.get());
            }
        } catch (Exception e) {
            logger.error("Failed to {} the Server", givenAction.toString());
            throw new RuntimeException(e);
        }
    }

    private void updateAllRemoteServers() {
        Map<Date, Server> newServerMap = new HashMap<>();
        List<String> hostAndKey = HetznerCloud.getInstance().getHttpDetails();
        String httpUrl = String.format("%sservers", hostAndKey.get(0));
        ServerDTOList serverDTOList = null;
        try {
            serverDTOList = client.sendHttpRequest(ServerDTOList.class, httpUrl, GET, hostAndKey.get(1));
        } catch (IOException | InterruptedException | IllegalAccessException e) {
            logger.error("Failed to refresh all remote servers!");
            throw new RuntimeException(e);
        }
        serverDTOList.getServers().forEach(serverDTO -> {
            newServerMap.put(Date.from(Instant.now()), ServerConverterUtil.transformServerDTOToServer(serverDTO));
        });
        remoteServers = newServerMap;
        lastFullRefresh = Date.from(Instant.now());
    }

    /**
     * Get a server, will refresh the cache if the cache is out of date
     * @param id ID of the wanted server
     * @return Server object from Hetzner
     */
    public Server getServer(Integer id) {
        verifyCacheUpToDate();
        for (var entry : remoteServers.entrySet()) {
            if (entry.getValue().getId().equals(id)) {
                return entry.getValue();
            }
        }
        logger.warn("Server with id {} not found", id);
        return null;
    }

    private CompletableFuture<Void> scheduleHttpRequest(String host, String apiKey) {
        return CompletableFuture.runAsync(() -> {
            try {
                // sleep to allow multiple field changes
                TimeUnit.MILLISECONDS.sleep(500);
                if (!updatedFields.isEmpty()) {
                    AtomicReference<String> ref = new AtomicReference<>();
                    ServerDTO serverDTO = ServerConverterUtil.transformServerToServerDTO(updatedServer);
                    String info = "Sending an update for the following fields: ";
                    ref.set(info);
                    updatedFields.forEach((key, value) -> {
                        String i = ref.get();
                        ref.set(i + "   " + key + " with a value of " + value);
                    });
                    removeUnchangedFields(serverDTO);
                    info = ref.get();
                    logger.info(info);
                    String endpoint = host + "servers/" + updatedServer.getId();
                    client.sendHttpRequest(ServerDTO.class, endpoint, PUT, apiKey, HetznerCloud.getObjectMapper().writeValueAsString(serverDTO));
                } else {
                    throw new RuntimeException("No updated values??");
                }
            } catch (InterruptedException | IOException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }, serviceManager.getExecutor());
    }

    private void removeUnchangedFields(ServerDTO serverDTO) {
        if (!updatedFields.containsKey("labels")) {
            serverDTO.setLabels(null);
        }
        if (!updatedFields.containsKey("name")) {
            serverDTO.setName(null);
        }
    }

    private void verifyCacheUpToDate() {
        if (lastFullRefresh == null || lastFullRefresh.before(Date.from(Instant.now().minus(10, ChronoUnit.MINUTES)))) {
            forceRefreshServersCache();
        }
    }
}

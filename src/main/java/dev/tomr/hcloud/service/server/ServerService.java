package dev.tomr.hcloud.service.server;

import dev.tomr.hcloud.HetznerCloud;
import dev.tomr.hcloud.http.HetznerCloudHttpClient;
import dev.tomr.hcloud.http.converter.ServerConverterUtil;
import dev.tomr.hcloud.http.model.ServerDTO;
import dev.tomr.hcloud.resources.server.Server;
import dev.tomr.hcloud.service.ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static dev.tomr.hcloud.http.RequestVerb.PUT;

public class ServerService {
    protected static final Logger logger = LogManager.getLogger();

    private final HetznerCloudHttpClient client;
    private final ServiceManager serviceManager;

    private final ConcurrentHashMap<String, Object> updatedFields = new ConcurrentHashMap<>();
    private Server updatedServer;
    private CompletableFuture<Void> updatedServerFuture;

    /**
     * Creates a new {@code ServerService} instance
     */
    public ServerService() {
        client = HetznerCloudHttpClient.getInstance();
        serviceManager = HetznerCloud.getServiceManager();
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
                    String endpoint = host + "server/" + updatedServer.getId();
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
}

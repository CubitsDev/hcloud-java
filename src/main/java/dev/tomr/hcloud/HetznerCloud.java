package dev.tomr.hcloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tomr.hcloud.listener.ListenerManager;
import dev.tomr.hcloud.resources.server.Server;
import dev.tomr.hcloud.service.ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Entrypoint to interact with the Hetzner Cloud API, via hcloud-java. Construct with an available construtor, alternatively call .getInstance()
 */
public class HetznerCloud {
    protected static final Logger logger = LogManager.getLogger();

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String HETZNER_CLOUD_HOST = "https://api.hetzner.cloud/v1/";

    private static HetznerCloud instance;

    private final ListenerManager listenerManager = ListenerManager.getInstance();
    private final ServiceManager serviceManager = ServiceManager.getInstance();

    private String apiKey;
    private final String host;

    /**
     * Constructs a new {@code HetznerCloud} Instance with the given API key.
     * @param apiKey API Key to use from the Hetzner Cloud Dashboard
     */
    public HetznerCloud(String apiKey) {
        this.apiKey = apiKey;
        this.host = HETZNER_CLOUD_HOST;
        instance = this;
    }

    /**
     * Constructs a new {@code HetznerCloud} Instance with the given API key and a different host. This is mainly a testing constructor, it's unlikely you should call this.
     * @param apiKey API Key to use from the Hetzner Cloud Dashboard
     * @param host Different host to use in REST calls. Should start with http(s)://
     */
    public HetznerCloud(String apiKey, String host) {
        this.apiKey = apiKey;
        this.host = host;
        instance = this;
    }

    /**
     * Get the current instance of {@code HetznerCloud} to interact with the API. If an instance doesn't exist, this will create a new instance.
     * @return The instance of {@code HetznerCloud} being used
     */
    public static HetznerCloud getInstance() {
        if (instance == null) {
            instance = new HetznerCloud(null);
            logger.warn("Hetzner Cloud API Key needs to be set");
        }
        return instance;
    }

    /**
     * Set the API key for REST calls. Necessary if you create the instance with the .getInstance() method
     * @param apiKey API Key to use from the Hetzner Cloud Dashboard
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Get the shared Jackson {@code ObjectMapper}
     * @return The shared {@code ObjectMapper} instance
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * Get the internal {@code ListenerManager}. End users do not need to interact with the {@code ListenerManager}
     * @return The {@code ListenerManager} Instance
     */
    public ListenerManager getListenerManager() {
        return listenerManager;
    }

    /**
     * Get the internal {@code ServiceManager}. End users do not need to interact with the {@code ServiceManager}
     * @return The {@code ServiceManager} Instance
     */
    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    /**
     * Get the HTTP host and Hetzner Cloud API Key. End users do not need to interact with this.
     * @return A {@code List<String>}, with the HTTP host at 0, and the API key at 1.
     */
    public List<String> getHttpDetails() {
        return List.of(host, apiKey);
    }

    /**
     * Whether we have an API Key supplied
     * @return true if one is present, false if not
     */
    public boolean hasApiKey() {
        return apiKey != null && !apiKey.isEmpty();
    }

    /**
     * Get a Hetzner Server Instance from the local cache
     * @param id ID of the server
     * @return A server object from the local cache
     */
    public Server getServer(Integer id) {
        return serviceManager.getServerService().getServer(id);
    }
}

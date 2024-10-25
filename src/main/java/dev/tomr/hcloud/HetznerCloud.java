package dev.tomr.hcloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tomr.hcloud.listener.ListenerManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HetznerCloud {
    protected static final Logger logger = LogManager.getLogger();

    private static final ListenerManager listenerManager = ListenerManager.getInstance();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static String HETZNER_CLOUD_HOST = "https://api.hetzner.cloud/v1/";

    private static HetznerCloud instance;

    private String apiKey;
    private final String host;

    public HetznerCloud(String apiKey) {
        this.apiKey = apiKey;
        this.host = HETZNER_CLOUD_HOST;
        instance = this;
    }

    public HetznerCloud(String apiKey, String host) {
        this.apiKey = apiKey;
        this.host = host;
        instance = this;
    }

    public static HetznerCloud getInstance() {
        if (instance == null) {
            instance = new HetznerCloud(null);
            logger.warn("Hetzner Cloud API Key needs to be set");
        }
        return instance;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static ListenerManager getListenerManager() {
        return listenerManager;
    }

}

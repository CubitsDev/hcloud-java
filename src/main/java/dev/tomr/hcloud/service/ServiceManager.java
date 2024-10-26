package dev.tomr.hcloud.service;

import dev.tomr.hcloud.service.server.ServerService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceManager {
    private static ServiceManager instance;

    private final ServerService serverService;

    private ExecutorService executor;

    private ServiceManager() {
        this.serverService = new ServerService();
        instance = this;
    }

    public static ServiceManager getInstance() {
        if (instance == null) {
           new ServiceManager();
        }
        return instance;
    }

    public ServerService getServerService() {
        return serverService;
    }

    public ExecutorService getExecutor() {
        if (executor == null) {
            executor = Executors.newFixedThreadPool(4);
        }
        return executor;
    }

    public void closeExecutor() {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }
    }
}

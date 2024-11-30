package dev.tomr.hcloud.service;

import dev.tomr.hcloud.service.action.ActionService;
import dev.tomr.hcloud.service.server.ServerService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceManager {
    private static ServiceManager instance;

    private final ServerService serverService;
    private final ActionService actionService;

    private ExecutorService executor;

    private ServiceManager() {
        instance = this;
        this.serverService = new ServerService(this);
        this.actionService = new ActionService();
    }

    /**
     * Get the ServiceManager Instance (Singleton)
     * @return The {@code ServiceManager} Instance
     */
    public static ServiceManager getInstance() {
        if (instance == null) {
           new ServiceManager();
        }
        return instance;
    }

    /**
     * Get ServerService Instance
     * @return the {@code ServerService} instance
     */
    public ServerService getServerService() {
        return serverService;
    }

    /**
     * Get ActionService Instance
     * @return the {@code ActionService} instance
     */
    public ActionService getActionService() {
        return actionService;
    }

    /**
     * Get an Executor for threaded tasks
     * @return The Existing or a new {@code ExecutorService}
     */
    public ExecutorService getExecutor() {
        if (executor == null) {
            executor = Executors.newFixedThreadPool(4);
        }
        return executor;
    }

    /**
     * Close the current Executor and remove it from memory
     */
    public void closeExecutor() {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }
    }
}

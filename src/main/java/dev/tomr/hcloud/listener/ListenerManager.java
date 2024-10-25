package dev.tomr.hcloud.listener;

public class ListenerManager {
    public static ListenerManager instance;

    private final ServerChangeListener serverChangeListener;

    private ListenerManager() {
        this.serverChangeListener = new ServerChangeListener();
        instance = this;
    }

    public static ListenerManager getInstance() {
        if (instance == null) {
            instance = new ListenerManager();
        }
        return instance;
    }

    public ServerChangeListener getServerChangeListener() {
        return serverChangeListener;
    }
}

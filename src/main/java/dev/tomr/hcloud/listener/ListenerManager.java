package dev.tomr.hcloud.listener;

public class ListenerManager {
    public static ListenerManager instance;

    private final ServerChangeListener serverChangeListener;

    private ListenerManager() {
        this.serverChangeListener = new ServerChangeListener();
        instance = this;
    }

    /**
     * Gets the ListenerManager Instance, creates one if it doesn't exist
     * @return {@code ListenerManager} in use
     */
    public static ListenerManager getInstance() {
        if (instance == null) {
            instance = new ListenerManager();
        }
        return instance;
    }

    /**
     * Gets the associated Server Change Listener
     * @return {@code ServerChangeListener} the ListenerManager's ServerChangeListener
     */
    public ServerChangeListener getServerChangeListener() {
        return serverChangeListener;
    }
}

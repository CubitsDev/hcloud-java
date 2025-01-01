package dev.tomr.hcloud.listener;

import dev.tomr.hcloud.HetznerCloud;
import dev.tomr.hcloud.resources.server.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ServerChangeListener implements PropertyChangeListener {
    protected static final Logger logger = LogManager.getLogger();

    /**
     * Handles the Change sent by a {@code Server} object
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Server server = (Server) evt.getSource();
        if (evt.getPropertyName().equals("delete")) {
            logger.warn("Server delete has been called. Instructing Hetzner to delete");
            HetznerCloud.getInstance().getServiceManager().getServerService().deleteServerFromHetzner(server);
        } else if (evt.getPropertyName().equals("shutdown")) {
            logger.info("Server shutdown has been called. Instructing Hetzner to shut the server down");
            HetznerCloud.getInstance().getServiceManager().getServerService().shutdownServer(server);
        } else {
            logger.info("Server changed: " + evt.getPropertyName());
            logger.info("Server: " + evt.getOldValue() + " -> " + evt.getNewValue());
            HetznerCloud.getInstance().getServiceManager().getServerService().serverNameOrLabelUpdate(evt.getPropertyName(), evt.getNewValue(), server);
        }
    }
}

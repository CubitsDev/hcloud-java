package dev.tomr.hcloud.listener;

import dev.tomr.hcloud.HetznerCloud;
import dev.tomr.hcloud.resources.common.Protection;
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
        String propertyName = evt.getPropertyName();

        switch (propertyName) {
            case "delete" -> {
                logger.warn("Server delete has been called. Instructing Hetzner to delete");
                HetznerCloud.getInstance().getServiceManager().getServerService().deleteServerFromHetzner(server);
            }
            case "shutdown" -> {
                logger.info("Server shutdown has been called. Instructing Hetzner to shut the server down");
                HetznerCloud.getInstance().getServiceManager().getServerService().shutdownServer(server);
            }
            case "poweroff" -> {
                logger.info("Server poweroff has been called. Instructing Hetzner to power down the server");
                logger.warn("This is a potentially destructive action!");
                HetznerCloud.getInstance().getServiceManager().getServerService().powerOffServer(server);
            }
            case "poweron" -> {
                logger.info("Server power on has been called. Instructing Hetzner to power up the server");
                HetznerCloud.getInstance().getServiceManager().getServerService().powerOnServer(server);
            }
            case "reboot" -> {
                logger.info("Server reboot has been called. Instructing Hetzner to reboot the server");
                HetznerCloud.getInstance().getServiceManager().getServerService().rebootServer(server);
            }
            case "reset" -> {
                logger.info("Server reset has been called. Instructing Hetzner to reset the server");
                logger.warn("This is a potentially destructive action!");
                HetznerCloud.getInstance().getServiceManager().getServerService().resetServer(server);
            }
            case "addToPlacementGroup" -> {
                logger.info("Add Server to placement group has been called. Instructing Hetzner to add the server to the given placement group {}", evt.getNewValue());
                HetznerCloud.getInstance().getServiceManager().getServerService().addServerToPlacementGroup(server, (Integer) evt.getNewValue());
            }
            case "removeFromPlacementGroup" -> {
                logger.info("Remove Server from placement group has been called. Instructing Hetzner to remove the server from a placement group");
                HetznerCloud.getInstance().getServiceManager().getServerService().removeServerFromPlacementGroup(server);
            }
            case "changeServerProtection" -> {
                logger.info("Change Server protection has been called. Instructin Hetzner to update the server protection to {}", evt.getNewValue().toString());
                HetznerCloud.getInstance().getServiceManager().getServerService().changeServerProtection(server, (Protection) evt.getNewValue());
            }
            default -> {
                logger.info("Server changed: {}", evt.getPropertyName());
                logger.info("Server: {} -> {}", evt.getOldValue(), evt.getNewValue());
                HetznerCloud.getInstance().getServiceManager().getServerService().serverNameOrLabelUpdate(evt.getPropertyName(), evt.getNewValue(), server);
            }
        }
    }
}

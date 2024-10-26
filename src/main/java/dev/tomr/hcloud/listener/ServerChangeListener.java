package dev.tomr.hcloud.listener;

import dev.tomr.hcloud.HetznerCloud;
import dev.tomr.hcloud.resources.server.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ServerChangeListener implements PropertyChangeListener {
    protected static final Logger logger = LogManager.getLogger();

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Server server = (Server) evt.getSource();
        //todo handle incoming changes to send API request
        logger.info("Server changed: " + evt.getPropertyName());
        logger.info("Server: " + evt.getOldValue() + " -> " + evt.getNewValue());
        HetznerCloud.getServiceManager().getServerService().serverNameOrLabelUpdate(evt.getPropertyName(), evt.getNewValue(), server);
    }
}

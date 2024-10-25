package dev.tomr.hcloud.resources.server;

import dev.tomr.hcloud.HetznerCloud;
import dev.tomr.hcloud.resources.common.*;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Server implements Serializable {
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private Integer id;
    private String backupWindow;
    private String created;
    private Datacenter datacenter;
    private Image image;
    private Long includedTraffic;
    private Long ingoingTraffic;
    private Long outgoingTraffic;
    private Iso iso;
    private Map<String, String> labels;
    private List<Object> loadBalancers; // need to figure this out
    private boolean locked;
    private String name;
    private PlacementGroup placementGroup;
    private Long primaryDiskSize;
    //todo change this to an actual class
    private List<Object> privateNet;
    private Protection protection;
    private Object publicNet;
    private boolean rescueEnabled;
    private ServerType serverType;
    private String status;
    private List<Integer> volumes;

    public Server() {
        setupPropertyChangeListener();
    }

    public Server(Integer id, String backupWindow, String created, Datacenter datacenter, Image image, Long includedTraffic, Long ingoingTraffic, Long outgoingTraffic, Iso iso, Map<String, String> labels, List<Object> loadBalancers, boolean locked, String name, PlacementGroup placementGroup, Long primaryDiskSize, List<Object> privateNet, Protection protection, Object publicNet, boolean rescueEnabled, ServerType serverType, String status, List<Integer> volumes) {
        this.id = id;
        this.backupWindow = backupWindow;
        this.created = created;
        this.datacenter = datacenter;
        this.image = image;
        this.includedTraffic = includedTraffic;
        this.ingoingTraffic = ingoingTraffic;
        this.outgoingTraffic = outgoingTraffic;
        this.iso = iso;
        this.labels = labels;
        this.loadBalancers = loadBalancers;
        this.locked = locked;
        this.name = name;
        this.placementGroup = placementGroup;
        this.primaryDiskSize = primaryDiskSize;
        this.privateNet = privateNet;
        this.protection = protection;
        this.publicNet = publicNet;
        this.rescueEnabled = rescueEnabled;
        this.serverType = serverType;
        this.status = status;
        this.volumes = volumes;
        setupPropertyChangeListener();
    }

    private void setupPropertyChangeListener() {
        propertyChangeSupport.addPropertyChangeListener(HetznerCloud.getListenerManager().getServerChangeListener());
    }


    // These are the current setters that will send an API request (PUT /servers) when actions begin to be added, they will also likely be triggered by setters

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        propertyChangeSupport.firePropertyChange("labels", this.labels, labels);
        this.labels = labels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        propertyChangeSupport.firePropertyChange("name", this.name, name);
        this.name = name;
    }

    // Getter only (no support to change in API)


    public Integer getId() {
        return id;
    }

    public String getBackupWindow() {
        return backupWindow;
    }

    public String getCreated() {
        return created;
    }

    public Datacenter getDatacenter() {
        return datacenter;
    }

    public Image getImage() {
        return image;
    }

    public Long getIncludedTraffic() {
        return includedTraffic;
    }

    public Long getIngoingTraffic() {
        return ingoingTraffic;
    }

    public Long getOutgoingTraffic() {
        return outgoingTraffic;
    }

    public Iso getIso() {
        return iso;
    }

    public List<Object> getLoadBalancers() {
        return loadBalancers;
    }

    public boolean isLocked() {
        return locked;
    }

    public PlacementGroup getPlacementGroup() {
        return placementGroup;
    }

    public Long getPrimaryDiskSize() {
        return primaryDiskSize;
    }

    public List<Object> getPrivateNet() {
        return privateNet;
    }

    public Protection getProtection() {
        return protection;
    }

    public Object getPublicNet() {
        return publicNet;
    }

    public boolean isRescueEnabled() {
        return rescueEnabled;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public String getStatus() {
        return status;
    }

    public List<Integer> getVolumes() {
        return volumes;
    }
}

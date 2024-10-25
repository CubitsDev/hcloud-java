package dev.tomr.hcloud.http.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.tomr.hcloud.http.HetznerJsonObject;
import dev.tomr.hcloud.resources.common.*;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerDTO extends HetznerJsonObject {
    private Integer id;
    @JsonProperty("backup_window")
    private String backupWindow;
    private String created;
    private Datacenter datacenter;
    private Image image;
    @JsonProperty("included_traffic")
    private Long includedTraffic;
    @JsonProperty("ongoing_traffic")
    private Long ingoingTraffic;
    @JsonProperty("outgoing_traffic")
    private Long outgoingTraffic;
    private Iso iso;
    private Map<String, String> labels;
    @JsonProperty("load_balancers")
    private List<Object> loadBalancers; // need to figure this out
    private boolean locked;
    private String name;
    @JsonProperty("placement_group")
    private PlacementGroup placementGroup;
    @JsonProperty("primary_disk_size")
    private Long primaryDiskSize;
    //todo change these to an actual class
    @JsonProperty("private_net")
    private List<Object> privateNet;
    private Protection protection;
    @JsonProperty("public_net")
    private Object publicNet;
    @JsonProperty("rescue_enabled")
    private boolean rescueEnabled;
    @JsonProperty("server_type")
    private ServerType serverType;
    private String status;
    private List<Integer> volumes;

    public ServerDTO(Integer id, String backupWindow, String created, Datacenter datacenter, Image image, Long includedTraffic, Long ingoingTraffic, Long outgoingTraffic, Iso iso, Map<String, String> labels, List<Object> loadBalancers, boolean locked, String name, PlacementGroup placementGroup, Long primaryDiskSize, List<Object> privateNet, Protection protection, Object publicNet, boolean rescueEnabled, ServerType serverType, String status, List<Integer> volumes) {
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
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBackupWindow() {
        return backupWindow;
    }

    public void setBackupWindow(String backupWindow) {
        this.backupWindow = backupWindow;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Datacenter getDatacenter() {
        return datacenter;
    }

    public void setDatacenter(Datacenter datacenter) {
        this.datacenter = datacenter;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Long getIncludedTraffic() {
        return includedTraffic;
    }

    public void setIncludedTraffic(Long includedTraffic) {
        this.includedTraffic = includedTraffic;
    }

    public Long getIngoingTraffic() {
        return ingoingTraffic;
    }

    public void setIngoingTraffic(Long ingoingTraffic) {
        this.ingoingTraffic = ingoingTraffic;
    }

    public Long getOutgoingTraffic() {
        return outgoingTraffic;
    }

    public void setOutgoingTraffic(Long outgoingTraffic) {
        this.outgoingTraffic = outgoingTraffic;
    }

    public Iso getIso() {
        return iso;
    }

    public void setIso(Iso iso) {
        this.iso = iso;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public List<Object> getLoadBalancers() {
        return loadBalancers;
    }

    public void setLoadBalancers(List<Object> loadBalancers) {
        this.loadBalancers = loadBalancers;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlacementGroup getPlacementGroup() {
        return placementGroup;
    }

    public void setPlacementGroup(PlacementGroup placementGroup) {
        this.placementGroup = placementGroup;
    }

    public Long getPrimaryDiskSize() {
        return primaryDiskSize;
    }

    public void setPrimaryDiskSize(Long primaryDiskSize) {
        this.primaryDiskSize = primaryDiskSize;
    }

    public List<Object> getPrivateNet() {
        return privateNet;
    }

    public void setPrivateNet(List<Object> privateNet) {
        this.privateNet = privateNet;
    }

    public Protection getProtection() {
        return protection;
    }

    public void setProtection(Protection protection) {
        this.protection = protection;
    }

    public Object getPublicNet() {
        return publicNet;
    }

    public void setPublicNet(Object publicNet) {
        this.publicNet = publicNet;
    }

    public boolean isRescueEnabled() {
        return rescueEnabled;
    }

    public void setRescueEnabled(boolean rescueEnabled) {
        this.rescueEnabled = rescueEnabled;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Integer> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Integer> volumes) {
        this.volumes = volumes;
    }

    @Override
    public String toString() {
        return "ServerDTO{" +
                "id=" + id +
                ", backupWindow='" + backupWindow + '\'' +
                ", created='" + created + '\'' +
                ", datacenter=" + datacenter +
                ", image=" + image +
                ", includedTraffic=" + includedTraffic +
                ", ingoingTraffic=" + ingoingTraffic +
                ", outgoingTraffic=" + outgoingTraffic +
                ", iso=" + iso +
                ", labels=" + labels +
                ", loadBalancers=" + loadBalancers +
                ", locked=" + locked +
                ", name='" + name + '\'' +
                ", placementGroup=" + placementGroup +
                ", primaryDiskSize=" + primaryDiskSize +
                ", privateNet=" + privateNet +
                ", protection=" + protection +
                ", publicNet=" + publicNet +
                ", rescueEnabled=" + rescueEnabled +
                ", serverType=" + serverType +
                ", status='" + status + '\'' +
                ", volumes=" + volumes +
                '}';
    }

    public static class Builder {
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
        private List<Object> loadBalancers;
        private boolean locked;
        private String name;
        private PlacementGroup placementGroup;
        private Long primaryDiskSize;
        private List<Object> privateNet;
        private Protection protection;
        private Object publicNet;
        private boolean rescueEnabled;
        private ServerType serverType;
        private String status;
        private List<Integer> volumes;

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder backupWindow(String backupWindow) {
            this.backupWindow = backupWindow;
            return this;
        }

        public Builder created(String created) {
            this.created = created;
            return this;
        }

        public Builder datacenter(Datacenter datacenter) {
            this.datacenter = datacenter;
            return this;
        }

        public Builder image(Image image) {
            this.image = image;
            return this;
        }

        public Builder includedTraffic(Long includedTraffic) {
            this.includedTraffic = includedTraffic;
            return this;
        }

        public Builder ingoingTraffic(Long ingoingTraffic) {
            this.ingoingTraffic = ingoingTraffic;
            return this;
        }

        public Builder outgoingTraffic(Long outgoingTraffic) {
            this.outgoingTraffic = outgoingTraffic;
            return this;
        }

        public Builder iso(Iso iso) {
            this.iso = iso;
            return this;
        }

        public Builder labels(Map<String, String> labels) {
            this.labels = labels;
            return this;
        }

        public Builder loadBalancers(List<Object> loadBalancers) {
            this.loadBalancers = loadBalancers;
            return this;
        }

        public Builder locked(boolean locked) {
            this.locked = locked;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder placementGroup(PlacementGroup placementGroup) {
            this.placementGroup = placementGroup;
            return this;
        }

        public Builder primaryDiskSize(Long primaryDiskSize) {
            this.primaryDiskSize = primaryDiskSize;
            return this;
        }

        public Builder privateNet(List<Object> privateNet) {
            this.privateNet = privateNet;
            return this;
        }

        public Builder protection(Protection protection) {
            this.protection = protection;
            return this;
        }

        public Builder publicNet(Object publicNet) {
            this.publicNet = publicNet;
            return this;
        }

        public Builder rescueEnabled(boolean rescueEnabled) {
            this.rescueEnabled = rescueEnabled;
            return this;
        }

        public Builder serverType(ServerType serverType) {
            this.serverType = serverType;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder volumes(List<Integer> volumes) {
            this.volumes = volumes;
            return this;
        }

        public ServerDTO build() {
            return new ServerDTO(id, backupWindow, created, datacenter, image, includedTraffic, ingoingTraffic, outgoingTraffic, iso, labels, loadBalancers, locked, name, placementGroup, primaryDiskSize, privateNet, protection, publicNet, rescueEnabled, serverType, status, volumes);
        }
    }
}

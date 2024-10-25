package dev.tomr.hcloud.resources.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;

public class Image{
    private String architecture;
    @JsonProperty("bound_to")
    private Object boundTo; // not sure what this is yet
    private Date created;
    @JsonProperty("created_from")
    private CreatedFrom createdFrom;
    private Object deleted;
    private Date deprecated;
    private String description;
    @JsonProperty("disk_size")
    private Integer diskSize;
    private Integer id;
    @JsonProperty("image_size")
    private double imageSize;
    private Map<String, String> labels;
    private String name;
    @JsonProperty("os_flavor")
    private String osFlavor;
    @JsonProperty("os_version")
    private String osVersion;
    private Protection protection;
    @JsonProperty("rapid_deploy")
    private boolean rapidDeploy;
    private String status;
    private String type;

    public Image(String architecture, Object boundTo, Date created, CreatedFrom createdFrom, Object deleted, Date deprecated, String description, Integer diskSize, Integer id, double imageSize, Map<String, String> labels, String name, String osFlavor, String osVersion, Protection protection, boolean rapidDeploy, String status, String type) {
        this.architecture = architecture;
        this.boundTo = boundTo;
        this.created = created;
        this.createdFrom = createdFrom;
        this.deleted = deleted;
        this.deprecated = deprecated;
        this.description = description;
        this.diskSize = diskSize;
        this.id = id;
        this.imageSize = imageSize;
        this.labels = labels;
        this.name = name;
        this.osFlavor = osFlavor;
        this.osVersion = osVersion;
        this.protection = protection;
        this.rapidDeploy = rapidDeploy;
        this.status = status;
        this.type = type;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public Object getBoundTo() {
        return boundTo;
    }

    public void setBoundTo(Object boundTo) {
        this.boundTo = boundTo;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public CreatedFrom getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(CreatedFrom createdFrom) {
        this.createdFrom = createdFrom;
    }

    public Object getDeleted() {
        return deleted;
    }

    public void setDeleted(Object deleted) {
        this.deleted = deleted;
    }

    public Date getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Date deprecated) {
        this.deprecated = deprecated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(Integer diskSize) {
        this.diskSize = diskSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getImageSize() {
        return imageSize;
    }

    public void setImageSize(double imageSize) {
        this.imageSize = imageSize;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOsFlavor() {
        return osFlavor;
    }

    public void setOsFlavor(String osFlavor) {
        this.osFlavor = osFlavor;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public Protection getProtection() {
        return protection;
    }

    public void setProtection(Protection protection) {
        this.protection = protection;
    }

    public boolean isRapidDeploy() {
        return rapidDeploy;
    }

    public void setRapidDeploy(boolean rapidDeploy) {
        this.rapidDeploy = rapidDeploy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

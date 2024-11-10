package dev.tomr.hcloud.resources.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ServerType {
    private String architecture;
    private Integer cores;
    @JsonProperty("cpu_type")
    private String cpuType;
    private boolean deprecated;
    private Deprecation deprecation;
    private String description;
    private Integer disk;
    private Integer id;
    @JsonProperty("included_traffic")
    private Object includedTraffic;
    private Integer memory;
    private String name;
    private List<Price> prices;
    @JsonProperty("storage_type")
    private String storageType;

    public ServerType(String architecture, Integer cores, String cpuType, boolean deprecated, Deprecation deprecation, String description, Integer disk, Integer id, Object includedTraffic, Integer memory, String name, List<Price> prices, String storageType) {
        this.architecture = architecture;
        this.cores = cores;
        this.cpuType = cpuType;
        this.deprecated = deprecated;
        this.deprecation = deprecation;
        this.description = description;
        this.disk = disk;
        this.id = id;
        this.includedTraffic = includedTraffic;
        this.memory = memory;
        this.name = name;
        this.prices = prices;
        this.storageType = storageType;
    }

    public ServerType() {}

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public Integer getCores() {
        return cores;
    }

    public void setCores(Integer cores) {
        this.cores = cores;
    }

    public String getCpuType() {
        return cpuType;
    }

    public void setCpuType(String cpuType) {
        this.cpuType = cpuType;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    public Deprecation getDeprecation() {
        return deprecation;
    }

    public void setDeprecation(Deprecation deprecation) {
        this.deprecation = deprecation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDisk() {
        return disk;
    }

    public void setDisk(Integer disk) {
        this.disk = disk;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getIncludedTraffic() {
        return includedTraffic;
    }

    public void setIncludedTraffic(Object includedTraffic) {
        this.includedTraffic = includedTraffic;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }
}

package dev.tomr.hcloud.resources.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Datacenter{
    private String description;
    private Integer id;
    private Location location;
    private String name;
    @JsonProperty("server_types")
    private ServerTypes serverTypes;

    public Datacenter(String description, Integer id, Location location, String name, ServerTypes serverTypes) {
        this.description = description;
        this.id = id;
        this.location = location;
        this.name = name;
        this.serverTypes = serverTypes;
    }

    public Datacenter() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServerTypes getServerTypes() {
        return serverTypes;
    }

    public void setServerTypes(ServerTypes serverTypes) {
        this.serverTypes = serverTypes;
    }
}

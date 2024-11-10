package dev.tomr.hcloud.resources.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PlacementGroup {
    private Date created;
    private int id;
    private Map<String, String> labels;
    private String name;
    private List<Integer> servers;
    private String type;

    public PlacementGroup(Date created, int id, Map<String, String> labels, String name, List<Integer> servers, String type) {
        this.created = created;
        this.id = id;
        this.labels = labels;
        this.name = name;
        this.servers = servers;
        this.type = type;
    }

    public PlacementGroup() {
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Integer> getServers() {
        return servers;
    }

    public void setServers(List<Integer> servers) {
        this.servers = servers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

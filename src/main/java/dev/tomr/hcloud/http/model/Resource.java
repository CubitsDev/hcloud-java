package dev.tomr.hcloud.http.model;

public class Resource {
    private Integer id;
    private String type;

    public Resource(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Resource() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

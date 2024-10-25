package dev.tomr.hcloud.resources.common;

public class CreatedFrom{
    private int id;
    private String name;

    public CreatedFrom(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public CreatedFrom setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

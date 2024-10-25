package dev.tomr.hcloud.resources.common;

import java.util.Date;
import java.util.Map;

public class Iso{
    private String architecture;
    private Map<String, Date> deprecation;
    private String description;
    private int id;
    private String name;
    private String type;

    public Iso(String architecture, Map<String, Date> deprecation, String description, int id, String name, String type) {
        this.architecture = architecture;
        this.deprecation = deprecation;
        this.description = description;
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public Map<String, Date> getDeprecation() {
        return deprecation;
    }

    public void setDeprecation(Map<String, Date> deprecation) {
        this.deprecation = deprecation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

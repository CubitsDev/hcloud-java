package dev.tomr.hcloud.resources.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ServerTypes{
    private List<Integer> available;
    @JsonProperty("available_for_migration")
    private List<Integer> availableForMigration;
    private List<Integer> supported;

    public ServerTypes(List<Integer> available, List<Integer> availableForMigration, List<Integer> supported) {
        this.available = available;
        this.availableForMigration = availableForMigration;
        this.supported = supported;
    }

    public ServerTypes() {}

    public List<Integer> getAvailable() {
        return available;
    }

    public void setAvailable(List<Integer> available) {
        this.available = available;
    }

    public List<Integer> getAvailableForMigration() {
        return availableForMigration;
    }

    public void setAvailableForMigration(List<Integer> availableForMigration) {
        this.availableForMigration = availableForMigration;
    }

    public List<Integer> getSupported() {
        return supported;
    }

    public void setSupported(List<Integer> supported) {
        this.supported = supported;
    }
}

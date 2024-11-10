package dev.tomr.hcloud.resources.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Deprecation{
    private Date announced;
    @JsonProperty("unavailable_after")
    private Date unavailableAfter;

    public Deprecation(Date announced, Date unavailableAfter) {
        this.announced = announced;
        this.unavailableAfter = unavailableAfter;
    }

    public Deprecation() {
    }

    public Date getAnnounced() {
        return announced;
    }

    public void setAnnounced(Date announced) {
        this.announced = announced;
    }

    public Date getUnavailableAfter() {
        return unavailableAfter;
    }

    public void setUnavailableAfter(Date unavailableAfter) {
        this.unavailableAfter = unavailableAfter;
    }
}

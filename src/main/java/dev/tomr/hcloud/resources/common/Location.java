package dev.tomr.hcloud.resources.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location{
    private String city;
    private String country;
    private String description;
    private Integer id;
    private double latitude;
    private double longitude;
    private String name;
    @JsonProperty("network_zone")
    private String networkZone;

    public Location(String city, String country, String description, Integer id, double latitude, double longitude, String name, String networkZone) {
        this.city = city;
        this.country = country;
        this.description = description;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.networkZone = networkZone;
    }

    public Location() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetworkZone() {
        return networkZone;
    }

    public void setNetworkZone(String networkZone) {
        this.networkZone = networkZone;
    }
}
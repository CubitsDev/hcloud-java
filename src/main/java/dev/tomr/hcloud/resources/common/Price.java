package dev.tomr.hcloud.resources.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Price{
    @JsonProperty("included_traffic")
    private Integer includedTraffic;
    private String location;
    @JsonProperty("price_hourly")
    private PriceDetails priceHourly;
    @JsonProperty("price_monthly")
    private PriceDetails priceMonthly;
    @JsonProperty("price_per_tb_traffic")
    private PriceDetails pricePerTbTraffic;

    public Price(Integer includedTraffic, String location, PriceDetails priceHourly, PriceDetails priceMonthly, PriceDetails pricePerTbTraffic) {
        this.includedTraffic = includedTraffic;
        this.location = location;
        this.priceHourly = priceHourly;
        this.priceMonthly = priceMonthly;
        this.pricePerTbTraffic = pricePerTbTraffic;
    }

    public Integer getIncludedTraffic() {
        return includedTraffic;
    }

    public void setIncludedTraffic(Integer includedTraffic) {
        this.includedTraffic = includedTraffic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PriceDetails getPriceHourly() {
        return priceHourly;
    }

    public void setPriceHourly(PriceDetails priceHourly) {
        this.priceHourly = priceHourly;
    }

    public PriceDetails getPriceMonthly() {
        return priceMonthly;
    }

    public void setPriceMonthly(PriceDetails priceMonthly) {
        this.priceMonthly = priceMonthly;
    }

    public PriceDetails getPricePerTbTraffic() {
        return pricePerTbTraffic;
    }

    public void setPricePerTbTraffic(PriceDetails pricePerTbTraffic) {
        this.pricePerTbTraffic = pricePerTbTraffic;
    }
}

package dev.tomr.hcloud.resources.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    private Price price;

    @BeforeEach
    void setUp() {
        price = new Price(00L, null, null, null, null);
    }

    @Test
    void setIncludedTraffic() {
        price.setIncludedTraffic(1L);
        assertEquals(1, price.getIncludedTraffic());
    }

    @Test
    void setLocation() {
        price.setLocation("location");
        assertEquals("location", price.getLocation());
    }

    @Test
    void setPriceHourly() {
        PriceDetails priceDetails = new PriceDetails("gross", "net");
        price.setPriceHourly(priceDetails);
        assertEquals(priceDetails, price.getPriceHourly());
    }

    @Test
    void setPriceMonthly() {
        PriceDetails priceDetails = new PriceDetails("gross", "net");
        price.setPriceMonthly(priceDetails);
        assertEquals(priceDetails, price.getPriceMonthly());
    }

    @Test
    void setPricePerTbTraffic() {
        PriceDetails priceDetails = new PriceDetails("gross", "net");
        price.setPricePerTbTraffic(priceDetails);
        assertEquals(priceDetails, price.getPricePerTbTraffic());
    }

    @Test
    void defaultConstructor() {
        Price price = new Price();
        assertNotNull(price);
    }
}
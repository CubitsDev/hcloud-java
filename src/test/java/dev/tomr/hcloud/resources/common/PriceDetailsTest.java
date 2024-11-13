package dev.tomr.hcloud.resources.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceDetailsTest {

    private PriceDetails priceDetails;

    @BeforeEach
    void setUp() {
        priceDetails = new PriceDetails(null, null);
    }

    @Test
    void setGross() {
        priceDetails.setGross("gross");
        assertEquals("gross", priceDetails.getGross());
    }

    @Test
    void setNet() {
        priceDetails.setNet("net");
        assertEquals("net", priceDetails.getNet());
    }

    @Test
    void defaultConstructor() {
        PriceDetails priceDetails = new PriceDetails();
        assertNotNull(priceDetails);
    }
}
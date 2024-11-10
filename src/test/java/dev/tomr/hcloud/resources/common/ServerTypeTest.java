package dev.tomr.hcloud.resources.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServerTypeTest {

    private ServerType serverType;

    @BeforeEach
    void setUp() {
        serverType = new ServerType(null, 0, null, false, null, null, 0, 0, null, 0, null, null, null);
    }

    @Test
    void setArchitecture() {
        serverType.setArchitecture("x86");
        assertEquals("x86", serverType.getArchitecture());
    }

    @Test
    void setCores() {
        serverType.setCores(2);
        assertEquals(2, serverType.getCores());
    }

    @Test
    void setCpuType() {
        serverType.setCpuType("cpu");
        assertEquals("cpu", serverType.getCpuType());
    }

    @Test
    void setDeprecated() {
        serverType.setDeprecated(true);
        assertTrue(serverType.isDeprecated());
    }

    @Test
    void setDeprecation() {
        Deprecation deprecation = new Deprecation(new Date(), new Date());
        serverType.setDeprecation(deprecation);
        assertEquals(deprecation, serverType.getDeprecation());
    }

    @Test
    void setDescription() {
        serverType.setDescription("test");
        assertEquals("test", serverType.getDescription());
    }

    @Test
    void setDisk() {
        serverType.setDisk(1024);
        assertEquals(1024, serverType.getDisk());
    }

    @Test
    void setId() {
        serverType.setId(1);
        assertEquals(1, serverType.getId());
    }

    @Test
    void setIncludedTraffic() {
        Object includedTraffic = new Object();
        serverType.setIncludedTraffic(includedTraffic);
        assertEquals(includedTraffic, serverType.getIncludedTraffic());
    }

    @Test
    void setMemory() {
        serverType.setMemory(1024);
        assertEquals(1024, serverType.getMemory());
    }

    @Test
    void setName() {
        serverType.setName("test");
        assertEquals("test", serverType.getName());
    }

    @Test
    void setPrices() {
        List<Price> prices = List.of(new Price(0L, "", new PriceDetails("", ""), new PriceDetails("", ""), new PriceDetails("", "")));
        serverType.setPrices(prices);
        assertEquals(prices, serverType.getPrices());
    }

    @Test
    void setStorageType() {
        serverType.setStorageType("storage");
        assertEquals("storage", serverType.getStorageType());
    }
}
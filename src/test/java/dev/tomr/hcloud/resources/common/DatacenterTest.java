package dev.tomr.hcloud.resources.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatacenterTest {

    private Datacenter datacenter;

    @BeforeEach
    void setUp() {
        datacenter = new Datacenter(null, 0, null, null, null);
    }

    @Test
    void setDescription() {
        datacenter.setDescription("test");
        assertEquals("test", datacenter.getDescription());
    }

    @Test
    void setId() {
        datacenter.setId(1);
        assertEquals(1, datacenter.getId());
    }

    @Test
    void setLocation() {
        Location location = new Location("london", "gb", "", 0, 1.0, 1.0, "name", "nZ");
        datacenter.setLocation(location);
        assertEquals(location, datacenter.getLocation());
    }

    @Test
    void setName() {
        datacenter.setName("test");
        assertEquals("test", datacenter.getName());
    }

    @Test
    void setServerTypes() {
        ServerTypes serverTypes = new ServerTypes(List.of(), List.of(), List.of());
        datacenter.setServerTypes(serverTypes);
        assertEquals(serverTypes, datacenter.getServerTypes());
    }
}
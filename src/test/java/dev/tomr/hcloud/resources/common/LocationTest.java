package dev.tomr.hcloud.resources.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    private Location location;

    @BeforeEach
    void setUp() {
        location = new Location(null, null, null, 0, 0, 0, null, null);
    }

    @Test
    void setCity() {
        location.setCity("London");
        assertEquals("London", location.getCity());
    }

    @Test
    void setCountry() {
        location.setCountry("UK");
        assertEquals("UK", location.getCountry());
    }

    @Test
    void setDescription() {
        location.setDescription("test");
        assertEquals("test", location.getDescription());
    }

    @Test
    void setId() {
        location.setId(1);
        assertEquals(1, location.getId());
    }

    @Test
    void setLatitude() {
        location.setLatitude(1.0);
        assertEquals(1.0, location.getLatitude(), 0);
    }

    @Test
    void setLongitude() {
        location.setLongitude(1.0);
        assertEquals(1.0, location.getLongitude(), 0);
    }

    @Test
    void setName() {
        location.setName("test");
        assertEquals("test", location.getName());
    }

    @Test
    void setNetworkZone() {
        location.setNetworkZone("test");
        assertEquals("test", location.getNetworkZone());
    }

    @Test
    void defaultConstructor() {
        Location location = new Location();
        assertNotNull(location);
    }
}
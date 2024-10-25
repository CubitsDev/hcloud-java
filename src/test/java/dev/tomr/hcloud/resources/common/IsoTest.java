package dev.tomr.hcloud.resources.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class IsoTest {

    private Iso iso;

    @BeforeEach
    void setUp() {
        iso = new Iso(null, null, null, 0, null, null);
    }

    @Test
    void setArchitecture() {
        iso.setArchitecture("x86");
        assertEquals("x86", iso.getArchitecture());
    }

    @Test
    void setDeprecation() {
        Map<String, Date> map = Map.of("", new Date());
        iso.setDeprecation(map);
        assertEquals(map, iso.getDeprecation());
    }

    @Test
    void setDescription() {
        iso.setDescription("test");
        assertEquals("test", iso.getDescription());
    }

    @Test
    void setId() {
        iso.setId(1);
        assertEquals(1, iso.getId());
    }

    @Test
    void setName() {
        iso.setName("test");
        assertEquals("test", iso.getName());
    }

    @Test
    void setType() {
        iso.setType("test");
        assertEquals("test", iso.getType());
    }
}
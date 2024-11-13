package dev.tomr.hcloud.resources.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlacementGroupTest {

    private PlacementGroup placementGroup;

    @BeforeEach
    void setUp() {
        placementGroup = new PlacementGroup(null, 0, null, null, null, null);
    }

    @Test
    void setCreated() {
        Date created = new Date();
        placementGroup.setCreated(created);
        assertEquals(created, placementGroup.getCreated());
    }

    @Test
    void setId() {
        placementGroup.setId(1);
        assertEquals(1, placementGroup.getId());
    }

    @Test
    void setLabels() {
        placementGroup.setLabels(Map.of("k", "v"));
        assertEquals(Map.of("k", "v"), placementGroup.getLabels());
    }

    @Test
    void setName() {
        placementGroup.setName("test");
        assertEquals("test", placementGroup.getName());
    }

    @Test
    void setServers() {
        placementGroup.setServers(List.of(0, 1, 2));
        assertEquals(List.of(0, 1, 2), placementGroup.getServers());
    }

    @Test
    void setType() {
        placementGroup.setType("test");
        assertEquals("test", placementGroup.getType());
    }

    @Test
    void defaultConstructor() {
        PlacementGroup placementGroup = new PlacementGroup();
        assertNotNull(placementGroup);
    }
}
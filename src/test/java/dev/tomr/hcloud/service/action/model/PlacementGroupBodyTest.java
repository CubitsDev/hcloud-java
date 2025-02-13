package dev.tomr.hcloud.service.action.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlacementGroupBodyTest {

    @Test
    void getId() {
        PlacementGroupBody pl = new PlacementGroupBody(1);
        assertEquals(1, pl.getId());
    }

    @Test
    void setId() {
        PlacementGroupBody pl = new PlacementGroupBody(1);
        pl.setId(2);
        assertEquals(2, pl.getId());
    }
}
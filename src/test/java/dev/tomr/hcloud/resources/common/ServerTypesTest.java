package dev.tomr.hcloud.resources.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServerTypesTest {

    private ServerTypes serverTypes;

    @BeforeEach
    void setUp() {
        serverTypes = new ServerTypes(null, null, null);
    }

    @Test
    void setAvailable() {
        serverTypes.setAvailable(List.of(1, 2));
        assertEquals(List.of(1, 2), serverTypes.getAvailable());
    }

    @Test
    void setAvailableForMigration() {
        serverTypes.setAvailableForMigration(List.of(1, 2));
        assertEquals(List.of(1, 2), serverTypes.getAvailableForMigration());
    }

    @Test
    void setSupported() {
        serverTypes.setSupported(List.of(1, 2));
        assertEquals(List.of(1, 2), serverTypes.getSupported());
    }
}
package dev.tomr.hcloud.resources.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DeprecationTest {

    private Deprecation deprecation;

    @BeforeEach
    void setUp() {
        deprecation = new Deprecation(null, null);
    }

    @Test
    void setAnnounced() {
        Date date = Date.from(Instant.now());
        deprecation.setAnnounced(date);
        assertEquals(date, deprecation.getAnnounced());
    }

    @Test
    void setUnavailableAfter() {
        Date date = Date.from(Instant.now());
        deprecation.setUnavailableAfter(date);
        assertEquals(date, deprecation.getUnavailableAfter());
    }
}
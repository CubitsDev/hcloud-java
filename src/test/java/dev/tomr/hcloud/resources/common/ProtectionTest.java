package dev.tomr.hcloud.resources.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProtectionTest {

    private Protection protection;

    @BeforeEach
    void setUp() {
        protection = new Protection(false, false);
    }

    @Test
    void setDelete() {
        protection.setDelete(true);
        assertTrue(protection.isDelete());
    }

    @Test
    void setRebuild() {
        protection.setRebuild(true);
        assertTrue(protection.isRebuild());
    }

    @Test
    void defaultConstructor() {
        Protection protection = new Protection();
        assertNotNull(protection);
    }
}
package dev.tomr.hcloud.resources.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatedFromTest {

    private CreatedFrom createdFrom;

    @BeforeEach
    void setUp() {
        createdFrom = new CreatedFrom(0, null);
    }

    @Test
    void setId() {
        createdFrom.setId(1);
        assertEquals(1, createdFrom.getId());
    }

    @Test
    void setName() {
        createdFrom.setName("test");
        assertEquals("test", createdFrom.getName());
    }
}

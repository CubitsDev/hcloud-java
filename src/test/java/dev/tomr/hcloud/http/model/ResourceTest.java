package dev.tomr.hcloud.http.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceTest {

    @Test
    @DisplayName("setID changes ID of object")
    public void setId() {
        Resource resource = new Resource();
        resource.setId(1);
        assertEquals(1, resource.getId());
    }

    @Test
    @DisplayName("setType changes type of object")
    public void setType() {
        Resource resource = new Resource();
        resource.setType("type");
        assertEquals("type", resource.getType());
    }

    @Test
    @DisplayName("Constructor sets attributes")
    public void constructorWorks() {
        Resource resource = new Resource(1, "type");
        assertEquals("type", resource.getType());
        assertEquals(1, resource.getId());
    }

}

package dev.tomr.hcloud.resources.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ImageTest {

    private Image image;

    @BeforeEach
    void setUp() {
        image = new Image(null, null, null, null, null, null, null, null, 0, 0, null, null, null, null, null, false, null, null);
    }

    @Test
    void setArchitecture() {
        image.setArchitecture("x86");
        assertEquals("x86", image.getArchitecture());
    }

    @Test
    void setBoundTo() {
        Object boundTo = new Object();
        image.setBoundTo(boundTo);
        assertEquals(boundTo, image.getBoundTo());
    }

    @Test
    void setCreated() {
        Date date = new Date();
        image.setCreated(date);
        assertEquals(date, image.getCreated());
    }

    @Test
    void setCreatedFrom() {
        CreatedFrom createdFrom = new CreatedFrom(0, "");
        image.setCreatedFrom(createdFrom);
        assertEquals(createdFrom, image.getCreatedFrom());
    }

    @Test
    void setDeleted() {
        image.setDeleted(true);
        assertEquals(true, image.getDeleted());
    }

    @Test
    void setDeprecated() {
        Date date = new Date();
        image.setDeprecated(date);
        assertEquals(date, image.getDeprecated());
    }

    @Test
    void setDescription() {
        image.setDescription("");
        assertEquals("", image.getDescription());
    }

    @Test
    void setDiskSize() {
        image.setDiskSize(1024);
        assertEquals(1024, image.getDiskSize());
    }

    @Test
    void setId() {
        image.setId(1);
        assertEquals(1, image.getId());
    }

    @Test
    void setImageSize() {
        image.setImageSize(1024);
        assertEquals(1024, image.getImageSize());
    }

    @Test
    void setLabels() {
        image.setLabels(Map.of("k", "v"));
        assertEquals(Map.of("k", "v"), image.getLabels());
    }

    @Test
    void setName() {
        image.setName("test");
        assertEquals("test", image.getName());
    }

    @Test
    void setOsFlavor() {
        image.setOsFlavor("linux");
        assertEquals("linux", image.getOsFlavor());
    }

    @Test
    void setOsVersion() {
        image.setOsVersion("linux");
        assertEquals("linux", image.getOsVersion());
    }

    @Test
    void setProtection() {
        Protection protection = new Protection(false, false);
        image.setProtection(protection);
        assertEquals(protection, image.getProtection());
    }

    @Test
    void setRapidDeploy() {
        image.setRapidDeploy(true);
        assertTrue(image.isRapidDeploy());
    }

    @Test
    void setStatus() {
        image.setStatus("test");
        assertEquals("test", image.getStatus());
    }

    @Test
    void setType() {
        image.setType("type");
        assertEquals("type", image.getType());
    }
}
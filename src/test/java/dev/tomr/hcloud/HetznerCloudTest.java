package dev.tomr.hcloud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class HetznerCloudTest {

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        HetznerCloud hetznerCloud = HetznerCloud.getInstance();
        Field field = hetznerCloud.getClass().getDeclaredField("instance");
        field.setAccessible(true);
        field.set(hetznerCloud, null);
    }

    @Test
    @DisplayName("Calling Get Instance creates a new Instance when one doesn't already exist")
    void callingGetInstanceCreatesANewInstanceIfOneDoesNotExist() {
        assertInstanceOf(HetznerCloud.class, HetznerCloud.getInstance());
    }

    @Test
    @DisplayName("Calling Get Instance returns an existing Instance if one was created by .getInstance()")
    void callingGetInstanceReturnsExistingInstanceIfOneExistsFromGetInstance() {
        HetznerCloud instance = HetznerCloud.getInstance();
        assertEquals(instance, HetznerCloud.getInstance());
    }

    @Test
    @DisplayName("Calling Get Instance Returns an Existing Instance when made from a constructor")
    void callingGetInstanceReturnsExistingInstanceMadeFromConstructor() {
        HetznerCloud instance = new HetznerCloud("apiKey");
        assertEquals(instance, HetznerCloud.getInstance());

        HetznerCloud instance2 = new HetznerCloud("apiKey", "differentHost");
        assertEquals(instance2, HetznerCloud.getInstance());
    }

    @Test
    @DisplayName("Calling Set Api Key updates the API key")
    void callingSetApiKeyUpdatesTheAPIKey() throws IllegalAccessException, NoSuchFieldException {
        HetznerCloud instance = HetznerCloud.getInstance();
        instance.setApiKey("apiKey");

        Field field = instance.getClass().getDeclaredField("apiKey");
        field.setAccessible(true);
        assertEquals("apiKey", (String) field.get(instance));
    }

}
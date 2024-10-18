package dev.tomr.hcloud.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class HetznerCloudHttpClientTest {

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        HetznerCloudHttpClient client = new HetznerCloudHttpClient();
        Field field = client.getClass().getDeclaredField("instance");
        field.setAccessible(true);
        field.set(client, null);
    }

    @Test
    @DisplayName("Calling Get Instance creates a new Instance when one doesn't already exist")
    void callingGetInstanceCreatesANewInstanceWhenOneDoesNotExist() {
        HetznerCloudHttpClient instance = HetznerCloudHttpClient.getInstance();
        assertInstanceOf(HetznerCloudHttpClient.class, instance);
    }

    @Test
    @DisplayName("Calling Get Instance returns an existing Instance if one was created by .getInstance()")
    void callingGetInstanceReturnsExistingInstanceIfOneExistsFromGetInstance() {
        HetznerCloudHttpClient client = HetznerCloudHttpClient.getInstance();
        assertEquals(client, HetznerCloudHttpClient.getInstance());
    }

    @Test
    @DisplayName("Calling Get Instance Returns an Existing Instance when made from a constructor")
    void callingGetInstanceReturnsExistingInstanceWhenMadeFromConstructor() {
        HetznerCloudHttpClient client = new HetznerCloudHttpClient();
        assertEquals(client, HetznerCloudHttpClient.getInstance());

        HetznerCloudHttpClient client2 = HetznerCloudHttpClient.getInstance();
        assertEquals(client2, HetznerCloudHttpClient.getInstance());
    }
}

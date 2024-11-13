package dev.tomr.hcloud;

import dev.tomr.hcloud.listener.ListenerManager;
import dev.tomr.hcloud.resources.server.Server;
import dev.tomr.hcloud.service.ServiceManager;
import dev.tomr.hcloud.service.server.ServerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName("Calling getListenerManager returns the listener manager")
    void callingGetListenerManagerReturnsTheListenerManager() {
        ListenerManager listenerManager = ListenerManager.getInstance();
        assertEquals(listenerManager, HetznerCloud.getListenerManager());
    }

    @Test
    @DisplayName("Calling getServiceManager returns the service manager")
    void callingGetServiceManagerReturnsTheServiceManager() {
        ServiceManager serviceManager = ServiceManager.getInstance();
        assertEquals(serviceManager, HetznerCloud.getServiceManager());
    }

    @Test
    @DisplayName("Calling getHttpDetails will return the host and apikey in a list")
    void callingGetHttpDetailsWillReturnTheHostAndApikeyInAList() {
        HetznerCloud instance = HetznerCloud.getInstance();
        instance.setApiKey("apiKey");
        assertEquals(List.of("https://api.hetzner.cloud/v1/", "apiKey"), instance.getHttpDetails());
    }

    @Test
    @DisplayName("Calling hasApiKey returns true for present key")
    void callingHasApiKeyReturnsTrueForPresentKey() {
        HetznerCloud instance = HetznerCloud.getInstance();
        instance.setApiKey("apiKey");
        assertTrue(instance.hasApiKey());
    }

    @Test
    @DisplayName("Calling hasApiKey returns false for empty value")
    void callingHasApiKeyReturnsFalseForEmptyValue() {
        HetznerCloud instance = HetznerCloud.getInstance();
        instance.setApiKey("");
        assertFalse(instance.hasApiKey());
    }

    @Test
    @DisplayName("Calling hasApiKey returns false for null value")
    void callingHasApiKeyReturnsFalseForNullValue() {
        HetznerCloud instance = HetznerCloud.getInstance();
        instance.setApiKey(null);
        assertFalse(instance.hasApiKey());
    }

    @Test
    @DisplayName("calling getServer calls ServerManager for the server")
    void callingGetServerCallsServerManagerForTheServer() {
        try (MockedStatic<ServiceManager> serviceManagerMockedStatic = mockStatic(ServiceManager.class);
             MockedStatic<ListenerManager> listenerManagerMockedStatic = mockStatic(ListenerManager.class)) {

            ServiceManager serviceManager = mock(ServiceManager.class);
            ServerService serverService = mock(ServerService.class);
            ListenerManager listenerManager = mock(ListenerManager.class);

            serviceManagerMockedStatic.when(ServiceManager::getInstance).thenReturn(serviceManager);
            listenerManagerMockedStatic.when(ListenerManager::getInstance).thenReturn(listenerManager);

            when(serviceManager.getServerService()).thenReturn(serverService);

            Server server = new Server(1,
                    "backupWindow",
                    "created",
                    null,
                    null,
                    1L,
                    1L,
                    1L,
                    null,
                    Map.of("label", "value"),
                    List.of(),
                    false,
                    "name",
                    null,
                    1L,
                    List.of(),
                    null,
                    new Object(),
                    false,
                    null,
                    "healthy",
                    List.of(1));

            HetznerCloud instance = HetznerCloud.getInstance();

            when(serverService.getServer(anyInt())).thenReturn(server);

            Server serverUnderTest = instance.getServer(1);

            assertEquals(server, serverUnderTest);
            verify(serverService, times(1)).getServer(1);
        }

    }

}
package dev.tomr.hcloud.resources.server;

import dev.tomr.hcloud.HetznerCloud;
import dev.tomr.hcloud.listener.ListenerManager;
import dev.tomr.hcloud.listener.ServerChangeListener;
import dev.tomr.hcloud.resources.common.*;
import dev.tomr.hcloud.service.ServiceManager;
import dev.tomr.hcloud.service.server.ServerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import java.beans.PropertyChangeEvent;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class ServerTest {

    @Test
    @DisplayName("Server can be instantiated and values set correctly from constructor")
    void serverCanBeInstantiatedAndValuesSetCorrectly() {
        ServerType SERVER_TYPE = new ServerType(
                "",
                1,
                "",
                false,
                null,
                "",
                0,
                1,
                null,
                2048,
                "",
                List.of(new Price(0L, "london", new PriceDetails("", ""), new PriceDetails("", ""), new PriceDetails("", ""))),
                ""
        );
        Iso ISO = new Iso("", Map.of(), "", 1, "", "");
        Protection PROTECTION = new Protection(false, false);
        Datacenter DATACENTER = new Datacenter("", 1, new Location("", "", "", 1, 1.0, 1.0, "", ""), "", new ServerTypes(List.of(1), List.of(1), List.of(1)));
        PlacementGroup PLACEMENT_GROUP = new PlacementGroup(Date.from(Instant.now()), 1, Map.of(), "name", List.of(1), "type");
        Image IMAGE = new Image("",
                null, Date.from(Instant.now()),
                new CreatedFrom(1, ""),
                null, Date.from(Instant.now()),
                "",
                0,
                1,
                1.21243,
                Map.of("label", "value"),
                "",
                "",
                "",
                PROTECTION,
                true,
                "",
                "");

        Server server = new Server(1,
                "backupWindow",
                "created",
                DATACENTER,
                IMAGE,
                1L,
                1L,
                1L,
                ISO,
                Map.of("label", "value"),
                List.of(),
                false,
                "name",
                PLACEMENT_GROUP,
                1L,
                List.of(),
                PROTECTION,
                new Object(),
                false,
                SERVER_TYPE,
                "healthy",
                List.of(1));

        assertEquals(1, server.getId());
        assertEquals("backupWindow", server.getBackupWindow());
        assertEquals("created", server.getCreated());
        assertEquals(DATACENTER, server.getDatacenter());
        assertEquals(IMAGE, server.getImage());
        assertEquals(1L, server.getIncludedTraffic());
        assertEquals(1L, server.getIngoingTraffic());
        assertEquals(1L, server.getOutgoingTraffic());
        assertEquals(ISO, server.getIso());
        assertEquals(Map.of("label", "value"), server.getLabels());
        assertEquals(List.of(), server.getLoadBalancers());
        assertEquals("name", server.getName());
        assertFalse(server.isLocked());
        assertEquals(PLACEMENT_GROUP, server.getPlacementGroup());
        assertEquals(1L, server.getPrimaryDiskSize());
        assertNotNull(server.getPrivateNet());
        assertEquals(PROTECTION, server.getProtection());
        assertNotNull(server.getPublicNet());
        assertFalse(server.isRescueEnabled());
        assertEquals(SERVER_TYPE, server.getServerType());
        assertEquals("healthy", server.getStatus());
        assertEquals(1, server.getVolumes().size());
    }

    @Test
    @DisplayName("calling setLabels updates labels")
    void callingSetLabelsUpdatesLabels() {
        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
            MockedStatic<ListenerManager> listenerManagerMockedStatic = mockStatic(ListenerManager.class)) {
            HetznerCloud hetznerCloud = mock(HetznerCloud.class);
            ListenerManager listenerManager = mock(ListenerManager.class);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);

            Server server = new Server();
            server.setLabels(Map.of("label", "value"));
            assertEquals("value", server.getLabels().get("label"));
            assertEquals(Map.of("label", "value"), server.getLabels());
        }
    }

    @Test
    @DisplayName("calling setLabels sends an event to the ServerChangeListener")
    void callingSetLabelsSendsAnEventToTheServerChangeListener() {
        try (MockedStatic<HetznerCloud> hetznerCloud = mockStatic(HetznerCloud.class)) {
            ServerChangeListener scl = new ServerChangeListener();
            ServerChangeListener serverChangeListener = spy(scl);
            HetznerCloud hetznerCloudMock = mock(HetznerCloud.class);
            ListenerManager listenerManager = mock(ListenerManager.class);
            ServiceManager serviceManager = mock(ServiceManager.class);
            ServerService serverService = mock(ServerService.class);
            ArgumentCaptor<PropertyChangeEvent> captor = ArgumentCaptor.forClass(PropertyChangeEvent.class);

            hetznerCloud.when(HetznerCloud::getInstance).thenReturn(hetznerCloudMock);
            when(hetznerCloudMock.getListenerManager()).thenReturn(listenerManager);
            when(hetznerCloudMock.getServiceManager()).thenReturn(serviceManager);
            when(listenerManager.getServerChangeListener()).thenReturn(serverChangeListener);
            when(serviceManager.getServerService()).thenReturn(serverService);

            Server server = new Server();
            server.setLabels(Map.of("label", "value"));
            verify(serverChangeListener, times(1)).propertyChange(captor.capture());
            assertEquals("labels", captor.getValue().getPropertyName());
            assertNull(captor.getValue().getOldValue());
            assertEquals(Map.of("label", "value"), captor.getValue().getNewValue());
        }

    }

    @Test
    @DisplayName("calling setName updates the name")
    void callingSetNameUpdatesTheName() {
        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class)) {
            HetznerCloud hetznerCloud = mock(HetznerCloud.class);
            ListenerManager listenerManager = mock(ListenerManager.class);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);
            Server server = new Server();
            server.setName("name");
            assertEquals("name", server.getName());
        }
    }

    @Test
    @DisplayName("calling setName sends an event to the ServerChangeListener")
    void callingSetNameSendsAnEventToTheServerChangeListener() {
        try (MockedStatic<HetznerCloud> hetznerCloud = mockStatic(HetznerCloud.class)) {
            HetznerCloud hetznerCloudMock = mock(HetznerCloud.class);
            ServerChangeListener scl = new ServerChangeListener();
            ServerChangeListener serverChangeListener = spy(scl);
            ListenerManager listenerManager = mock(ListenerManager.class);
            ServiceManager serviceManager = mock(ServiceManager.class);
            ServerService serverService = mock(ServerService.class);
            ArgumentCaptor<PropertyChangeEvent> captor = ArgumentCaptor.forClass(PropertyChangeEvent.class);

            hetznerCloud.when(HetznerCloud::getInstance).thenReturn(hetznerCloudMock);
            when(hetznerCloudMock.getListenerManager()).thenReturn(listenerManager);
            when(hetznerCloudMock.getServiceManager()).thenReturn(serviceManager);
            when(listenerManager.getServerChangeListener()).thenReturn(serverChangeListener);
            when(serviceManager.getServerService()).thenReturn(serverService);

            Server server = new Server();
            server.setName("test");
            verify(serverChangeListener, times(1)).propertyChange(captor.capture());
            assertEquals("name", captor.getValue().getPropertyName());
            assertNull(captor.getValue().getOldValue());
            assertEquals("test", captor.getValue().getNewValue());
        }

    }


}
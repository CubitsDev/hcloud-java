package dev.tomr.hcloud.service.server;

import dev.tomr.hcloud.HetznerCloud;
import dev.tomr.hcloud.http.HetznerCloudHttpClient;
import dev.tomr.hcloud.http.RequestVerb;
import dev.tomr.hcloud.http.model.ServerDTO;
import dev.tomr.hcloud.listener.ListenerManager;
import dev.tomr.hcloud.resources.common.*;
import dev.tomr.hcloud.resources.server.Server;
import dev.tomr.hcloud.service.ServiceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServerServiceTest {
    private ServerType SERVER_TYPE = new ServerType(
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
    private Iso ISO = new Iso("", Map.of(), "", 1, "", "");
    private Protection PROTECTION = new Protection(false, false);
    private Datacenter DATACENTER = new Datacenter("", 1, new Location("", "", "", 1, 1.0, 1.0, "", ""), "", new ServerTypes(List.of(1), List.of(1), List.of(1)));
    private PlacementGroup PLACEMENT_GROUP = new PlacementGroup(Date.from(Instant.now()), 1, Map.of(), "name", List.of(1), "type");
    private Image IMAGE = new Image("",
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

    @Test
    @DisplayName("Task is scheduled when serverNameOrLabelUpdate called for first time with name supplied")
    void taskIsScheduledWhenServerNameOrLabelUpdateCalledForFirstTime() {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        ListenerManager listenerManager = mock(ListenerManager.class);
        ServiceManager serviceManager = spy(ServiceManager.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class);
             MockedStatic<ListenerManager> listenerManagerMockedStatic = mockStatic(ListenerManager.class);
             MockedStatic<ServiceManager> serviceManagerMockedStatic = mockStatic(ServiceManager.class)) {

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            listenerManagerMockedStatic.when(ListenerManager::getInstance).thenReturn(listenerManager);
            serviceManagerMockedStatic.when(ServiceManager::getInstance).thenReturn(serviceManager);

            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            hetznerCloudMockedStatic.when(HetznerCloud::getServiceManager).thenReturn(serviceManager);
            hetznerCloudMockedStatic.when(HetznerCloud::getListenerManager).thenReturn(listenerManager);

            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));

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

            ServerService serverService = new ServerService();

            serverService.serverNameOrLabelUpdate("name", "name", server);

            verify(hetznerCloudHttpClient, timeout(2000).times(1)).sendHttpRequest(eq(ServerDTO.class), eq("http://host/server/1"), any(RequestVerb.class), eq("key1234"),  eq("{\"name\":\"name\"}"));
        } catch (IOException | InterruptedException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Task is scheduled when serverNameOrLabelUpdate called for first time with labels supplied")
    void taskIsScheduledWhenServerNameOrLabelUpdateCalledForFirstTimeWithLabels() {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        ListenerManager listenerManager = mock(ListenerManager.class);
        ServiceManager serviceManager = spy(ServiceManager.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class);
             MockedStatic<ListenerManager> listenerManagerMockedStatic = mockStatic(ListenerManager.class);
             MockedStatic<ServiceManager> serviceManagerMockedStatic = mockStatic(ServiceManager.class)) {

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            listenerManagerMockedStatic.when(ListenerManager::getInstance).thenReturn(listenerManager);
            serviceManagerMockedStatic.when(ServiceManager::getInstance).thenReturn(serviceManager);

            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            hetznerCloudMockedStatic.when(HetznerCloud::getServiceManager).thenReturn(serviceManager);
            hetznerCloudMockedStatic.when(HetznerCloud::getListenerManager).thenReturn(listenerManager);

            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));

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

            ServerService serverService = new ServerService();

            serverService.serverNameOrLabelUpdate("labels", Map.of("label", "value"), server);

            verify(hetznerCloudHttpClient, timeout(2000).times(1)).sendHttpRequest(eq(ServerDTO.class), eq("http://host/server/1"), any(RequestVerb.class), eq("key1234"),  eq("{\"labels\":{\"label\":\"value\"}}"));
        } catch (IOException | InterruptedException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Task uses extra fields changed after first invocation")
    void taskUsesExtraFieldsChangedAfterFirstInvocation() {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        ListenerManager listenerManager = mock(ListenerManager.class);
        ServiceManager serviceManager = spy(ServiceManager.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class);
             MockedStatic<ListenerManager> listenerManagerMockedStatic = mockStatic(ListenerManager.class);
             MockedStatic<ServiceManager> serviceManagerMockedStatic = mockStatic(ServiceManager.class)) {

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            listenerManagerMockedStatic.when(ListenerManager::getInstance).thenReturn(listenerManager);
            serviceManagerMockedStatic.when(ServiceManager::getInstance).thenReturn(serviceManager);

            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            hetznerCloudMockedStatic.when(HetznerCloud::getServiceManager).thenReturn(serviceManager);
            hetznerCloudMockedStatic.when(HetznerCloud::getListenerManager).thenReturn(listenerManager);

            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));

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

            ServerService serverService = new ServerService();

            serverService.serverNameOrLabelUpdate("name", "name", server);
            serverService.serverNameOrLabelUpdate("labels", Map.of("l", "v"), server);

            verify(hetznerCloudHttpClient, timeout(2000).times(1)).sendHttpRequest(eq(ServerDTO.class), eq("http://host/server/1"), any(RequestVerb.class), eq("key1234"),  eq("{\"labels\":{\"label\":\"value\"},\"name\":\"name\"}"));
        } catch (IOException | InterruptedException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("When Http Client throws, it's handled gracefully")
    void whenHttpClientThrowsGracefully() {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        ListenerManager listenerManager = mock(ListenerManager.class);
        ServiceManager serviceManager = spy(ServiceManager.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class);
             MockedStatic<ListenerManager> listenerManagerMockedStatic = mockStatic(ListenerManager.class);
             MockedStatic<ServiceManager> serviceManagerMockedStatic = mockStatic(ServiceManager.class)) {

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            listenerManagerMockedStatic.when(ListenerManager::getInstance).thenReturn(listenerManager);
            serviceManagerMockedStatic.when(ServiceManager::getInstance).thenReturn(serviceManager);

            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            hetznerCloudMockedStatic.when(HetznerCloud::getServiceManager).thenReturn(serviceManager);
            hetznerCloudMockedStatic.when(HetznerCloud::getListenerManager).thenReturn(listenerManager);

            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));

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

            ServerService serverService = new ServerService();

            serverService.serverNameOrLabelUpdate("name", "name", server);

            when(hetznerCloudHttpClient.sendHttpRequest(any(), any(), any(), any(), any())).thenThrow(IOException.class);

            verify(hetznerCloudHttpClient, timeout(2000).times(0)).sendHttpRequest(eq(ServerDTO.class), eq("http://host/server/1"), any(RequestVerb.class), eq("key1234"),  eq("{\"labels\":{\"label\":\"value\"},\"name\":\"name\"}"));
            verify(serviceManager, timeout(2000).times(1)).closeExecutor();
        } catch (IOException | InterruptedException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("Cancel method prevents the request being sent")
    void cancelMethodPreventsTheRequestBeingSent() {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        ListenerManager listenerManager = mock(ListenerManager.class);
        ServiceManager serviceManager = spy(ServiceManager.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class);
             MockedStatic<ListenerManager> listenerManagerMockedStatic = mockStatic(ListenerManager.class);
             MockedStatic<ServiceManager> serviceManagerMockedStatic = mockStatic(ServiceManager.class)) {

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            listenerManagerMockedStatic.when(ListenerManager::getInstance).thenReturn(listenerManager);
            serviceManagerMockedStatic.when(ServiceManager::getInstance).thenReturn(serviceManager);

            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            hetznerCloudMockedStatic.when(HetznerCloud::getServiceManager).thenReturn(serviceManager);
            hetznerCloudMockedStatic.when(HetznerCloud::getListenerManager).thenReturn(listenerManager);

            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));

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

            ServerService serverService = new ServerService();

            serverService.serverNameOrLabelUpdate("name", "name", server);
            serverService.cancelServerNameOrLabelUpdate();

            verify(hetznerCloudHttpClient, timeout(2000).times(0)).sendHttpRequest(any(), any(), any(), any(), any());
            verify(serviceManager, timeout(2000).times(1)).closeExecutor();
        } catch (IOException | InterruptedException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
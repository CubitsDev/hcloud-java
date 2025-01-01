package dev.tomr.hcloud.service.server;

import dev.tomr.hcloud.HetznerCloud;
import dev.tomr.hcloud.http.HetznerCloudHttpClient;
import dev.tomr.hcloud.http.RequestVerb;
import dev.tomr.hcloud.http.model.Action;
import dev.tomr.hcloud.http.model.ActionWrapper;
import dev.tomr.hcloud.http.model.ServerDTO;
import dev.tomr.hcloud.http.model.ServerDTOList;
import dev.tomr.hcloud.listener.ListenerManager;
import dev.tomr.hcloud.resources.common.*;
import dev.tomr.hcloud.resources.server.Server;
import dev.tomr.hcloud.service.ServiceManager;
import dev.tomr.hcloud.service.action.ActionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

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

    private MockedStatic<Instant> instantMockedStatic;

    private void mockInstant(long time) {
        Instant instant = Instant.ofEpochSecond(time);
        instantMockedStatic.when(Instant::now).thenReturn(instant);
    }

    @BeforeEach
    void setUp() {
        instantMockedStatic = mockStatic(Instant.class, Mockito.CALLS_REAL_METHODS);
    }

    @AfterEach
    void after() {
        instantMockedStatic.close();
    }

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

            when(hetznerCloud.getServiceManager()).thenReturn(serviceManager);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);

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

            verify(hetznerCloudHttpClient, timeout(2000).times(1)).sendHttpRequest(eq(ServerDTO.class), eq("http://host/servers/1"), any(RequestVerb.class), eq("key1234"),  eq("{\"name\":\"name\"}"));
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

            when(hetznerCloud.getServiceManager()).thenReturn(serviceManager);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);

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

            verify(hetznerCloudHttpClient, timeout(2000).times(1)).sendHttpRequest(eq(ServerDTO.class), eq("http://host/servers/1"), any(RequestVerb.class), eq("key1234"),  eq("{\"labels\":{\"label\":\"value\"}}"));
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

            when(hetznerCloud.getServiceManager()).thenReturn(serviceManager);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);

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

            verify(hetznerCloudHttpClient, timeout(2000).times(1)).sendHttpRequest(eq(ServerDTO.class), eq("http://host/servers/1"), any(RequestVerb.class), eq("key1234"),  eq("{\"labels\":{\"label\":\"value\"},\"name\":\"name\"}"));
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

            when(hetznerCloud.getServiceManager()).thenReturn(serviceManager);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);

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

            verify(hetznerCloudHttpClient, timeout(2000).times(0)).sendHttpRequest(eq(ServerDTO.class), eq("http://host/servers/1"), any(RequestVerb.class), eq("key1234"),  eq("{\"labels\":{\"label\":\"value\"},\"name\":\"name\"}"));
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

            when(hetznerCloud.getServiceManager()).thenReturn(serviceManager);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);

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

    @Test
    @DisplayName("getServer returns a server from the cache")
    void getServerReturnsAServerFromTheCache() throws IOException, InterruptedException, IllegalAccessException {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        ListenerManager listenerManager = mock(ListenerManager.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {
            ServerDTOList serverDTOList = new ServerDTOList();
            ServerDTO serverDTO = new ServerDTO();
            serverDTO.setName("name");
            serverDTO.setId(1);
            serverDTOList.setServers(List.of(serverDTO));

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);
            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));
            when(hetznerCloud.hasApiKey()).thenReturn(true);
            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString())).thenReturn(serverDTOList);

            ServerService serverService = new ServerService();

            Server server = serverService.getServer(1);

            assertEquals(serverDTO.getName(), server.getName());
        }
    }

    @Test
    @DisplayName("getServer returns a server from the cache when multiple are in the cache")
    void getServerReturnsAServerFromTheCacheWithMultipleCached() throws IOException, InterruptedException, IllegalAccessException {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        ListenerManager listenerManager = mock(ListenerManager.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {
            ServerDTOList serverDTOList = new ServerDTOList();
            ServerDTO serverDTO = new ServerDTO();
            serverDTO.setName("name");
            serverDTO.setId(1);
            ServerDTO serverDTO2 = new ServerDTO();
            serverDTO2.setName("name2");
            serverDTO2.setId(2);
            serverDTOList.setServers(List.of(serverDTO2, serverDTO));

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);
            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));
            when(hetznerCloud.hasApiKey()).thenReturn(true);
            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString())).thenReturn(serverDTOList);

            ServerService serverService = new ServerService();

            Server server = serverService.getServer(2);

            assertEquals(serverDTO2.getName(), server.getName());
        }
    }

    @Test
    @DisplayName("getServer returns null when it's not found")
    void getServerReturnsNullWhenItIsNotFound() throws IOException, InterruptedException, IllegalAccessException {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        ListenerManager listenerManager = mock(ListenerManager.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {
            ServerDTOList serverDTOList = new ServerDTOList();
            ServerDTO serverDTO = new ServerDTO();
            serverDTO.setName("name");
            serverDTO.setId(20);
            serverDTOList.setServers(List.of(serverDTO));

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);
            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));
            when(hetznerCloud.hasApiKey()).thenReturn(true);
            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString())).thenReturn(serverDTOList);

            ServerService serverService = new ServerService();

            Server server = serverService.getServer(1);

            assertNull(server);
        }
    }

    @Test
    @DisplayName("If no API key is present, refreshing the cache does nothing")
    void ifNoAPIKeyPresentRefreshCacheDoesNothing() {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class)) {
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            ServerService serverService = new ServerService();
            assertDoesNotThrow(serverService::forceRefreshServersCache);
        }
    }

    @Test
    @DisplayName("When http client throws, updateAllRemoteServers also throws a Runtime Exception")
    void whenHttpClientThrowsRuntimeException() throws IOException, InterruptedException, IllegalAccessException {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        ListenerManager listenerManager = mock(ListenerManager.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);
            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));
            when(hetznerCloud.hasApiKey()).thenReturn(true);
            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString())).thenThrow(IOException.class);

            ServerService serverService = new ServerService();

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> serverService.getServer(1));

            assertTrue(runtimeException.getMessage().contains("IOException"));

        }
    }

    @Test
    @DisplayName("Cache is refreshed if it was last refreshed over 10 minutes ago")
    void cacheIsRefreshedIfItWasLastRefreshedOver10MinutesAgo() throws IOException, InterruptedException, IllegalAccessException {
        mockInstant(1731856842);
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        ListenerManager listenerManager = mock(ListenerManager.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {
            ServerDTOList serverDTOList = new ServerDTOList();
            ServerDTO serverDTO = new ServerDTO();
            serverDTO.setName("name");
            serverDTO.setId(1);
            serverDTOList.setServers(List.of(serverDTO));

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);
            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));
            when(hetznerCloud.hasApiKey()).thenReturn(true);
            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString())).thenReturn(serverDTOList);

            ServerService serverService = new ServerService();
            serverService.getServer(1);

            verify(hetznerCloudHttpClient, times(1)).sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString());

            mockInstant(1731857082);

            serverService.getServer(1);
            verify(hetznerCloudHttpClient, times(1)).sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString());

            mockInstant(1731858342);

            serverService.getServer(1);

            verify(hetznerCloudHttpClient, times(2)).sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString());
        }

    }

    @Test
    @DisplayName("Delete Server From Hetzner calls Hetzner and tracks the action")
    void deleteServerFromHetznerAndTracksTheAction() throws IOException, InterruptedException, IllegalAccessException {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        ListenerManager listenerManager = mock(ListenerManager.class);
        ServiceManager serviceManager = mock(ServiceManager.class);
        ActionService actionService = mock(ActionService.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {

            Action action = new Action();
            action.setFinished(Date.from(Instant.now()).toString());

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);
            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));
            when(serviceManager.getActionService()).thenReturn(actionService);
            when(actionService.waitForActionToComplete(any(Action.class))).thenReturn(CompletableFuture.completedFuture(action));

            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString())).thenReturn(new ActionWrapper(action));

            ServerService serverService = new ServerService(serviceManager);
            serverService.deleteServerFromHetzner(new Server());

            verify(hetznerCloudHttpClient, times(1)).sendHttpRequest(any(), anyString(), eq(RequestVerb.DELETE), eq("key1234"));
            verify(actionService, times(1)).waitForActionToComplete(any(Action.class));
        }
    }

    @Test
    @DisplayName("When actionService throws, then delete Server from Hetzner also throws a Runtime exception")
    void whenActionServiceThrowsDeleteServerAlsoThrows() throws IOException, InterruptedException, IllegalAccessException {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        ListenerManager listenerManager = mock(ListenerManager.class);
        ServiceManager serviceManager = mock(ServiceManager.class);
        ActionService actionService = mock(ActionService.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);
            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));
            when(serviceManager.getActionService()).thenReturn(actionService);

            when(actionService.waitForActionToComplete(any(Action.class))).thenReturn(CompletableFuture.failedFuture(new RuntimeException(new TimeoutException())));

            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString())).thenReturn(new ActionWrapper(new Action()));

            ServerService serverService = new ServerService(serviceManager);

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> serverService.deleteServerFromHetzner(new Server()));

            verify(hetznerCloudHttpClient, times(1)).sendHttpRequest(any(), anyString(), eq(RequestVerb.DELETE), eq("key1234"));
            verify(actionService, times(1)).waitForActionToComplete(any(Action.class));

            assertTrue(runtimeException.getMessage().contains("TimeoutException"));
        }
    }

    @Test
    @DisplayName("When Delete Action returned from Hetzner is Null, server service throws a null pointer exception")
    void whenActionReturnedFromHetznerIsNullServerServiceThrowsANullPointer() throws IOException, InterruptedException, IllegalAccessException {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        ListenerManager listenerManager = mock(ListenerManager.class);
        ServiceManager serviceManager = mock(ServiceManager.class);
        ActionService actionService = mock(ActionService.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);
            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));
            when(serviceManager.getActionService()).thenReturn(actionService);

            when(actionService.waitForActionToComplete(any(Action.class))).thenReturn(CompletableFuture.completedFuture(null));

            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString())).thenReturn(new ActionWrapper(new Action()));

            ServerService serverService = new ServerService(serviceManager);

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> serverService.deleteServerFromHetzner(new Server()));

            verify(hetznerCloudHttpClient, times(1)).sendHttpRequest(any(), anyString(), eq(RequestVerb.DELETE), eq("key1234"));
            verify(actionService, times(1)).waitForActionToComplete(any(Action.class));

            assertTrue(runtimeException.getMessage().contains("NullPointerException"));
        }
    }

    @Test
    @DisplayName("When httpclient throws, then delete Server From Hetzner also throws a Runtime exception")
    void deleteServerFromHetznerHandlesException() throws IOException, InterruptedException, IllegalAccessException {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        ListenerManager listenerManager = mock(ListenerManager.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);
            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));

            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString())).thenThrow(new IOException());

            ServerService serverService = new ServerService();

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> serverService.deleteServerFromHetzner(new Server()));

            verify(hetznerCloudHttpClient, times(1)).sendHttpRequest(any(), anyString(), eq(RequestVerb.DELETE), eq("key1234"));

            assertTrue(runtimeException.getMessage().contains("IOException"));
        }
    }

    @Test
    @DisplayName("Shutdown Server calls Hetzner and tracks the action")
    void shutdownServerCallsHetznerAndTracksTheAction() throws IOException, InterruptedException, IllegalAccessException {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        ListenerManager listenerManager = mock(ListenerManager.class);
        ServiceManager serviceManager = mock(ServiceManager.class);
        ActionService actionService = mock(ActionService.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {

            Action action = new Action();
            action.setFinished(Date.from(Instant.now()).toString());

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);
            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));
            when(serviceManager.getActionService()).thenReturn(actionService);
            when(actionService.waitForActionToComplete(any(Action.class))).thenReturn(CompletableFuture.completedFuture(action));

            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString(), anyString())).thenReturn(new ActionWrapper(action));

            ServerService serverService = new ServerService(serviceManager);
            serverService.shutdownServer(new Server());

            verify(hetznerCloudHttpClient, times(1)).sendHttpRequest(any(), anyString(), eq(RequestVerb.POST), eq("key1234"), eq(""));
            verify(actionService, times(1)).waitForActionToComplete(any(Action.class));
        }
    }

    @Test
    @DisplayName("When httpclient throws, then shutdown Server also throws a Runtime exception")
    void shutdownServerHandlesException() throws IOException, InterruptedException, IllegalAccessException {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        ListenerManager listenerManager = mock(ListenerManager.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);
            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));

            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString(), anyString())).thenThrow(new IOException());

            ServerService serverService = new ServerService();

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> serverService.shutdownServer(new Server()));

            verify(hetznerCloudHttpClient, times(1)).sendHttpRequest(any(), anyString(), eq(RequestVerb.POST), eq("key1234"), eq(""));

            assertTrue(runtimeException.getMessage().contains("IOException"));
        }
    }

    @Test
    @DisplayName("When Shutdown Action returned from Hetzner is Null, server service throws a null pointer exception")
    void whenShutdownActionReturnedFromHetznerIsNullServerServiceThrowsANullPointer() throws IOException, InterruptedException, IllegalAccessException {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);
        ListenerManager listenerManager = mock(ListenerManager.class);
        ServiceManager serviceManager = mock(ServiceManager.class);
        ActionService actionService = mock(ActionService.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);
            when(hetznerCloud.getListenerManager()).thenReturn(listenerManager);
            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));
            when(serviceManager.getActionService()).thenReturn(actionService);

            when(actionService.waitForActionToComplete(any(Action.class))).thenReturn(CompletableFuture.completedFuture(null));

            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString(), anyString())).thenReturn(new ActionWrapper(new Action()));

            ServerService serverService = new ServerService(serviceManager);

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> serverService.shutdownServer(new Server()));

            verify(hetznerCloudHttpClient, times(1)).sendHttpRequest(any(), anyString(), eq(RequestVerb.POST), eq("key1234"), eq(""));
            verify(actionService, times(1)).waitForActionToComplete(any(Action.class));

            assertTrue(runtimeException.getMessage().contains("NullPointerException"));
        }
    }

}
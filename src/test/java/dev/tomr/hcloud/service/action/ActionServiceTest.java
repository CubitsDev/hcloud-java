package dev.tomr.hcloud.service.action;

import dev.tomr.hcloud.HetznerCloud;
import dev.tomr.hcloud.http.HetznerCloudHttpClient;
import dev.tomr.hcloud.http.RequestVerb;
import dev.tomr.hcloud.http.model.Action;
import dev.tomr.hcloud.http.model.ActionWrapper;
import dev.tomr.hcloud.http.model.Error;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ActionServiceTest {

    @Test
    @DisplayName("Wait for action to complete returns a CompletableFuture with the finished action when the progress is 100 and finished has a date string")
    void waitForActionToCompleteReturnsWhenProgressIs100() throws IOException, InterruptedException, IllegalAccessException {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);

            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));

            Action returnedAction = new Action();
            returnedAction.setProgress(100);
            returnedAction.setFinished(new Date().toString());
            returnedAction.setId(1);
            returnedAction.setCommand("delete_resource");

            Action originalAction = new Action();
            originalAction.setId(1);
            originalAction.setCommand("delete_resource");

            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString())).thenReturn(new ActionWrapper(returnedAction));

            ActionService actionService = new ActionService();

            CompletableFuture<Action> actionCompletableFuture = actionService.waitForActionToComplete(originalAction);

            assertEquals(1, actionCompletableFuture.join().getId());
        }
    }

    @Test
    @DisplayName("Wait for action to complete returns a CompletableFuture with the finished action after trying again when the action is not finished the first time")
    void waitForActionToCompleteReturnsACompletableFutureWithTheFinishedActionWithRetries() throws IOException, InterruptedException, IllegalAccessException {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);

            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));

            Action returnedAction = new Action();
            returnedAction.setProgress(100);
            returnedAction.setFinished(new Date().toString());
            returnedAction.setId(1);
            returnedAction.setCommand("delete_resource");

            Action unfinishedAction = new Action();
            unfinishedAction.setProgress(50);
            unfinishedAction.setId(1);
            unfinishedAction.setCommand("delete_resource");

            Action originalAction = new Action();
            originalAction.setId(1);
            originalAction.setCommand("delete_resource");

            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString())).thenReturn(
                    new ActionWrapper(unfinishedAction),
                    new ActionWrapper(unfinishedAction),
                    new ActionWrapper(returnedAction));

            ActionService actionService = new ActionService();

            CompletableFuture<Action> actionCompletableFuture = actionService.waitForActionToComplete(originalAction);

            assertEquals(1, actionCompletableFuture.join().getId());
            verify(hetznerCloudHttpClient, times(3)).sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString());
        }
    }

    @Test
    @DisplayName("Wait for action to complete returns a CompletableFuture with the finished action after the first http request is 100 on progress, but not 'finished', the second is")
    void waitForActionToCompleteReturnsACompletableFutureWithTheFinishedActionWithABadReturnFirstTryGoodSecond() throws IOException, InterruptedException, IllegalAccessException {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);

            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));

            Action returnedAction = new Action();
            returnedAction.setProgress(100);
            returnedAction.setFinished(new Date().toString());
            returnedAction.setId(1);
            returnedAction.setCommand("delete_resource");

            Action unfinishedAction = new Action();
            unfinishedAction.setProgress(100);
            unfinishedAction.setId(1);
            unfinishedAction.setCommand("delete_resource");

            Action originalAction = new Action();
            originalAction.setId(1);
            originalAction.setCommand("delete_resource");

            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString())).thenReturn(
                    new ActionWrapper(unfinishedAction),
                    new ActionWrapper(returnedAction));

            ActionService actionService = new ActionService();

            CompletableFuture<Action> actionCompletableFuture = actionService.waitForActionToComplete(originalAction);

            assertEquals(1, actionCompletableFuture.join().getId());
            verify(hetznerCloudHttpClient, times(2)).sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString());
        }
    }

    @Test
    @DisplayName("Wait for action to complete throws a Runtime exception when the Hetzner Action has the error field present")
    void waitForActionToCompleteThrowsRuntimeWhenHetznerReturnsAnError() throws IOException, InterruptedException, IllegalAccessException {
        HetznerCloud hetznerCloud = mock(HetznerCloud.class);
        HetznerCloudHttpClient hetznerCloudHttpClient = mock(HetznerCloudHttpClient.class);

        try (MockedStatic<HetznerCloud> hetznerCloudMockedStatic = mockStatic(HetznerCloud.class);
             MockedStatic<HetznerCloudHttpClient> hetznerCloudHttpClientMockedStatic = mockStatic(HetznerCloudHttpClient.class)) {

            hetznerCloudHttpClientMockedStatic.when(HetznerCloudHttpClient::getInstance).thenReturn(hetznerCloudHttpClient);
            hetznerCloudMockedStatic.when(HetznerCloud::getInstance).thenReturn(hetznerCloud);

            when(hetznerCloud.getHttpDetails()).thenReturn(List.of("http://host/", "key1234"));


            Action errorAction = new Action();
            errorAction.setId(1);
            errorAction.setCommand("delete_resource");
            errorAction.setError(new Error("HETZNER_01", "This is the error from Hetzner"));

            Action originalAction = new Action();
            originalAction.setId(1);
            originalAction.setCommand("delete_resource");

            when(hetznerCloudHttpClient.sendHttpRequest(any(), anyString(), any(RequestVerb.class), anyString())).thenReturn(new ActionWrapper(errorAction));

            ActionService actionService = new ActionService();

            RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> actionService.waitForActionToComplete(originalAction).join());

            assertTrue(runtimeException.getMessage().contains("HETZNER_01"));
            assertTrue(runtimeException.getMessage().contains("This is the error from Hetzner"));
        }
    }
}

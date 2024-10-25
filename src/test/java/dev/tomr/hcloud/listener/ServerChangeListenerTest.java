package dev.tomr.hcloud.listener;

import dev.tomr.hcloud.HetznerCloud;
import dev.tomr.hcloud.resources.server.Server;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Spy;

import java.beans.PropertyChangeEvent;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ServerChangeListenerTest {

    @Test
    void verifyServerChangeListenerCalledWhenSetterIsSet() {
        try (MockedStatic<HetznerCloud> hetznerCloud = mockStatic(HetznerCloud.class)) {
            ServerChangeListener scl = new ServerChangeListener();
            ServerChangeListener serverChangeListener = spy(scl);
            ListenerManager listenerManager = mock(ListenerManager.class);
            ArgumentCaptor<PropertyChangeEvent> captor = ArgumentCaptor.forClass(PropertyChangeEvent.class);

            hetznerCloud.when(HetznerCloud::getListenerManager).thenReturn(listenerManager);
            when(listenerManager.getServerChangeListener()).thenReturn(serverChangeListener);

            Server server = new Server();
            server.setName("test");
            verify(serverChangeListener, times(1)).propertyChange(captor.capture());
            assertEquals("name", captor.getValue().getPropertyName());
            assertNull(captor.getValue().getOldValue());
            assertEquals("test", captor.getValue().getNewValue());

            server.setLabels(Map.of("label", "value!"));
            verify(serverChangeListener, times(2)).propertyChange(captor.capture());
            assertEquals("labels", captor.getValue().getPropertyName());
            assertNull(captor.getValue().getOldValue());
            assertEquals(Map.of("label", "value!"), captor.getValue().getNewValue());
        }
    }
}

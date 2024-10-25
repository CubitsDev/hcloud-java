package dev.tomr.hcloud.listener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ListenerManagerTest {

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        ListenerManager listenerManager = ListenerManager.getInstance();
        Field field = listenerManager.getClass().getDeclaredField("instance");
        field.setAccessible(true);
        field.set(listenerManager, null);
    }

    @Test
    @DisplayName("Calling Get Instance creates a new Instance when one does not already exist")
    void callingGetInstanceCreatesANewInstanceIfOneDoesNotExist() {
        assertInstanceOf(ListenerManager.class, ListenerManager.getInstance());
    }

    @Test
    @DisplayName("Calling Get Instance returns an existing instance if one created by .getInstance()")
    void callingGetInstanceReturnsAnExistingInstanceIfCreatedByGetInstance() {
        ListenerManager listenerManager = ListenerManager.getInstance();
        assertEquals(ListenerManager.getInstance(), listenerManager);
    }

    @Test
    @DisplayName("Calling getServerChangeListener returns an instance of server change listener")
    void callingGetServerChangeListenerReturnsAnInstanceOfServerChangeListener() {
        ListenerManager listenerManager = ListenerManager.getInstance();
        assertInstanceOf(ServerChangeListener.class, listenerManager.getServerChangeListener());
    }

}
package dev.tomr.hcloud.service;

import dev.tomr.hcloud.service.server.ServerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.*;

class ServiceManagerTest {

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        ServiceManager serviceManager = ServiceManager.getInstance();
        Field field = serviceManager.getClass().getDeclaredField("instance");
        field.setAccessible(true);
        field.set(serviceManager, null);
    }

    @Test
    @DisplayName("Calling Get Instance creates a new Instance when one does not already exist")
    void callingGetInstanceCreatesANewInstanceIfOneDoesNotExist() {
        assertInstanceOf(ServiceManager.class, ServiceManager.getInstance());
    }

    @Test
    @DisplayName("Calling get instance returns an existing instance if one created by .getInstance")
    void callingGetInstanceReturnsAnExistingInstance() {
        ServiceManager serviceManager = ServiceManager.getInstance();
        assertEquals(ServiceManager.getInstance(), serviceManager);
    }

    @Test
    @DisplayName("Calling getServerService returns the server service instance")
    void callingGetServerServiceReturnsTheServerServiceInstance() {
        ServiceManager serviceManager = ServiceManager.getInstance();
        assertInstanceOf(ServerService.class, serviceManager.getServerService());
        assertNotNull(serviceManager.getServerService());
    }


    @Test
    @DisplayName("Calling getExecutor returns a new instance of an ExecutorService if it doesn't already exist")
    void callingGetExecutorCreatesANewInstanceIfOneDoesNotExist() {
        ServiceManager serviceManager = ServiceManager.getInstance();
        assertInstanceOf(ExecutorService.class, serviceManager.getExecutor());
    }

    @Test
    @DisplayName("Calling getExecutor returns the existing Instance when it already exists")
    void callingGetExecutorReturnsAnExistingInstance() {
        ServiceManager serviceManager = ServiceManager.getInstance();
        ExecutorService executor = serviceManager.getExecutor();

        assertEquals(executor, serviceManager.getExecutor());
    }

    @Test
    @DisplayName("Calling closeExecutor shuts the executor down")
    void callingCloseExecutorShutsTheExecutorDown() {
        ServiceManager serviceManager = ServiceManager.getInstance();
        ExecutorService executor = serviceManager.getExecutor();

        serviceManager.closeExecutor();
        assertTrue(executor.isShutdown());
    }

    @Test
    @DisplayName("Calling closeExecutor does nothing if executor is null")
    void callingCloseExecutorDoesNothingIfExecutorIsNull() {
        ServiceManager serviceManager = ServiceManager.getInstance();
        assertDoesNotThrow(serviceManager::closeExecutor);
    }

}
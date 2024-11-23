package dev.tomr.hcloud.http.model;

import dev.tomr.hcloud.http.model.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionTest {

    private Action action;

    @BeforeEach
    public void setUp() {
        action = new Action();
    }

    @Test
    @DisplayName("setCommand updates the command attribute")
    void setCommand() {
        action.setCommand("command");
        assertEquals("command", action.getCommand());
    }

    @Test
    @DisplayName("setFinished updates the finished attribute")
    void setFinished() {
        action.setFinished("finished");
        assertEquals("finished", action.getFinished());
    }

    @Test
    @DisplayName("setId updates the id attribute")
    void setId() {
        action.setId(1);
        assertEquals(1, action.getId());
    }

    @Test
    @DisplayName("setProgress updates the progress attribute")
    void setProgress() {
        action.setProgress(100);
        assertEquals(100, action.getProgress());
    }

    @Test
    @DisplayName("setStarted updates the progress attribute")
    void setStarted() {
        action.setStarted("started");
        assertEquals("started", action.getStarted());
    }

    @Test
    @DisplayName("setError updates the error attribute")
    void setError() {
        Error error = new Error("code", "message");
        action.setError(error);
        assertEquals(error, action.getError());
    }

    @Test
    @DisplayName("setStatus updates the status attribute")
    void setStatus() {
        action.setStatus(Status.RUNNING);
        assertEquals(Status.RUNNING, action.getStatus());
        action.setStatus(Status.ERROR);
        assertEquals(Status.ERROR, action.getStatus());
        action.setStatus(Status.SUCCESS);
        assertEquals(Status.SUCCESS, action.getStatus());
    }

    @Test
    @DisplayName("setResources updates the resources attribute")
    void setResources() {
        Resource resource = new Resource(1, "type");
        action.setResources(List.of(resource));
        assertEquals(List.of(resource), action.getResources());
    }

    @Test
    @DisplayName("constructor updates all attributes")
    void constructor() {
        Error error = new Error("code", "message");
        Resource resource = new Resource(1, "type");
        action = new Action("command",
                "finished",
                1,
                100,
                "started",
                error,
                Status.SUCCESS,
                List.of(resource));
        assertEquals(List.of(resource), action.getResources());
        assertEquals("command", action.getCommand());
        assertEquals("finished", action.getFinished());
        assertEquals(Status.SUCCESS, action.getStatus());
        assertEquals(100, action.getProgress());
        assertEquals(error, action.getError());
        assertEquals(1, action.getId());
        assertEquals("finished", action.getFinished());
    }
}

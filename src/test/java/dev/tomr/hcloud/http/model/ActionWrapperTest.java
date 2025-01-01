package dev.tomr.hcloud.http.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionWrapperTest {

    @Test
    @DisplayName("calling setAction set's the action")
    void setAction() {
        ActionWrapper actionWrapper = new ActionWrapper();
        Action action = new Action();
        action.setId(1);
        actionWrapper.setAction(action);
        assertEquals(action, actionWrapper.getAction());
    }
}

package dev.tomr.hcloud.service.action;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionEnumTest {

    @Test
    @DisplayName("SHUTDOWN enum returns 'shutdown' for the path")
    void shutdown() {
        assertEquals("shutdown", Action.SHUTDOWN.path);
    }

    @Test
    @DisplayName("POWEROFF enum returns 'poweroff' for the path")
    void poweroff() {
        assertEquals("poweroff", Action.POWEROFF.path);
    }

    @Test
    @DisplayName("POWERON enum returns 'poweron' for the path")
    void poweron() {
        assertEquals("poweron", Action.POWERON.path);
    }

    @Test
    @DisplayName("REBOOT enum returns 'reboot' for the path")
    void reboot() {
        assertEquals("reboot", Action.REBOOT.path);
    }
}

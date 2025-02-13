package dev.tomr.hcloud.service.action.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangeProtectionBodyTest {

    @Test
    void isDelete() {
        ChangeProtectionBody changeProtectionBody = new ChangeProtectionBody(true, true);
        assertTrue(changeProtectionBody.isDelete());
    }

    @Test
    void isRebuild() {
        ChangeProtectionBody changeProtectionBody = new ChangeProtectionBody(true, true);
        assertTrue(changeProtectionBody.isRebuild());
    }

    @Test
    void setDelete() {
        ChangeProtectionBody changeProtectionBody = new ChangeProtectionBody(true, true);
        changeProtectionBody.setDelete(false);
        assertFalse(changeProtectionBody.isDelete());
    }

    @Test
    void setRebuild() {
        ChangeProtectionBody changeProtectionBody = new ChangeProtectionBody(true, true);
        changeProtectionBody.setRebuild(false);
        assertFalse(changeProtectionBody.isRebuild());
    }
}

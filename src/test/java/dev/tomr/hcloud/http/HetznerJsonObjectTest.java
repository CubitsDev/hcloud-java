package dev.tomr.hcloud.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HetznerJsonObjectTest {

    @Test
    @DisplayName("HetznerJsonObject can be constructed")
    void hetznerJsonObjectCanBeConstructed() {
        HetznerJsonObject hetznerObject = new HetznerJsonObject();
        assertTrue(hetznerObject instanceof HetznerJsonObject);
    }
}

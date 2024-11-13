package dev.tomr.hcloud.http.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerDTOListTest {

    @Test
    @DisplayName("getters work as expected")
    void gettersWorkAsExpected() {
        Object o = new Object();
        List<ServerDTO> serverDTOList = new ArrayList<>();
        ServerDTOList list = new ServerDTOList(o, serverDTOList);

        assertEquals(o, list.getMeta());
        assertEquals(serverDTOList, list.getServers());
    }

    @Test
    @DisplayName("Setters work as expected")
    void settersWorkAsExpected() {
        Object o = new Object();
        List<ServerDTO> serverDTOList = new ArrayList<>();
        ServerDTOList list = new ServerDTOList();

        list.setMeta(o);
        list.setServers(serverDTOList);

        assertEquals(o, list.getMeta());
        assertEquals(serverDTOList, list.getServers());
    }
}

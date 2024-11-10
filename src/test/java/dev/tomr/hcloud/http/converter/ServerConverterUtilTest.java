package dev.tomr.hcloud.http.converter;

import dev.tomr.hcloud.http.model.ServerDTO;
import dev.tomr.hcloud.resources.common.*;
import dev.tomr.hcloud.resources.server.Server;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ServerConverterUtilTest {

    private ServerType SERVER_TYPE = new ServerType(
            "",
            1,
            "",
            false,
            null,
            "",
            0,
            1,
            null,
            2048,
            "",
            List.of(new Price(0L, "london", new PriceDetails("", ""), new PriceDetails("", ""), new PriceDetails("", ""))),
            ""
    );
    private Iso ISO = new Iso("", Map.of(), "", 1, "", "");
    private Protection PROTECTION = new Protection(false, false);
    private Image IMAGE = new Image("",
            null, Date.from(Instant.now()),
            new CreatedFrom(1, ""),
            null, Date.from(Instant.now()),
            "",
            0,
            1,
            1.21243,
            Map.of("label", "value"),
            "",
            "",
            "",
            PROTECTION,
            true,
            "",
            "");
    private Datacenter DATACENTER = new Datacenter("", 1, new Location("", "", "", 1, 1.0, 1.0, "", ""), "", new ServerTypes(List.of(1), List.of(1), List.of(1)));
    private PlacementGroup PLACEMENT_GROUP = new PlacementGroup(Date.from(Instant.now()), 1, Map.of(), "name", List.of(1), "type");
    private Object PUBLICNET = new Object();

    @Test
    @DisplayName("transformServerDTOToServer returns an expected Server object")
    void transformServerDTOToServer() {
        ServerDTO serverDTO = ServerDTO.Builder.newBuilder()
                .id(1)
                .serverType(SERVER_TYPE)
                .rescueEnabled(false)
                .created("created")
                .backupWindow("backupWindow")
                .name("name")
                .protection(PROTECTION)
                .image(IMAGE)
                .placementGroup(PLACEMENT_GROUP)
                .publicNet(PUBLICNET)
                .labels(Map.of("label", "value"))
                .locked(false)
                .primaryDiskSize(2L)
                .iso(ISO)
                .datacenter(DATACENTER)
                .loadBalancers(List.of())
                .outgoingTraffic(2L)
                .ingoingTraffic(2L)
                .privateNet(List.of())
                .volumes(List.of(1))
                .status("status")
                .includedTraffic(2L)
                .build();

        Server server = ServerConverterUtil.transformServerDTOToServer(serverDTO);

        assertEquals(1, server.getId());
        assertEquals(SERVER_TYPE, server.getServerType());
        assertFalse(server.isRescueEnabled());
        assertEquals("created", server.getCreated());
        assertEquals("backupWindow", server.getBackupWindow());
        assertEquals("name", server.getName());
        assertEquals(PROTECTION, server.getProtection());
        assertEquals(IMAGE, server.getImage());
        assertEquals(PLACEMENT_GROUP, server.getPlacementGroup());
        assertEquals(PUBLICNET, server.getPublicNet());
        assertEquals(Map.of("label", "value"), server.getLabels());
        assertFalse(server.isLocked());
        assertEquals(2, server.getPrimaryDiskSize());
        assertEquals(ISO, server.getIso());
        assertEquals(DATACENTER, server.getDatacenter());
        assertEquals(List.of(), server.getLoadBalancers());
        assertEquals(2, server.getOutgoingTraffic());
        assertEquals(List.of(1), server.getVolumes());
        assertEquals("status", server.getStatus());
        assertEquals(2, server.getIncludedTraffic());
    }

    @Test
    @DisplayName("transformServerToServerDTO returns an expected ServerDTO object")
    void transformServerToServerDTO() {
        Server server = new Server(1,
                "backupWindow",
                "created",
                DATACENTER,
                IMAGE,
                1L,
                1L,
                1L,
                ISO,
                Map.of("l", "v"),
                List.of(new Object()),
                false,
                "name",
                PLACEMENT_GROUP,
                1L,
                List.of(new Object()),
                PROTECTION,
                PUBLICNET,
                false,
                SERVER_TYPE,
                "status",
                List.of(1));

        ServerDTO serverDTO = ServerConverterUtil.transformServerToServerDTO(server);
        assertEquals("name", serverDTO.getName());
        assertEquals(Map.of("l", "v"), serverDTO.getLabels());
    }

    @Test
    @DisplayName("transformServerToServerDTO returns object when name is null in Server")
    void transformServerToServerDTOWhenServerHasNullName() {
        Server server = new Server(1,
                "backupWindow",
                "created",
                DATACENTER,
                IMAGE,
                1L,
                1L,
                1L,
                ISO,
                Map.of("l", "v"),
                List.of(new Object()),
                false,
                null,
                PLACEMENT_GROUP,
                1L,
                List.of(new Object()),
                PROTECTION,
                PUBLICNET,
                false,
                SERVER_TYPE,
                "status",
                List.of(1));

        ServerDTO serverDTO = ServerConverterUtil.transformServerToServerDTO(server);
        assertNull(serverDTO.getName());
        assertEquals(Map.of("l", "v"), serverDTO.getLabels());
    }

    @Test
    @DisplayName("transformServerToServerDTO returns object when labels are null in Server")
    void transformServerToServerDTOWhenServerHasNullLabels() {
        Server server = new Server(1,
                "backupWindow",
                "created",
                DATACENTER,
                IMAGE,
                1L,
                1L,
                1L,
                ISO,
                null,
                List.of(new Object()),
                false,
                "name",
                PLACEMENT_GROUP,
                1L,
                List.of(new Object()),
                PROTECTION,
                PUBLICNET,
                false,
                SERVER_TYPE,
                "status",
                List.of(1));

        ServerDTO serverDTO = ServerConverterUtil.transformServerToServerDTO(server);
        assertNull(serverDTO.getLabels());
        assertEquals("name", serverDTO.getName());
    }

    @Test
    @DisplayName("transformServerToServerDTO handles all null fields gracefully")
    void transformServerToServerDTOWhenServerHasNullFields() {
        Server server = new Server(1,
                "backupWindow",
                "created",
                DATACENTER,
                IMAGE,
                1L,
                1L,
                1L,
                ISO,
                null,
                List.of(new Object()),
                false,
                null,
                PLACEMENT_GROUP,
                1L,
                List.of(new Object()),
                PROTECTION,
                PUBLICNET,
                false,
                SERVER_TYPE,
                "status",
                List.of(1));

        ServerDTO serverDTO = ServerConverterUtil.transformServerToServerDTO(server);
        assertNull(serverDTO.getLabels());
        assertNull(serverDTO.getName());
    }

    @Test
    @DisplayName("ServerConverterUtil constructor")
    void test() {
        assertInstanceOf(ServerConverterUtil.class, new ServerConverterUtil());
    }
}

package dev.tomr.hcloud.http.model;

import dev.tomr.hcloud.resources.common.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ServerDTOBuilderTest {

    @Test
    @DisplayName("ServerDTOBuilder creates a ServerDTO as expected")
    void serverDTOBuilderCreatesAServerDTOCorrectly() {
        ServerType SERVER_TYPE = new ServerType(
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
                List.of(new Price(0, "london", new PriceDetails("", ""), new PriceDetails("", ""), new PriceDetails("", ""))),
                ""
        );
        Iso ISO = new Iso("", Map.of(), "", 1, "", "");
        Protection PROTECTION = new Protection(false, false);
        Image IMAGE = new Image("",
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
        Datacenter DATACENTER = new Datacenter("", 1, new Location("", "", "", 1, 1.0, 1.0, "", ""), "", new ServerTypes(List.of(1), List.of(1), List.of(1)));
        PlacementGroup PLACEMENT_GROUP = new PlacementGroup(Date.from(Instant.now()), 1, Map.of(), "name", List.of(1), "type");

        ServerDTO serverDTO = ServerDTO.Builder.newBuilder()
                .serverType(SERVER_TYPE)
                .rescueEnabled(false)
                .created("created")
                .backupWindow("backupWindow")
                .status("online")
                .volumes(List.of(12))
                .iso(ISO)
                .image(IMAGE)
                .includedTraffic(1L)
                .ingoingTraffic(1L)
                .datacenter(DATACENTER)
                .loadBalancers(List.of(new Object()))
                .locked(false)
                .name("name")
                .outgoingTraffic(1L)
                .placementGroup(PLACEMENT_GROUP)
                .primaryDiskSize(200L)
                .privateNet(List.of(new Object()))
                .protection(PROTECTION)
                .publicNet(List.of())
                .id(0)
                .labels(Map.of())
                .build();

        assertEquals(serverDTO.getServerType(), SERVER_TYPE);
        assertFalse(serverDTO.isRescueEnabled());
        assertEquals(serverDTO.getCreated(), "created");
        assertEquals(serverDTO.getBackupWindow(), "backupWindow");
        assertEquals(serverDTO.getStatus(), "online");
        assertEquals(serverDTO.getVolumes().size(), 1);
        assertEquals(serverDTO.getIso(), ISO);
        assertEquals(serverDTO.getImage(), IMAGE);
        assertEquals(serverDTO.getIncludedTraffic(), 1L);
        assertEquals(serverDTO.getIngoingTraffic(), 1L);
        assertEquals(serverDTO.getDatacenter(), DATACENTER);
        assertEquals(serverDTO.getLoadBalancers().size(), 1);
        assertFalse(serverDTO.isLocked());
        assertEquals(serverDTO.getName(), "name");
        assertEquals(serverDTO.getOutgoingTraffic(), 1L);
        assertEquals(serverDTO.getPlacementGroup(), PLACEMENT_GROUP);
        assertEquals(serverDTO.getPrimaryDiskSize(), 200L);
        assertEquals(serverDTO.getPrivateNet().size(), 1);
        assertEquals(serverDTO.getProtection(), PROTECTION);
        assertNotNull(serverDTO.getPublicNet());
        assertEquals(serverDTO.getId(), 0);
        assertEquals(serverDTO.getLabels(), Map.of());
    }

    @Test
    @DisplayName("ServerDTOBuilder handles missing fields gracefully")
    void serverDTOBuilderHandlesMissingFieldsGracefully() {
        ServerDTO serverDTO = ServerDTO.Builder.newBuilder().id(1).build();


        assertNull(serverDTO.getServerType());
        assertFalse(serverDTO.isRescueEnabled());
        assertNull(serverDTO.getCreated());
        assertNull(serverDTO.getBackupWindow());
        assertNull(serverDTO.getStatus());
        assertNull(serverDTO.getVolumes());
        assertNull(serverDTO.getIso());
        assertNull(serverDTO.getImage());
        assertNull(serverDTO.getIncludedTraffic());
        assertNull(serverDTO.getIngoingTraffic());
        assertNull(serverDTO.getDatacenter());
        assertNull(serverDTO.getLoadBalancers());
        assertFalse(serverDTO.isLocked());
        assertNull(serverDTO.getName());
        assertNull(serverDTO.getOutgoingTraffic());
        assertNull(serverDTO.getPlacementGroup());
        assertNull(serverDTO.getPrimaryDiskSize());
        assertNull(serverDTO.getPrivateNet());
        assertNull(serverDTO.getProtection());
        assertNull(serverDTO.getPublicNet());
        assertEquals(serverDTO.getId(), 1);
        assertNull(serverDTO.getLabels());
    }
}

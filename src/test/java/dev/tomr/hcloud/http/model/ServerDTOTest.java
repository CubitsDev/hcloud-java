package dev.tomr.hcloud.http.model;

import dev.tomr.hcloud.resources.common.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerDTOTest {

    private ServerDTO serverDTO;

    @BeforeEach
    void setup() {
        serverDTO = ServerDTO.Builder.newBuilder().build();
    }

    @Test
    void setId() {
        serverDTO.setId(1);
        assertEquals(serverDTO.getId(), 1);
    }

    @Test
    void setBackupWindow() {
        serverDTO.setBackupWindow("backup");
        assertEquals("backup", serverDTO.getBackupWindow());
    }

    @Test
    void setCreated() {
        serverDTO.setCreated("created");
        assertEquals("created", serverDTO.getCreated());
    }

    @Test
    void setDatacenter() {
        Datacenter datacenter = new Datacenter("", 0, new Location("", "", "", 0, 1.0, 1.0, "", ""), "", new ServerTypes(List.of(), List.of(), List.of()));
        serverDTO.setDatacenter(datacenter);
        assertEquals(datacenter, serverDTO.getDatacenter());
    }

    @Test
    void setImage() {
        Image image = new Image("",
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
                new Protection(false, false),
                true,
                "",
                "");
        serverDTO.setImage(image);
        assertEquals(image, serverDTO.getImage());
    }

    @Test
    void setIncludedTraffic() {
        serverDTO.setIncludedTraffic(100L);
        assertEquals(100L, serverDTO.getIncludedTraffic());
    }

    @Test
    void setIngoingTraffic() {
        serverDTO.setIngoingTraffic(100L);
        assertEquals(100L, serverDTO.getIngoingTraffic());
    }

    @Test
    void setOutgoingTraffic() {
        serverDTO.setOutgoingTraffic(100L);
        assertEquals(100L, serverDTO.getOutgoingTraffic());
    }

    @Test
    void setIso() {
        Iso Iso = new Iso("", Map.of(), "", 1, "", "");
        serverDTO.setIso(Iso);
        assertEquals(Iso, serverDTO.getIso());
    }

    @Test
    void setLabels() {
        serverDTO.setLabels(Map.of("label1", "value1", "label2", "value2"));
        assertEquals(Map.of("label1", "value1", "label2", "value2"), serverDTO.getLabels());
    }

    @Test
    void setLoadBalancers() {
        serverDTO.setLoadBalancers(List.of());
        assertEquals(List.of(), serverDTO.getLoadBalancers());
    }

    @Test
    void setLocked() {
        serverDTO.setLocked(true);
        assertTrue(serverDTO.isLocked());
    }

    @Test
    void setName() {
        serverDTO.setName("name");
        assertEquals("name", serverDTO.getName());
    }

    @Test
    void setPlacementGroup() {
        PlacementGroup placementGroup = new PlacementGroup(Date.from(Instant.now()), 0, Map.of(), "", List.of(1), "");
        serverDTO.setPlacementGroup(placementGroup);
        assertEquals(placementGroup, serverDTO.getPlacementGroup());
    }

    @Test
    void setPrimaryDiskSize() {
        serverDTO.setPrimaryDiskSize(100L);
        assertEquals(100L, serverDTO.getPrimaryDiskSize());
    }

    @Test
    void setPrivateNet() {
        serverDTO.setPrivateNet(List.of());
        assertEquals(List.of(), serverDTO.getPrivateNet());
    }

    @Test
    void setProtection() {
        Protection protection = new Protection(false, false);
        serverDTO.setProtection(protection);
        assertEquals(protection, serverDTO.getProtection());
    }

    @Test
    void setPublicNet() {
        Object object = new Object();
        serverDTO.setPublicNet(object);
        assertEquals(object, serverDTO.getPublicNet());
    }

    @Test
    void setRescueEnabled() {
        serverDTO.setRescueEnabled(true);
        assertTrue(serverDTO.isRescueEnabled());
    }

    @Test
    void setServerType() {
        ServerType serverType = new ServerType(
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
        serverDTO.setServerType(serverType);
        assertEquals(serverType, serverDTO.getServerType());
    }

    @Test
    void setStatus() {
        serverDTO.setStatus("status");
        assertEquals("status", serverDTO.getStatus());
    }

    @Test
    void setVolumes() {
        serverDTO.setVolumes(List.of());
        assertEquals(List.of(), serverDTO.getVolumes());
    }

    @Test
    void testToString() {
        assertEquals("ServerDTO{id=null, " +
                "backupWindow='null', " +
                "created='null', " +
                "datacenter=null, " +
                "image=null, " +
                "includedTraffic=null, " +
                "ingoingTraffic=null, " +
                "outgoingTraffic=null, " +
                "iso=null, " +
                "labels=null, " +
                "loadBalancers=null, " +
                "locked=false, " +
                "name='null', " +
                "placementGroup=null, " +
                "primaryDiskSize=null, " +
                "privateNet=null, " +
                "protection=null, " +
                "publicNet=null, " +
                "rescueEnabled=false, " +
                "serverType=null, " +
                "status='null', " +
                "volumes=null}", serverDTO.toString());
    }
}

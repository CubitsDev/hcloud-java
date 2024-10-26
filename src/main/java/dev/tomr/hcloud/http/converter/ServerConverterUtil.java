package dev.tomr.hcloud.http.converter;

import dev.tomr.hcloud.http.model.ServerDTO;
import dev.tomr.hcloud.resources.server.Server;

public class ServerConverterUtil {

    /**
     * Converts a DTO from the API to a Server Object
     * @param dto DTO from Hetzner Cloud API
     * @return Server Object representing the Hetzner server resource
     */
    public static Server transformServerDTOToServer(ServerDTO dto) {
        return new Server(
                dto.getId(),
                dto.getBackupWindow(),
                dto.getCreated(),
                dto.getDatacenter(),
                dto.getImage(),
                dto.getIncludedTraffic(),
                dto.getIngoingTraffic(),
                dto.getOutgoingTraffic(),
                dto.getIso(),
                dto.getLabels(),
                dto.getLoadBalancers(),
                dto.isLocked(),
                dto.getName(),
                dto.getPlacementGroup(),
                dto.getPrimaryDiskSize(),
                dto.getPrivateNet(),
                dto.getProtection(),
                dto.getPublicNet(),
                dto.isRescueEnabled(),
                dto.getServerType(),
                dto.getStatus(),
                dto.getVolumes()
        );
    }

    /**
     * Used to create a DTO to be **sent** to the Hetzner API (It will only include name and/or labels
     * @param server Server object to transform
     * @return ServerDTO with name and/or labels populated
     */
    public static ServerDTO transformServerToServerDTO(Server server) {
        ServerDTO.Builder builder = ServerDTO.Builder.newBuilder();

        if (server.getName() != null) {
            builder.name(server.getName());
        }
        if (server.getLabels() != null) {
            builder.labels(server.getLabels());
        }

        return builder.build();
    }
}

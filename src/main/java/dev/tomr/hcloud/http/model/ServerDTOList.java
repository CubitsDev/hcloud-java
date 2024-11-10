package dev.tomr.hcloud.http.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import dev.tomr.hcloud.http.HetznerJsonObject;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerDTOList extends HetznerJsonObject {
    private Object meta;
    private List<ServerDTO> servers;

    public ServerDTOList() {}

    public ServerDTOList(Object meta, List<ServerDTO> servers) {
        this.meta = meta;
        this.servers = servers;
    }

    public Object getMeta() {
        return meta;
    }

    public List<ServerDTO> getServers() {
        return servers;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }

    public void setServers(List<ServerDTO> servers) {
        this.servers = servers;
    }
}

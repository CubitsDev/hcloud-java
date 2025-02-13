package dev.tomr.hcloud.service.action.model;

import dev.tomr.hcloud.http.HetznerJsonObject;

public class PlacementGroupBody extends HetznerJsonObject {
    private Integer id;

    public PlacementGroupBody(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

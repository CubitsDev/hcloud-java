package dev.tomr.hcloud.service.action.model;

import dev.tomr.hcloud.http.HetznerJsonObject;

public class ChangeProtectionBody extends HetznerJsonObject {
    private boolean delete;
    private boolean rebuild;

    public ChangeProtectionBody(boolean delete, boolean rebuild) {
        this.delete = delete;
        this.rebuild = rebuild;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isRebuild() {
        return rebuild;
    }

    public void setRebuild(boolean rebuild) {
        this.rebuild = rebuild;
    }
}

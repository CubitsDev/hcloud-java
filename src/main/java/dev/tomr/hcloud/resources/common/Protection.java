package dev.tomr.hcloud.resources.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Protection{
    private boolean delete;
    private boolean rebuild;

    public Protection(boolean delete, boolean rebuild) {
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

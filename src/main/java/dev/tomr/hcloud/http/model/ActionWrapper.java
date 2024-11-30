package dev.tomr.hcloud.http.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.tomr.hcloud.http.HetznerJsonObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionWrapper extends HetznerJsonObject {
    private Action action;

    public ActionWrapper() {
    }

    public ActionWrapper(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}

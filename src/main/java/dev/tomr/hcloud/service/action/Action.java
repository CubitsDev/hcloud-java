package dev.tomr.hcloud.service.action;

public enum Action {
    SHUTDOWN("shutdown"),
    POWEROFF("poweroff");

    public final String path;

    Action(String path) {
        this.path = path;
    }
}

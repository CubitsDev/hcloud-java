package dev.tomr.hcloud.service.action;

public enum Action {
    SHUTDOWN("shutdown"),
    POWEROFF("poweroff"),
    POWERON("poweron"),
    REBOOT("reboot"),
    RESET("reset"),
    ADD_PLACEMENT_GROUP("add_to_placement_group");

    public final String path;

    Action(String path) {
        this.path = path;
    }
}

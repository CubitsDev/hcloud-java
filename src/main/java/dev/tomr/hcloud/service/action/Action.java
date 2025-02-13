package dev.tomr.hcloud.service.action;

public enum Action {
    SHUTDOWN("shutdown"),
    POWEROFF("poweroff"),
    POWERON("poweron"),
    REBOOT("reboot"),
    RESET("reset"),
    ADD_PLACEMENT_GROUP("add_to_placement_group"),
    REMOVE_PLACEMENT_GROUP("remove_from_placement_group"),
    CHANGE_SERVER_PROTECTION("change_protection");

    public final String path;

    Action(String path) {
        this.path = path;
    }
}

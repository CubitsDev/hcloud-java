package dev.tomr.hcloud.service.action;

public enum Action {
    SHUTDOWN("shutdown"),
    POWEROFF("poweroff"),
    POWERON("poweron"),
    REBOOT("reboot"),
    RESET("reset"),;

    public final String path;

    Action(String path) {
        this.path = path;
    }
}

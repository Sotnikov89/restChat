package ru.balabolka.chat.domain;

public enum Permission {
    MESSAGE_READ("message:read"),
    MESSAGE_WRITE("message:write"),
    ROOM_READ("room:read"),
    ROOM_WRITE("room:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

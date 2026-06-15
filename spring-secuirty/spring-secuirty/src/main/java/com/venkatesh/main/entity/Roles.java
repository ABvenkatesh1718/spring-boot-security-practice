package com.venkatesh.main.entity;

import java.util.Set;

public enum Roles {
    // USER only gets read permission
    ROLE_USER(Set.of("DATA_READ")),

    // ADMIN gets both read and write permissions
    ROLE_ADMIN(Set.of("DATA_READ", "DATA_WRITE"));

    private final Set<String> permissions;

    Roles(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Set<String> getPermissions() {
        return permissions;
    }
}
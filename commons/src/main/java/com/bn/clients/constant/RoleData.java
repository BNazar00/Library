package com.bn.clients.constant;

public enum RoleData {
    ADMIN("ADMIN"), USER("USER"), READER("READER");

    private final String name;

    RoleData(String name) {
        this.name = name;
    }

    public String getRoleName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

package com.bn.library.constant;

public enum RoleData {
    ADMIN("ADMIN"), USER("USER"), READER("READER");
    private static final String DB_PREFIX = "ROLE_";

    private final String name;

    RoleData(String name) {
        this.name = name;
    }

    public String getRoleName() {
        return name;
    }

    public String getDBRoleName() {
        return DB_PREFIX + name;
    }

    @Override
    public String toString() {
        return name;
    }
}

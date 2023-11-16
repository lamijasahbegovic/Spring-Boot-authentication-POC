package com.lamija.authenticationapp.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum RolesEnum {
    ROLE_USER("User"),
    ROLE_ADMIN("Admin"),
    ROLE_GUEST("Guest");

    private final String value;

    RolesEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static List<EnumObject> items() {
        List<EnumObject> enums = new ArrayList<>();
        for (RolesEnum enum_ : RolesEnum.values()) {
            enums.add(new EnumObject(enum_.name(), enum_.value()));
        }
        return enums;
    }
}

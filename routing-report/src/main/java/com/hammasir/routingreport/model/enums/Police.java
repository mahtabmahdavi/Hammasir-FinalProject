package com.hammasir.routingreport.model.enums;

import lombok.Getter;

@Getter
public enum Police {

    POLICE("POLICE"),
    SECRET_POLICE("SECRET_POLICE"),
    OPPOSITE_LINE("OPPOSITE_LINE");

    private final String value;

    Police(String value) {
        this.value = value;
    }

    public static Police fromValue(String value) {
        for (Police category : Police.values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid Police Category value: " + value);
    }
}

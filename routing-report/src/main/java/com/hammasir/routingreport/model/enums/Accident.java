package com.hammasir.routingreport.model.enums;

import lombok.Getter;

@Getter
public enum Accident {

    LIGHT("LIGHT"),
    HEAVY("HEAVY"),
    OPPOSITE_LINE("OPPOSITE_LINE");

    private final String value;

    Accident(String value) {
        this.value = value;
    }

    public static Accident fromValue(String value) {
        for (Accident category : Accident.values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid Accident Category value: " + value);
    }
}

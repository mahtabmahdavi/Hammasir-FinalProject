package com.hammasir.routingreport.model.enumuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Accident {

    LIGHT("LIGHT"),
    HEAVY("HEAVY"),
    OPPOSITE_LINE("OPPOSITE_LINE");

    private final String value;

    public static Accident fromValue(String value) {
        for (Accident category : Accident.values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid Accident Category value: " + value);
    }
}

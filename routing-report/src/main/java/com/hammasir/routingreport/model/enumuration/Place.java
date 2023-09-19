package com.hammasir.routingreport.model.enumuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Place {

    POLICE("POLICE"),
    PARKING("PARKING"),
    GAS_STATION("GAS_STATION"),
    PETROL_STATION("PETROL_STATION"),
    RED_CRESCENT("RED_CRESCENT"),
    WELFARE_SERVICE("WELFARE_SERVICE");

    private final String value;

    public static Place fromValue(String value) {
        for (Place category : Place.values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid Place Category value: " + value);
    }
}

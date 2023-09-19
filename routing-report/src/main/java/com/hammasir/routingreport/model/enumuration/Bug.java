package com.hammasir.routingreport.model.enumuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Bug {

    NO_ENTRY("NO_ENTRY"),
    DEAD_END("DEAD_END"),
    DIRT_ROAD("DIRT_ROAD"),
    NO_CAR_PATH("NO_CAR_PATH"),
    FLOW_DIRECTION("FLOW_DIRECTION"),
    OTHER("OTHER");

    private final String value;

    public static Bug fromValue(String value) {
        for (Bug category : Bug.values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid Bug Category value: " + value);
    }
}

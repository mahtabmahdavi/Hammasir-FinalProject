package com.hammasir.routingreport.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Camera {

    RED_LIGHT("RED_LIGHT"),
    SPEED_CONTROL("SPEED_CONTROL");

    private final String value;

    public static Camera fromValue(String value) {
        for (Camera category : Camera.values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid Camera Category value: " + value);
    }
}

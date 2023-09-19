package com.hammasir.routingreport.model.enumuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Event {

    HOLE("HOLE"),
    BLOCKED_ROAD("BLOCKED_ROAD"),
    CONSTRUCTION_OPERATION("CONSTRUCTION_OPERATION");

    private final String value;

    public static Event fromValue(String value) {
        for (Event category : Event.values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid Event Category value: " + value);
    }
}

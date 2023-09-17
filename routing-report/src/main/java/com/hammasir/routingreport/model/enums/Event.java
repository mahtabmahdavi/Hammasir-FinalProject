package com.hammasir.routingreport.model.enums;

import lombok.Getter;

@Getter
public enum Event {

    HOLE("HOLE"),
    BLOCKED_ROAD("BLOCKED_ROAD"),
    CONSTRUCTION_OPERATION("CONSTRUCTION_OPERATION");

    private final String value;

    Event(String value) {
        this.value = value;
    }

    public static Event fromValue(String value) {
        for (Event category : Event.values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid Event Category value: " + value);
    }
}

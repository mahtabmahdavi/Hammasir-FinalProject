package com.hammasir.routingreport.model.enums;

import lombok.Getter;

@Getter
public enum Traffic {

    JAM("JAM"),
    SMOOTH("SMOOTH"),
    SEMI_HEAVY("SEMI_HEAVY");

    private final String value;

    Traffic(String value) {
        this.value = value;
    }

    public static Traffic fromValue(String value) {
        for (Traffic category : Traffic.values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid Traffic Category value: " + value);
    }
}

package com.hammasir.routingreport.model.enums;

import lombok.Getter;

@Getter
public enum Weather {

    FOG("FOG"),
    CHAINS("CHAINS"),
    SLIP_ROAD("SLIP_ROAD");

    private final String value;

    Weather(String value) {
        this.value = value;
    }

    public static Weather fromValue(String value) {
        for (Weather category : Weather.values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid Weather Category value: " + value);
    }
}

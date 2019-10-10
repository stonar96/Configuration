package com.vanillage.utils.configuration.conversion;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ConfigurationSerialization {
    private final String type;
    private final String value;
    private final Map<Object, Object> map;

    public ConfigurationSerialization(String type) {
        this.type = type;
        this.value = null;
        map = new LinkedHashMap<>();
    }

    public ConfigurationSerialization(String type, String value) {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        this.type = type;
        this.value = value;
        this.map = null;
    }

    public String getType() {
        return type;
    }

    public Map<Object, Object> getMap() {
        return map;
    }

    public String getValue() {
        return value;
    }
}

package com.vanillage.utils.configuration.deserializers;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerMap implements ConfigurationDeserializer<Map<?, ?>> {
    @Override
    public Map<?, ?> deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        return configurationSerialization.getMap() == null ? new LinkedHashMap<>() : configurationSerialization.getMap();
    }
}

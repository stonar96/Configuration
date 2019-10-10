package com.vanillage.utils.configuration.serializers;

import java.util.Map;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerMap implements ConfigurationSerializer<Map<?, ?>> {
    @Override
    public ConfigurationSerialization serialize(Map<?, ?> object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        ConfigurationSerialization configurationSerialization = new ConfigurationSerialization(null);
        configurationSerialization.getMap().putAll(object);
        return configurationSerialization;
    }
}

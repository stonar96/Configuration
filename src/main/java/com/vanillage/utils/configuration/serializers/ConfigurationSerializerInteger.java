package com.vanillage.utils.configuration.serializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerInteger implements ConfigurationSerializer<Integer> {
    @Override
    public ConfigurationSerialization serialize(Integer object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        return new ConfigurationSerialization(null, String.valueOf(object));
    }
}

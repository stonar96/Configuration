package com.vanillage.utils.configuration.serializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerBoolean implements ConfigurationSerializer<Boolean> {
    @Override
    public ConfigurationSerialization serialize(Boolean object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        return new ConfigurationSerialization(null, String.valueOf(object));
    }
}

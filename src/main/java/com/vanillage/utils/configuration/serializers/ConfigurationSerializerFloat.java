package com.vanillage.utils.configuration.serializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerFloat implements ConfigurationSerializer<Float> {
    @Override
    public ConfigurationSerialization serialize(Float object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        return new ConfigurationSerialization("Float", String.valueOf(object));
    }
}

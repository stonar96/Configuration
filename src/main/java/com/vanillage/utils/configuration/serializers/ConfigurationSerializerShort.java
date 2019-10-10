package com.vanillage.utils.configuration.serializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerShort implements ConfigurationSerializer<Short> {
    @Override
    public ConfigurationSerialization serialize(Short object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        return new ConfigurationSerialization("Short", String.valueOf(object));
    }
}

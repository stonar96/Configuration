package com.vanillage.utils.configuration.serializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerByte implements ConfigurationSerializer<Byte> {
    @Override
    public ConfigurationSerialization serialize(Byte object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        return new ConfigurationSerialization("Byte", String.valueOf(object));
    }
}

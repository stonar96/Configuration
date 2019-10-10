package com.vanillage.utils.configuration.deserializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerByte implements ConfigurationDeserializer<Byte> {
    @Override
    public Byte deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        try {
            return Byte.valueOf(configurationSerialization.getValue());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

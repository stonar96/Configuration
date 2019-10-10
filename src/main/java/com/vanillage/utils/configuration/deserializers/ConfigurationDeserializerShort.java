package com.vanillage.utils.configuration.deserializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerShort implements ConfigurationDeserializer<Short> {
    @Override
    public Short deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        try {
            return Short.valueOf(configurationSerialization.getValue());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

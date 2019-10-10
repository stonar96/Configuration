package com.vanillage.utils.configuration.deserializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerLong implements ConfigurationDeserializer<Long> {
    @Override
    public Long deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        try {
            return Long.valueOf(configurationSerialization.getValue());
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}

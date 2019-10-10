package com.vanillage.utils.configuration.serializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerLong implements ConfigurationSerializer<Long> {
    @Override
    public ConfigurationSerialization serialize(Long object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        String value = String.valueOf(object);

        try {
            Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return new ConfigurationSerialization(null, value);
        }

        return new ConfigurationSerialization("Long", value);
    }
}

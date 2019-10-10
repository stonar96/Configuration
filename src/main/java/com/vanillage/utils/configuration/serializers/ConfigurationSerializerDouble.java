package com.vanillage.utils.configuration.serializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerDouble implements ConfigurationSerializer<Double> {
    @Override
    public ConfigurationSerialization serialize(Double object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        String value = String.valueOf(object);

        try {
            Integer.valueOf(value);
        } catch (NumberFormatException e) {
            try {
                Long.valueOf(value);
            } catch (NumberFormatException e1) {
                return new ConfigurationSerialization(null, value);
            }
        }

        return new ConfigurationSerialization("Double", value);
    }
}

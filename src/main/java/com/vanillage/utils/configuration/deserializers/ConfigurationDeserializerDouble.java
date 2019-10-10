package com.vanillage.utils.configuration.deserializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerDouble implements ConfigurationDeserializer<Double> {
    @Override
    public Double deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        try {
            return Double.valueOf(configurationSerialization.getValue());
        } catch (NumberFormatException e) {
            return 0.;
        }
    }
}

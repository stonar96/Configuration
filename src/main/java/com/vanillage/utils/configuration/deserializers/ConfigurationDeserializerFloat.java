package com.vanillage.utils.configuration.deserializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerFloat implements ConfigurationDeserializer<Float> {
    @Override
    public Float deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        try {
            return Float.valueOf(configurationSerialization.getValue());
        } catch (NumberFormatException e) {
            return 0f;
        }
    }
}

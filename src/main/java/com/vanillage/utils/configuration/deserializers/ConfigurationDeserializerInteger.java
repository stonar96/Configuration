package com.vanillage.utils.configuration.deserializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerInteger implements ConfigurationDeserializer<Integer> {
    @Override
    public Integer deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        try {
            return Integer.valueOf(configurationSerialization.getValue());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

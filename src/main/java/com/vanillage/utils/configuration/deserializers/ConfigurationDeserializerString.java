package com.vanillage.utils.configuration.deserializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerString implements ConfigurationDeserializer<String> {
    @Override
    public String deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        return configurationSerialization.getValue() == null ? "" : configurationSerialization.getValue();
    }
}

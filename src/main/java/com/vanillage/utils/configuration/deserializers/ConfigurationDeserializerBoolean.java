package com.vanillage.utils.configuration.deserializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerBoolean implements ConfigurationDeserializer<Boolean> {
    @Override
    public Boolean deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        return Boolean.valueOf(configurationSerialization.getValue());
    }
}

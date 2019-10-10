package com.vanillage.utils.configuration.deserializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerNull implements ConfigurationDeserializer<Object> {
    @Override
    public Object deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        return null;
    }
}

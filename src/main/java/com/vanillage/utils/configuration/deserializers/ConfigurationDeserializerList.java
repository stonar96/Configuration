package com.vanillage.utils.configuration.deserializers;

import java.util.LinkedList;
import java.util.List;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerList implements ConfigurationDeserializer<List<?>> {
    @Override
    public List<?> deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        return configurationSerialization.getMap() == null ? new LinkedList<>() : new LinkedList<>(configurationSerialization.getMap().values());
    }
}

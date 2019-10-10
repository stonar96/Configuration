package com.vanillage.utils.configuration.deserializers;

import java.util.LinkedHashSet;
import java.util.Set;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerSet implements ConfigurationDeserializer<Set<?>> {
    @Override
    public Set<?> deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        return configurationSerialization.getMap() == null ? new LinkedHashSet<>() : new LinkedHashSet<>(configurationSerialization.getMap().values());
    }
}

package com.vanillage.utils.configuration.serializers;

import java.util.Set;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerSet implements ConfigurationSerializer<Set<?>> {
    @Override
    public ConfigurationSerialization serialize(Set<?> object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        ConfigurationSerialization configurationSerialization = new ConfigurationSerialization("Set");
        int i = 0;

        for (Object element : object) {
            configurationSerialization.getMap().put("element" + i, element);
            i++;
        }

        return configurationSerialization;
    }
}

package com.vanillage.utils.configuration.serializers;

import java.util.List;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerList implements ConfigurationSerializer<List<?>> {
    @Override
    public ConfigurationSerialization serialize(List<?> object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        ConfigurationSerialization configurationSerialization = new ConfigurationSerialization("List");
        int i = 0;

        for (Object element : object) {
            configurationSerialization.getMap().put("index" + i, element);
            i++;
        }

        return configurationSerialization;
    }
}

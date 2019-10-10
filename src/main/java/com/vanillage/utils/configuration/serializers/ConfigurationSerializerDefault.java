package com.vanillage.utils.configuration.serializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerDefault implements ConfigurationSerializer<Object> {
    @Override
    public ConfigurationSerialization serialize(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        if (/*object != null && object.getClass().equals(ConfigurationSerialization.class)*//*class ConfigurationSerialization is final*/object instanceof ConfigurationSerialization) {
            return (ConfigurationSerialization) object;
        }

        return new ConfigurationSerialization(object.getClass().getName(), String.valueOf(object));
    }
}

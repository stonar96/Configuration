package com.vanillage.utils.configuration.serializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerNull implements ConfigurationSerializer<Object> {
    @Override
    public ConfigurationSerialization serialize(Object object) {
        return new ConfigurationSerialization("null");
    }
}

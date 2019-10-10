package com.vanillage.utils.configuration.deserializers;

import com.vanillage.utils.configuration.conversion.ConfigurationElement;
import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerConfigurationElement implements ConfigurationDeserializer<ConfigurationElement<?, ?>> {
    @Override
    public ConfigurationElement<?, ?> deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        return configurationSerialization.getMap() == null ? new ConfigurationElement<>(null, null) : new ConfigurationElement<>(configurationSerialization.getMap().get("key"), configurationSerialization.getMap().get("value"));
    }
}

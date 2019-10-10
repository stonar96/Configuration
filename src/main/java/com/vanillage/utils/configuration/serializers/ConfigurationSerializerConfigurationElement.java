package com.vanillage.utils.configuration.serializers;

import com.vanillage.utils.configuration.conversion.ConfigurationElement;
import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerConfigurationElement implements ConfigurationSerializer<ConfigurationElement<?, ?>> {
    @Override
    public ConfigurationSerialization serialize(ConfigurationElement<?, ?> object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        ConfigurationSerialization configurationSerialization = new ConfigurationSerialization("ConfigurationElement");
        configurationSerialization.getMap().put("key", object.getKey());
        configurationSerialization.getMap().put("value", object.getValue());
        return configurationSerialization;
    }
}

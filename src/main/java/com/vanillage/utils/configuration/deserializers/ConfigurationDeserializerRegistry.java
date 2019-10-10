package com.vanillage.utils.configuration.deserializers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerRegistry implements ConfigurationDeserializer<Object> {
    private final Map<String, ConfigurationDeserializer<?>> configurationDeserializers = new HashMap<>();
    private ConfigurationDeserializer<?> defaultConfigurationDeserializer;

    public Map<String, ConfigurationDeserializer<?>> getConfigurationDeserializers() {
        return Collections.unmodifiableMap(configurationDeserializers);
    }

    public ConfigurationDeserializer<?> getDefaultConfigurationDeserializer() {
        return defaultConfigurationDeserializer;
    }

    public void setDefaultConfigurationDeserializer(ConfigurationDeserializer<?> defaultConfigurationDeserializer) {
        this.defaultConfigurationDeserializer = defaultConfigurationDeserializer;
    }

    public ConfigurationDeserializer<?> register(String type, ConfigurationDeserializer<?> configurationDeserializer) {
        if (configurationDeserializer == null) {
            throw new IllegalArgumentException("configurationDeserializer cannot be null");
        }

        return configurationDeserializers.put(type, configurationDeserializer);
    }

    public ConfigurationDeserializer<?> unregister(String type) {
        return configurationDeserializers.remove(type);
    }

    public ConfigurationDeserializer<?> get(String type) {
        return configurationDeserializers.get(type);
    }

    public void clear() {
        configurationDeserializers.clear();
    }

    @Override
    public Object deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        ConfigurationDeserializer<?> configurationDeserializer = get(configurationSerialization.getType());

        if (configurationDeserializer == null) {
            configurationDeserializer = defaultConfigurationDeserializer;

            if (configurationDeserializer == null) {
                return configurationSerialization;
            }
        }

        return configurationDeserializer.deserialize(configurationSerialization);
    }
}

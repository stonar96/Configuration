package com.vanillage.utils.configuration.serializers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerRegistry implements ConfigurationSerializer<Object> {
    private final Map<Class<?>, ConfigurationSerializer<?>> configurationSerializers = new HashMap<>();
    private ConfigurationSerializer<Object> defaultConfigurationSerializer;

    public Map<Class<?>, ConfigurationSerializer<?>> getConfigurationSerializers() {
        return Collections.unmodifiableMap(configurationSerializers);
    }

    public ConfigurationSerializer<Object> getDefaultConfigurationSerializer() {
        return defaultConfigurationSerializer;
    }

    public void setDefaultConfigurationSerializer(ConfigurationSerializer<Object> defaultConfigurationSerializer) {
        this.defaultConfigurationSerializer = defaultConfigurationSerializer;
    }

    public <T> ConfigurationSerializer<?> register(Class<T> clazz, ConfigurationSerializer<? super T> configurationSerializer) {
        if (configurationSerializer == null) {
            throw new IllegalArgumentException("configurationSerializer cannot be null");
        }

        return configurationSerializers.put(clazz, configurationSerializer);
    }

    @SuppressWarnings("unchecked")
    public <T> ConfigurationSerializer<? super T> unregister(Class<T> clazz) {
        return (ConfigurationSerializer<? super T>) configurationSerializers.remove(clazz);
    }

    @SuppressWarnings("unchecked")
    public <T> ConfigurationSerializer<? super T> get(Class<T> clazz) {
        return (ConfigurationSerializer<? super T>) configurationSerializers.get(clazz);
    }

    public void clear() {
        configurationSerializers.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ConfigurationSerialization serialize(Object object) {
        @SuppressWarnings("rawtypes")
        ConfigurationSerializer configurationSerializer = get(object == null ? null : object.getClass());

        if (configurationSerializer == null) {
            configurationSerializer = defaultConfigurationSerializer;

            if (configurationSerializer == null) {
                if (/*object != null && object.getClass().equals(ConfigurationSerialization.class)*//*class ConfigurationSerialization is final*/object instanceof ConfigurationSerialization) {
                    return (ConfigurationSerialization) object;
                }

                return new ConfigurationSerialization(object == null ? String.valueOf(null) : object.getClass().getName(), String.valueOf(object));
            }
        }

        return configurationSerializer.serialize(object);
    }
}

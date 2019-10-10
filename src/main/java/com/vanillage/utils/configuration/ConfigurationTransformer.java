package com.vanillage.utils.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import com.vanillage.utils.configuration.conversion.ConfigurationElement;
import com.vanillage.utils.configuration.exceptions.ConfigurationTransformerException;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerBoolean;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerByte;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerCharacter;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerConfigurationElement;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerDefault;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerDouble;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerFloat;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerInteger;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerList;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerLong;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerMap;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerNull;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerRegistry;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerSet;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerShort;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerString;

public abstract class ConfigurationTransformer {
    private static final ConfigurationSerializerRegistry defaultConfigurationSerializerRegistry = new ConfigurationSerializerRegistry();

    static {
        defaultConfigurationSerializerRegistry.setDefaultConfigurationSerializer(new ConfigurationSerializerDefault());
        defaultConfigurationSerializerRegistry.register(String.class, new ConfigurationSerializerString());
        @SuppressWarnings("unchecked")
        Class<HashMap<?, ?>> hashMapClass = (Class<HashMap<?, ?>>) ((Class<?>) HashMap.class);
        ConfigurationSerializerMap configurationSerializerMap = new ConfigurationSerializerMap();
        defaultConfigurationSerializerRegistry.register(hashMapClass, configurationSerializerMap);
        @SuppressWarnings("unchecked")
        Class<LinkedHashMap<?, ?>> linkedHashMapClass = (Class<LinkedHashMap<?, ?>>) ((Class<?>) LinkedHashMap.class);
        defaultConfigurationSerializerRegistry.register(linkedHashMapClass, configurationSerializerMap);
        @SuppressWarnings("unchecked")
        Class<HashSet<?>> hashSetClass = (Class<HashSet<?>>) ((Class<?>) HashSet.class);
        ConfigurationSerializerSet configurationSerializerSet = new ConfigurationSerializerSet();
        defaultConfigurationSerializerRegistry.register(hashSetClass, configurationSerializerSet);
        @SuppressWarnings("unchecked")
        Class<LinkedHashSet<?>> linkedHashSetClass = (Class<LinkedHashSet<?>>) ((Class<?>) LinkedHashSet.class);
        defaultConfigurationSerializerRegistry.register(linkedHashSetClass, configurationSerializerSet);
        @SuppressWarnings("unchecked")
        Class<LinkedList<?>> linkedListClass = (Class<LinkedList<?>>) ((Class<?>) LinkedList.class);
        ConfigurationSerializerList configurationSerializerList = new ConfigurationSerializerList();
        defaultConfigurationSerializerRegistry.register(linkedListClass, configurationSerializerList);
        @SuppressWarnings("unchecked")
        Class<ArrayList<?>> arrayListClass = (Class<ArrayList<?>>) ((Class<?>) ArrayList.class);
        defaultConfigurationSerializerRegistry.register(arrayListClass, configurationSerializerList);
        @SuppressWarnings("unchecked")
        Class<ConfigurationElement<?, ?>> configurationElementClass = (Class<ConfigurationElement<?, ?>>) ((Class<?>) ConfigurationElement.class);
        defaultConfigurationSerializerRegistry.register(configurationElementClass, new ConfigurationSerializerConfigurationElement());
        defaultConfigurationSerializerRegistry.register(null, new ConfigurationSerializerNull());
        defaultConfigurationSerializerRegistry.register(Byte.class, new ConfigurationSerializerByte());
        defaultConfigurationSerializerRegistry.register(Character.class, new ConfigurationSerializerCharacter());
        defaultConfigurationSerializerRegistry.register(Short.class, new ConfigurationSerializerShort());
        defaultConfigurationSerializerRegistry.register(Integer.class, new ConfigurationSerializerInteger());
        defaultConfigurationSerializerRegistry.register(Long.class, new ConfigurationSerializerLong());
        defaultConfigurationSerializerRegistry.register(Float.class, new ConfigurationSerializerFloat());
        defaultConfigurationSerializerRegistry.register(Double.class, new ConfigurationSerializerDouble());
        defaultConfigurationSerializerRegistry.register(Boolean.class, new ConfigurationSerializerBoolean());
    }

    public static final ConfigurationSerializerRegistry getDefaultConfigurationSerializerRegistry() {
        return defaultConfigurationSerializerRegistry;
    }

    private final ConfigurationSerializerRegistry configurationSerializerRegistry;

    public ConfigurationTransformer() {
        this(defaultConfigurationSerializerRegistry);
    }

    public ConfigurationTransformer(ConfigurationSerializerRegistry configurationSerializerRegistry) {
        if (configurationSerializerRegistry == null) {
            throw new IllegalArgumentException("configurationSerializerRegistry cannot be null");
        }

        this.configurationSerializerRegistry = configurationSerializerRegistry;
    }

    public final ConfigurationSerializerRegistry getConfigurationSerializerRegistry() {
        return configurationSerializerRegistry;
    }

    public abstract void transform(Object object, File file) throws ConfigurationTransformerException;

    public abstract String transform(Object object) throws ConfigurationTransformerException;
}

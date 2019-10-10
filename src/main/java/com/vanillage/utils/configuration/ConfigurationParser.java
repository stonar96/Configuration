package com.vanillage.utils.configuration;

import java.io.File;

import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializer;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerBoolean;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerByte;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerCharacter;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerConfigurationElement;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerDefault;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerDouble;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerFloat;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerInteger;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerList;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerLong;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerMap;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerNull;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerRegistry;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerSet;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerShort;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerString;
import com.vanillage.utils.configuration.exceptions.ConfigurationParserException;

public abstract class ConfigurationParser {
    private static final ConfigurationDeserializerRegistry defaultConfigurationDeserializerRegistry = new ConfigurationDeserializerRegistry();

    static {
        defaultConfigurationDeserializerRegistry.setDefaultConfigurationDeserializer(new ConfigurationDeserializerDefault());
        ConfigurationDeserializer<?> configurationDeserializer = new ConfigurationDeserializerString();
        defaultConfigurationDeserializerRegistry.register("String", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.lang.String", configurationDeserializer);
        configurationDeserializer = new ConfigurationDeserializerMap();
        defaultConfigurationDeserializerRegistry.register("Map", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.util.Map", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("HashMap", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.util.HashMap", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("LinkedHashMap", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.util.LinkedHashMap", configurationDeserializer);
        configurationDeserializer = new ConfigurationDeserializerSet();
        defaultConfigurationDeserializerRegistry.register("Set", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.util.Set", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("HashSet", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.util.HashSet", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("LinkedHashSet", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.util.LinkedHashSet", configurationDeserializer);
        configurationDeserializer = new ConfigurationDeserializerList();
        defaultConfigurationDeserializerRegistry.register("List", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.util.List", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("LinkedList", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.util.LinkedList", configurationDeserializer);
        configurationDeserializer = new ConfigurationDeserializerConfigurationElement();
        defaultConfigurationDeserializerRegistry.register("ConfigurationElement", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("com.vanillage.utils.configuration.ConfigurationElement", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("Element", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("null", new ConfigurationDeserializerNull());
        configurationDeserializer = new ConfigurationDeserializerByte();
        defaultConfigurationDeserializerRegistry.register("Byte", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.lang.Byte", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("byte", configurationDeserializer);
        configurationDeserializer = new ConfigurationDeserializerCharacter();
        defaultConfigurationDeserializerRegistry.register("Character", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.lang.Character", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("char", configurationDeserializer);
        configurationDeserializer = new ConfigurationDeserializerShort();
        defaultConfigurationDeserializerRegistry.register("Short", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.lang.Short", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("short", configurationDeserializer);
        configurationDeserializer = new ConfigurationDeserializerInteger();
        defaultConfigurationDeserializerRegistry.register("Integer", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.lang.Integer", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("int", configurationDeserializer);
        configurationDeserializer = new ConfigurationDeserializerLong();
        defaultConfigurationDeserializerRegistry.register("Long", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.lang.Long", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("long", configurationDeserializer);
        configurationDeserializer = new ConfigurationDeserializerFloat();
        defaultConfigurationDeserializerRegistry.register("Float", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.lang.Float", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("float", configurationDeserializer);
        configurationDeserializer = new ConfigurationDeserializerDouble();
        defaultConfigurationDeserializerRegistry.register("Double", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.lang.Double", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("double", configurationDeserializer);
        configurationDeserializer = new ConfigurationDeserializerBoolean();
        defaultConfigurationDeserializerRegistry.register("Boolean", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("java.lang.Boolean", configurationDeserializer);
        defaultConfigurationDeserializerRegistry.register("boolean", configurationDeserializer);
    }

    public static final ConfigurationDeserializerRegistry getDefaultConfigurationDeserializerRegistry() {
        return defaultConfigurationDeserializerRegistry;
    }

    private final ConfigurationDeserializerRegistry configurationDeserializerRegistry;

    public ConfigurationParser() {
        this(defaultConfigurationDeserializerRegistry);
    }

    public ConfigurationParser(ConfigurationDeserializerRegistry configurationDeserializerRegistry) {
        if (configurationDeserializerRegistry == null) {
            throw new IllegalArgumentException("configurationDeserializerRegistry cannot be null");
        }

        this.configurationDeserializerRegistry = configurationDeserializerRegistry;
    }

    public final ConfigurationDeserializerRegistry getConfigurationDeserializerRegistry() {
        return configurationDeserializerRegistry;
    }

    public abstract Object parse(File file) throws ConfigurationParserException;

    public abstract Object parse(String string) throws ConfigurationParserException;
}

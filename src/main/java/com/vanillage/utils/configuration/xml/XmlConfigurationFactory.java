package com.vanillage.utils.configuration.xml;

import com.vanillage.utils.configuration.Configuration;
import com.vanillage.utils.configuration.ConfigurationParser;
import com.vanillage.utils.configuration.ConfigurationTransformer;
import com.vanillage.utils.configuration.deserializers.ConfigurationDeserializerRegistry;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializer;
import com.vanillage.utils.configuration.serializers.ConfigurationSerializerRegistry;

public final class XmlConfigurationFactory {
    private XmlConfigurationFactory() {
        throw new AssertionError("XmlConfigurationFactory cannot be instantiated");
    }

    public static final Configuration newConfiguration() {
        return new Configuration();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final Configuration newConfigurationWithClonedRegistries() {
        ConfigurationSerializerRegistry configurationSerializerRegistry = new ConfigurationSerializerRegistry();
        ConfigurationTransformer.getDefaultConfigurationSerializerRegistry().getConfigurationSerializers().forEach((key, value) -> configurationSerializerRegistry.register((Class) key, (ConfigurationSerializer) value));
        configurationSerializerRegistry.setDefaultConfigurationSerializer(ConfigurationTransformer.getDefaultConfigurationSerializerRegistry().getDefaultConfigurationSerializer());
        ConfigurationDeserializerRegistry configurationDeserializerRegistry = new ConfigurationDeserializerRegistry();
        ConfigurationParser.getDefaultConfigurationDeserializerRegistry().getConfigurationDeserializers().forEach(configurationDeserializerRegistry::register);
        configurationDeserializerRegistry.setDefaultConfigurationDeserializer(ConfigurationParser.getDefaultConfigurationDeserializerRegistry().getDefaultConfigurationDeserializer());
        ConfigurationTransformer configurationTransformer = new XmlConfigurationTransformer(configurationSerializerRegistry);
        ConfigurationParser configurationParser = new XmlConfigurationParser(configurationDeserializerRegistry);
        return new Configuration(configurationTransformer, configurationParser);
    }
}

package com.vanillage.utils.configuration.deserializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public interface ConfigurationDeserializer<T> {
    T deserialize(ConfigurationSerialization configurationSerialization);
}

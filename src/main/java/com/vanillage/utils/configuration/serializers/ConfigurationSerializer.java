package com.vanillage.utils.configuration.serializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public interface ConfigurationSerializer<T> {
    ConfigurationSerialization serialize(T object);
}

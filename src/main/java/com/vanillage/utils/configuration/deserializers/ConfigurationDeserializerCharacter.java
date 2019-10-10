package com.vanillage.utils.configuration.deserializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerCharacter implements ConfigurationDeserializer<Character> {
    @Override
    public Character deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        return configurationSerialization.getValue() == null || configurationSerialization.getValue().length() != 1 ? 0 : configurationSerialization.getValue().charAt(0);
    }
}

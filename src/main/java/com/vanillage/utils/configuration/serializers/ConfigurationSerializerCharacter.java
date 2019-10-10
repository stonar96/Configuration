package com.vanillage.utils.configuration.serializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerCharacter implements ConfigurationSerializer<Character> {
    @Override
    public ConfigurationSerialization serialize(Character object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        return new ConfigurationSerialization("Character", String.valueOf(object));
    }
}

package com.vanillage.utils.configuration.deserializers;

import java.util.LinkedHashMap;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationDeserializerDefault implements ConfigurationDeserializer<Object> {
    @Override
    public Object deserialize(ConfigurationSerialization configurationSerialization) {
        if (configurationSerialization == null) {
            throw new IllegalArgumentException("configurationSerialization cannot be null");
        }

        if (configurationSerialization.getType() != null) {
            return configurationSerialization;
        }

        if (configurationSerialization.getMap() != null) {
            return configurationSerialization.getMap();
        } else if (configurationSerialization.getValue().isEmpty()) {
            return new LinkedHashMap<>();
        } else {
            try {
                return Integer.valueOf(configurationSerialization.getValue());
            } catch (NumberFormatException e) {

            }

            try {
                return Long.valueOf(configurationSerialization.getValue());
            } catch (NumberFormatException e) {

            }

            try {
                return Double.valueOf(configurationSerialization.getValue());
            } catch (NumberFormatException e) {

            }

            return Boolean.valueOf(configurationSerialization.getValue()) ? true : configurationSerialization.getValue().equalsIgnoreCase(String.valueOf(false)) ? false : configurationSerialization.getValue();
        }
    }
}

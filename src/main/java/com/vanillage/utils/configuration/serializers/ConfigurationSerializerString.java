package com.vanillage.utils.configuration.serializers;

import com.vanillage.utils.configuration.conversion.ConfigurationSerialization;

public final class ConfigurationSerializerString implements ConfigurationSerializer<String> {
    @Override
    public ConfigurationSerialization serialize(String object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        if (!object.isEmpty()) {
            try {
                Integer.valueOf(object);
            } catch (NumberFormatException e) {
                try {
                    Long.valueOf(object);
                } catch (NumberFormatException e1) {
                    try {
                        Double.valueOf(object);
                    } catch (NumberFormatException e2) {
                        if (!Boolean.valueOf(object) && !object.equalsIgnoreCase(String.valueOf(false))) {
                            return new ConfigurationSerialization(null, object);
                        }
                    }
                }
            }
        }

        return new ConfigurationSerialization("String", object);
    }
}

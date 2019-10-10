package com.vanillage.utils.configuration.conversion;

public final class ConfigurationElement<K, V> {
    private final K key;
    private final V value;

    public ConfigurationElement(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

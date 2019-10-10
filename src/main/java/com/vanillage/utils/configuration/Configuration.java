package com.vanillage.utils.configuration;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vanillage.utils.configuration.exceptions.ConfigurationException;
import com.vanillage.utils.configuration.exceptions.ConfigurationParserException;
import com.vanillage.utils.configuration.exceptions.ConfigurationTransformerException;
import com.vanillage.utils.configuration.xml.XmlConfigurationParser;
import com.vanillage.utils.configuration.xml.XmlConfigurationTransformer;

public final class Configuration {
    private static final Logger logger = Logger.getLogger(Configuration.class.getName());

    public static final Logger getLogger() {
        return logger;
    }

    private static final Object deepCopyValueMaps(Object object) {
        if (object instanceof Map) {
            Map<Object, Object> copy = new LinkedHashMap<>();
            ((Map<?, ?>) object).forEach((key, value) -> copy.put(key, deepCopyValueMaps(value)));
            return copy;
        }

        return object;
    }

    private static final Object deepCopyValueMapsIfAbsent(Object source, Object destination, boolean nullTreatedAsValue) {
        if (!nullTreatedAsValue && destination == null) {
            return deepCopyValueMaps(source);
        }

        if (source instanceof Map && destination instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<Object, Object> destinationMap = (Map<Object, Object>) destination;

            for (Entry<?, ?> sourceEntry : ((Map<?, ?>) source).entrySet()) {
                if (nullTreatedAsValue ? destinationMap.containsKey(sourceEntry.getKey()) : destinationMap.get(sourceEntry.getKey()) != null) {
                    if (sourceEntry.getValue() instanceof Map) {
                        deepCopyValueMapsIfAbsent(sourceEntry.getValue(), destinationMap.get(sourceEntry.getKey()), nullTreatedAsValue);
                    }
                } else {
                    destinationMap.put(sourceEntry.getKey(), deepCopyValueMaps(sourceEntry.getValue()));
                }
            }
        }

        return destination;
    }

    private final Logger log;
    private Object content = new LinkedHashMap<>();
    private final Configuration defaultConfiguration;
    private final Configuration exampleConfiguration;
    private final ConfigurationTransformer configurationTransformer;
    private final ConfigurationParser configurationParser;
    private final ConfigurationOptions configurationOptions;

    public Configuration() {
        this(logger);
    }

    public Configuration(Logger log) {
        this(log, new XmlConfigurationTransformer(), new XmlConfigurationParser());
    }

    public Configuration(ConfigurationTransformer configurationTransformer, ConfigurationParser configurationParser) {
        this(logger, configurationTransformer, configurationParser);
    }

    public Configuration(Logger log, ConfigurationTransformer configurationTransformer, ConfigurationParser configurationParser) {
        this(log, configurationTransformer, configurationParser, new ConfigurationOptions());
    }

    private Configuration(Logger log, ConfigurationTransformer configurationTransformer, ConfigurationParser configurationParser, ConfigurationOptions configurationOptions) {
        this(log, new Configuration(log, null, null, configurationTransformer, configurationParser, configurationOptions), new Configuration(log, null, null, configurationTransformer, configurationParser, configurationOptions), configurationTransformer, configurationParser, configurationOptions);
    }

    private Configuration(Logger log, Configuration defaultConfiguration, Configuration exampleConfiguration, ConfigurationTransformer configurationTransformer, ConfigurationParser configurationParser, ConfigurationOptions configurationOptions) {
        if (log == null) {
            throw new IllegalArgumentException("log cannot be null");
        }

        if (configurationTransformer == null) {
            throw new IllegalArgumentException("configurationTransformer cannot be null");
        }

        if (configurationParser == null) {
            throw new IllegalArgumentException("configurationParser cannot be null");
        }

        this.log = log;
        this.defaultConfiguration = defaultConfiguration;
        this.exampleConfiguration = exampleConfiguration;
        this.configurationTransformer = configurationTransformer;
        this.configurationParser = configurationParser;
        this.configurationOptions = configurationOptions;
    }

    public Logger getLog() {
        return log;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Configuration getDefaultConfiguration() {
        return defaultConfiguration;
    }

    public Configuration getExampleConfiguration() {
        return exampleConfiguration;
    }

    public ConfigurationTransformer getConfigurationTransformer() {
        return configurationTransformer;
    }

    public ConfigurationParser getConfigurationParser() {
        return configurationParser;
    }

    public ConfigurationOptions getConfigurationOptions() {
        return configurationOptions;
    }

    public void createSilent(File file) {
        try {
            create(file);
        } catch (ConfigurationException e) {
            log.log(Level.SEVERE, "Error while handling file '" + file.getPath() + "'", e);
        }
    }

    public void create(File file) throws ConfigurationException {
        try {
            load(file);
        } finally {
            justSave(file);
        }
    }

    public void loadSilent(File file) {
        try {
            load(file);
        } catch (ConfigurationParserException e) {
            log.log(Level.SEVERE, "Error while loading file '" + file.getPath() + "'", e);
        }
    }

    public void load(File file) throws ConfigurationParserException {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }

        try {
            content = configurationParser.parse(file);
        } catch (ConfigurationParserException e) {
            if (exampleConfiguration != null) {
                content = deepCopyValueMaps(exampleConfiguration.getContent());
            } else {
                content = new LinkedHashMap<>();
            }

            throw e;
        }
    }

    public void saveSilent(File file) {
        try {
            save(file);
        } catch (ConfigurationTransformerException e) {
            log.log(Level.SEVERE, "Error while saving file '" + file.getPath() + "'", e);
        }
    }

    public void save(File file) throws ConfigurationTransformerException {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }

        if (!file.isFile() && exampleConfiguration != null) {
            content = deepCopyValueMapsIfAbsent(exampleConfiguration.getContent(), content, configurationOptions.isNullTreatedAsValue());
        }

        justSave(file);
    }

    private void justSave(File file) throws ConfigurationTransformerException {
        if (configurationOptions.isDefaultConfigurationAddedOnSave() && defaultConfiguration != null) {
            content = deepCopyValueMapsIfAbsent(defaultConfiguration.getContent(), content, configurationOptions.isNullTreatedAsValue());
        }

        file.getAbsoluteFile().getParentFile().mkdirs();
        configurationTransformer.transform(content, file);
    }

    public Object get(Predicate<Object> predicate, Object... path) { // get, retrieve, lookup, search
        if (path == null) {
            throw new IllegalArgumentException("path cannot be null");
        }

        Map<?, ?> previous = null;
        Object object = content;

        for (int i = 0; i < path.length; i++) {
            if (object instanceof Map) {
                previous = (Map<?, ?>) object;
                object = previous.get(path[i]);
            } else {
                return defaultConfiguration == null || predicate == null || predicate.test(NoValue.INSTANCE) ? NoValue.INSTANCE : defaultConfiguration.get(predicate, path);
            }
        }

        if (object == null && (!configurationOptions.isNullTreatedAsValue() || previous != null && /*if (defaultConfiguration != null && predicate != null && !predicate.test(null) && !predicate.test(NoValue.INSTANCE)) {return defaultConfiguration.get(predicate, path);} else if (defaultConfiguration == null || predicate == null || predicate.test(null) && predicate.test(NoValue.INSTANCE)) {return previous.containsKey(path[path.length - 1]) ? null : NoValue.INSTANCE;}*/!previous.containsKey(path[path.length - 1]))) {
            object = NoValue.INSTANCE;
        }

        return defaultConfiguration == null || predicate == null || predicate.test(object) ? object : defaultConfiguration.get(predicate, path);
    }

    @SuppressWarnings("unchecked")
    public Object set(Object value, Object... path) { // insert, set, create, construct, add, put
        if (path == null) {
            throw new IllegalArgumentException("path cannot be null");
        }

        if (path.length == 0) {
            Object oldContent = content;
            content = value;
            return oldContent == null && !configurationOptions.isNullTreatedAsValue() ? NoValue.INSTANCE : oldContent;
        }

        @SuppressWarnings("rawtypes")
        Map map;
        Object object = content;
        boolean noValue = false;

        if (object instanceof Map) {
            map = (Map<?, ?>) object;
        } else {
            map = new LinkedHashMap<>();
            content = map;
            noValue = true;
        }

        for (int i = 0; i < path.length - 1; i++) {
            if (!noValue && (object = map.get(path[i])) instanceof Map) {
                map = (Map<?, ?>) object;
            } else {
                Map<?, ?> newMap = new LinkedHashMap<>();
                map.put(path[i], newMap);
                map = newMap;
                noValue = true;
            }
        }

        if (configurationOptions.isNullTreatedAsValue()) {
            if (noValue) {
                map.put(path[path.length - 1], value);
                return NoValue.INSTANCE;
            } else {
                if (map.containsKey(path[path.length - 1])) {
                    return map.put(path[path.length - 1], value);
                } else {
                    map.put(path[path.length - 1], value);
                    return NoValue.INSTANCE;
                }
            }
        } else {
            object = map.put(path[path.length - 1], value);
            return object == null ? NoValue.INSTANCE : object;
        }
    }

    /*public boolean exists(Object... path) { // exists, contains, isSet
        return false;
    }

    public Object insertIfAbsent(Object value, Object... path) { // insert, set, create, construct, add, put
        return null;
    }

    public Object delete(Object... path) { // delete, remove, reset, clear, erase
        return null;
    }*/

    public static enum NoValue {
        INSTANCE;

        @Override
        public final String toString() {
            return "NO_VALUE_INSTANCE";
        }
    }
}

package com.redhat.common.markup;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.redhat.common.utils.LoggerUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides XML, JOSN and YAML support.
 *
 * @author sfloess
 */
public enum MarkupBuilder {
    JSON(() -> {
        return new JsonFactory();
    }),
    XML(() -> {
        return new XmlFactory();
    }),
    YAML(() -> {
        return new YAMLFactory();
    });

    /**
     * Our logger.
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Return our logger.
     *
     * @return our personal logger.
     */
    protected Logger getLogger() {
        return logger;
    }

    /**
     * Used to createFactory Json factories.
     */
    private final Supplier<JsonFactory> factorySupplier;

    private MarkupBuilder(final Supplier<JsonFactory> factorySupplier) {
        this.factorySupplier = factorySupplier;
    }

    /**
     * Allows us to encapsulate a JSONFactory.
     */
    Supplier<JsonFactory> getFactorySupplier() {
        return factorySupplier;
    }

    /**
     * Encapsulated JSONFactory.
     */
    JsonFactory createFactory() {
        return getFactorySupplier().get();
    }

    /**
     * The mapper based upon the factory for the enum.
     */
    public ObjectMapper createMapper() {
        return new ObjectMapper(createFactory())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES, true);
    }

    /**
     * Converts the string denoted in the markup to a type of class.
     */
    public <T> T asType(final String str, final Class<T> klass) {
        try {
            return createMapper().readValue(str, klass);
        } catch (final Exception exception) {
            LoggerUtils.logError(getLogger(), exception, "Trouble reading a value of type [", klass, "]");

            throw new MarkupException("Trouble reading a value of type [" + klass + "]", exception);
        }
    }

    /**
     * Convert <code>str</code> to a JSONObject. Assumption here is the string is in the correct markup for the enum.
     */
    public JSONObject asJsonObject(final String str) {
        return new JSONObject(asType(str, HashMap.class));
    }

    /**
     * Takes the markup denoted in <code>str</code> and converts to a map.
     */
    public Map asMap(final String str) {
        return asType(str, HashMap.class);
    }

    /**
     * Takes the JSONObject and converts to the correct markup based upon the enum.
     */
    public String toString(final Map map) {
        try {
            return createMapper().writeValueAsString(map);
        } catch (final Exception exception) {
            LoggerUtils.logError(getLogger(), exception, "Trouble converting:\n", map);

            throw new MarkupException("Trouble writing:  " + map, exception);
        }
    }

    /**
     * Converts the JSON Object to the proper markup.
     */
    public String toString(final JSONObject jsonObject) {
        return toString(jsonObject.toMap());
    }
}

package com.redhat.common.markup;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.redhat.common.utils.LoggerUtils;
import java.util.HashMap;
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

    Supplier<JsonFactory> getFactorySupplier() {
        return factorySupplier;
    }

    JsonFactory createFactory() {
        return getFactorySupplier().get();
    }

    public ObjectMapper createMapper() {
        return new ObjectMapper(createFactory()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> T strToObject(final String str, final Class<T> klass) {
        try {
            return createMapper().readValue(str, klass);
        } catch (final Exception exception) {
            LoggerUtils.logError(getLogger(), exception, "Trouble reading a value of type [", klass, "]");

            throw new MarkupException("Trouble reading a value of type [" + klass + "]", exception);
        }
    }

    public JSONObject strToJsonObject(final String str) {
        return new JSONObject(strToObject(str, HashMap.class));
    }
}

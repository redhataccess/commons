package com.redhat.common.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jboss.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * JSON Utilities.
 *
 * @author sfloess
 */
public final class JsonUtils {
    private static final Logger logger = Logger.getLogger(JsonUtils.class);

    private static Logger getLogger() {
        return logger;
    }

    public static ObjectMapper configureMapper(final ObjectMapper objectMapper) {
        return Objects.requireNonNull(objectMapper, "Mapper cannot be null").configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper createMapper() {
        try {
            return configureMapper(new ObjectMapper());
        } catch (final NullPointerException npe) {
            throw npe;
        } catch (final Exception exception) {
            LoggerUtils.logError(getLogger(), exception, "Trouble instantiating an ObjectMapper!");

            throw new JsonException("Trouble instantiating an ObjectMapper!", exception);
        }
    }

    public static ObjectMapper createMapper(final JsonFactory jsonFactory) {
        try {
            return configureMapper(new ObjectMapper(jsonFactory));
        } catch (final NullPointerException npe) {
            throw npe;
        } catch (final Exception exception) {
            LoggerUtils.logError(getLogger(), exception, "Trouble instantiating a YAML ObjectMapper!");

            throw new JsonException("Trouble instantiating a YAML ObjectMapper!", exception);
        }
    }

    public static JsonFactory createXmlFactory() {
        try {
            return new XmlFactory();
        } catch (final Exception exception) {
            LoggerUtils.logError(getLogger(), exception, "Trouble instantiating an XML ObjectMapper!");

            throw new JsonException("Trouble instantiating an XML ObjectMapper!", exception);
        }
    }

    public static JsonFactory createYamlFactory() {
        return new YAMLFactory();
    }

    public static ObjectMapper createXmlMapper() {
        return configureMapper(createMapper(createXmlFactory()));
    }

    public static ObjectMapper createYamlMapper() {
        return configureMapper(createMapper(createYamlFactory()));
    }

    public static <T> T readValue(final ObjectMapper mapper, final String raw, final Class<T> klass) {
        try {
            return mapper.readValue(raw, klass);
        } catch (final Exception exception) {
            LoggerUtils.logError(getLogger(), exception, "Trouble reading a value of type [", klass, "]");

            throw new JsonException("Trouble reading a value of type [" + klass + "]", exception);
        }
    }

    public static JSONObject jsonStrToJsonObject(final String jsonStr) {
        return readValue(createMapper(), jsonStr, JSONObject.class);
    }

    public static <T> T xmlStrToObject(final String xmlStr, final Class<T> klass) {
        return readValue(createXmlMapper(), xmlStr, klass);
    }

    public static JSONObject xmlStrToJsonObject(final String xmlStr) {
        return new JSONObject(readValue(createXmlMapper(), xmlStr, HashMap.class));
    }

    public static <T> T yamlStrToObject(final String yamlStr, final Class<T> klass) {
        return readValue(createYamlMapper(), yamlStr, klass);
    }

    public static JSONObject yamlStrToJsonObject(final String yamlStr) {
        return new JSONObject(readValue(createYamlMapper(), yamlStr, HashMap.class));
    }

    public static <T> T jsonStrToObject(final String jsonStr, final Class<T> klass) {
        return readValue(createMapper(), jsonStr, klass);
    }

    public static <T> T jsonObjectToObject(final JSONObject json, final Class<T> klass) {
        return jsonStrToObject(Objects.requireNonNull(json, "JSON Object cannot be null!").toString(), klass);
    }

    /**
     * Convert all maps in list to JSONObjects in jsonList.
     */
    public static List<JSONObject> populateJsonList(final List<JSONObject> jsonList, final List<Map> list) {
        Objects.requireNonNull(jsonList, "Cannot have a null list to populate!");
        Objects.requireNonNull(list, "Cannot have a null list from which to copy from!");

        for (final Map map : list) {
            jsonList.add(new JSONObject(map));
        }

        return jsonList;
    }

    public static List<JSONObject> toJsonList(final List<Map> list) {
        return populateJsonList(new ArrayList<>(list.size()), Objects.requireNonNull(list, "Cannot have a null list to convert!"));
    }

    public static JSONObject populateJsonObject(final JSONObject toPopulate, final String name, final JSONArray jsonArray) {
        Objects.requireNonNull(toPopulate, "Cannot have a null JSON object to populate!");
        Strings.require(name, "Cannot have a blank/null name");
        Objects.requireNonNull(jsonArray, "Cannot have a null JSON array!");

        return toPopulate.put(name, jsonArray);
    }

    public static JSONObject toJsonObject(final String name, final JSONArray jsonArray) {
        return populateJsonObject(new JSONObject(), name, jsonArray);
    }

    public static JSONObject toJsonObject(final String name, final List<JSONObject> list) {
        return populateJsonObject(new JSONObject(), name, new JSONArray(Objects.requireNonNull(list, "Cannot have a null list!")));
    }

    public static JSONObject put(final JSONObject toPopulate, final String name, final Object value) {
        try {
            toPopulate.put(name, value);

            return toPopulate;
        } catch (final Exception exception) {
            LoggerUtils.logError(getLogger(), exception, "Trouble putting a name [", name, "] for value [", value, "]");

            throw new JsonException("Trouble putting a name [" + name + "] for value [" + value + "]", exception);
        }
    }

    public static <T> T getValue(final JSONObject json, final String name, final T defaultValue) {
        if (json.isNull(name)) {
            return defaultValue;
        }

        return (T) json.get(name);
    }

    public static <T> T getValue(final JSONObject json, final String name) {
        return getValue(json, name, null);
    }

    /**
     * Default utilities not allowed.
     */
    private JsonUtils() {
    }
}

package com.redhat.common.markup.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.common.utils.LoggerUtils;
import com.redhat.common.utils.Strings;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.redhat.common.markup.MarkupBuilder.JSON;

/**
 * JSON Utilities.
 *
 * @author sfloess
 */
public final class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static Logger getLogger() {
        return logger;
    }

    /**
     * Convert an input stream containing JSON to an object.
     *
     * @param <T>         the type to return.
     *
     * @param inputStream contains the JSON.
     * @param klass       the class of the type to return.
     *
     * @return the object from inputStream.
     *
     * @throws IOException if any problems arise converting from a String to an
     *                     Object.
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToObject(final InputStream inputStream, final Class klass) throws IOException {
        return (T) new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true).readValue(inputStream, klass);
    }

    /**
     * Convert a reader containing JSON to an object.
     *
     * @param <T>    the type to return.
     *
     * @param reader contains the JSON.
     * @param klass  the class of the type to return.
     *
     * @return the object from inputStream.
     *
     * @throws IOException if any problems arise converting from a String to an
     *                     Object.
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToObject(final Reader reader, final Class klass) throws IOException {
        return (T) new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true).readValue(reader, klass);
    }

    /**
     * Converts a JSON string to an object of type klass.
     */
    public static <T> T jsonToObject(final String jsonStr, final Class klass) throws IOException {
        return (T) new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true).readValue(jsonStr, klass);
    }

    /**
     * Converts a JSONObject into an actual instance of an object.
     */
    public static <T> T jsonToObject(final JSONObject json, final Class type) throws IOException {
        return jsonToObject(json.toString(), type);
    }

    /**
     * Converts a resource on the classpath denoted as JSON to an object.
     *
     * @param <T>      the type to return.
     *
     * @param resource the resource containing JSON.
     * @param klass    the class of the type to return.
     *
     * @return the object found in the resource.
     *
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonResourceToObject(final String resource, final Class klass) throws IOException {
        try (final InputStream is = JsonUtils.class.getClassLoader().getResourceAsStream(resource)) {
            return jsonToObject(is, klass);
        }
    }

    /**
     * Converts a resource on the classpath denoted as JSON to an object.
     *
     * @param <T>      the type to return.
     *
     * @param resource the resource containing JSON.
     * @param klass    the class of the type to return.
     *
     * @return the object found in the resource.
     *
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonStringToObject(final String json, final Class klass) throws IOException {
        try (final Reader reader = new StringReader(json)) {
            return jsonToObject(reader, klass);
        }
    }

    /**
     * Using mapper, convert obj to a JSON string representation.
     */
    @SuppressWarnings("unchecked")
    static String objectToJsonStr(final ObjectMapper mapper, final Object obj) throws IOException {
        if (null == obj) {
            return "";
        }

        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            mapper.writeValue(baos, obj);

            return baos.toString();
        }
    }

    /**
     * Convert an object to a JSON string.
     *
     * @param obj the object to serialize to a JSON string.
     *
     * @return the object from inputStream.
     *
     * @throws IOException if any problems arise converting from a String to an
     *                     Object.
     */
    @SuppressWarnings("unchecked")
    public static String objectToJsonStr(final Object obj) throws IOException {
        if (null == obj) {
            return "";
        }

        return objectToJsonStr(new ObjectMapper(), obj);
    }

    /**
     * Convert an object to a JSON string and ignore any IOException.
     *
     * @param obj the object to serialize to a JSON string.
     *
     * @return the object from inputStream.
     */
    public static String silentObjectToJsonStr(final Object obj) {
        try {
            return objectToJsonStr(obj);
        } catch (final IOException ioException) {
            return "";
        }
    }

    public static <T> T jsonObjectToObject(final JSONObject json, final Class<T> klass) {
        return JSON.asType(Objects.requireNonNull(json, "Cannot have a null JSON object!").toString(), klass);
    }

    public static <T> T cloneObject(final Object toClone) throws IOException {
        Objects.requireNonNull(toClone, "Cannot clone a null object");

        return jsonToObject(objectToJsonStr(toClone), toClone.getClass());
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

package com.redhat.common.markup.json;

import static com.redhat.common.markup.MarkupBuilder.JSON;
import com.redhat.common.utils.LoggerUtils;
import com.redhat.common.utils.Strings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static <T> T jsonObjectToObject(final JSONObject json, final Class<T> klass) {
        return JSON.asType(Objects.requireNonNull(json, "Cannot have a null JSON object!").toString(), klass);
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

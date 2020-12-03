package com.redhat.common.jee.jms;

import com.redhat.common.markup.json.JsonUtils;
import com.redhat.common.utils.Strings;
import java.io.IOException;

/**
 * Abstract base class for JSON listeners.
 *
 * @author Scot P. Floess
 */
public abstract class AbstractJsonMessageListener<T> extends AbstractMessageListener {
    /**
     * This constructor sets the class of object we process.
     */
    protected AbstractJsonMessageListener() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getMessageType() {
        return getJsonClassType().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onStringMessage(final String message) {
        logInfo("Received JSON message to process:  [", message, "]");

        Strings.require(message, "Blank message - cannot process!");

        final long startTime = System.currentTimeMillis();

        try {
            onJsonMessage(JsonUtils.jsonToObject(message, getJsonClassType()));
        } catch (final IOException ioException) {
            logError(ioException, "Trouble processing message [", message, "]");

            throw new AndreasException(400, ioException);
        } finally {
            logInfo("Processing JSON message in [", (System.currentTimeMillis() - startTime), " ms]  message [", message, "]");
        }
    }

    /**
     * Return the class of the object we process.
     */
    protected abstract Class<T> getJsonClassType();

    /**
     * Process the JSON object.
     */
    protected abstract void onJsonMessage(final T jsonMessage);
}

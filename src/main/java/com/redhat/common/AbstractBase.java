package com.redhat.common;

import com.redhat.common.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Our abstract base class.
 *
 * @author sfloess
 */
public class AbstractBase {
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
     * Log a debug statement with a stack trace.
     *
     * @param throwable an exception to be logged with <code>toLog</code>.
     * @param toLog     the debug statement to log.
     */
    protected void logDebug(final Throwable throwable, final Object... toLog) {
        LoggerUtils.logDebug(getLogger(), throwable, toLog);
    }

    /**
     * Log a debug statement.
     *
     * @param toLog the debug statement to log.
     */
    protected void logDebug(final Object... toLog) {
        LoggerUtils.logDebug(getLogger(), toLog);
    }

    /**
     * Log a message and stack trace if debug is enabled.
     *
     * @param throwable an exception to be logged with <code>toLog</code
     * @param toLog     the debug message to log.
     */
    protected void logIfDebug(final Throwable throwable, final Object... toLog) {
        LoggerUtils.logIfDebug(getLogger(), throwable, toLog);
    }

    /**
     * Log a message if debug is enabled.
     *
     * @param toLog the debug message to log.
     */
    protected void logIfDebug(final Object... toLog) {
        LoggerUtils.logIfDebug(getLogger(), toLog);
    }

    /**
     * Log an info message with a stack trace.
     *
     * @param throwable an exception to be logged with <code>toLog</code
     * @param toLog     the info message to log.
     */
    protected void logInfo(final Throwable throwable, final Object... toLog) {
        LoggerUtils.logIfInfo(getLogger(), toLog);
    }

    /**
     * Log an info message.
     *
     * @param toLog the info message to log.
     */
    protected void logInfo(final Object... toLog) {
        LoggerUtils.logIfInfo(getLogger(), toLog);
    }

    /**
     * Log an info message and stack trace if enabled.
     *
     * @param toLog the info message to log.
     */
    protected void logIfInfo(final Throwable throwable, final Object... toLog) {
        LoggerUtils.logIfInfo(getLogger(), throwable, toLog);
    }

    /**
     * Log an info message if enabled.
     *
     * @param toLog the info message to log.
     */
    protected void logIfInfo(final Object... toLog) {
        LoggerUtils.logIfInfo(getLogger(), toLog);
    }

    /**
     * Log a warning message with a stack trace.
     *
     * @param throwable an exception to be logged with <code>toLog</code
     * @param toLog     the warning message to log.
     */
    protected void logWarning(final Throwable throwable, final Object... toLog) {
        LoggerUtils.logWarning(getLogger(), throwable, toLog);
    }

    /**
     * Log a warning message.
     *
     * @param toLog the warning message to log.
     */
    protected void logWarning(final Object... toLog) {
        LoggerUtils.logWarning(getLogger(), toLog);
    }

    /**
     * Log an error message with a stack trace.
     *
     * @param throwable an exception to be logged with <code>toLog</code
     * @param toLog     the error message to log.
     */
    protected void logError(final Throwable throwable, final Object... toLog) {
        LoggerUtils.logError(getLogger(), throwable, toLog);
    }

    /**
     * Log an error message.
     *
     * @param toLog the error message to log.
     */
    protected void logError(final Object... toLog) {
        LoggerUtils.logError(getLogger(), toLog);
    }
}

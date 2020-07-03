package com.redhat.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A logging utility class.
 *
 * @author sfloess
 */
public final class LoggerUtils {
    /**
     * Our personal logger. Used in case someone decides to present a null logger!
     */
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtils.class);

    /**
     * Return our personal logger.
     *
     * @return our personal logger.
     */
    private static Logger getLogger() {
        return logger;
    }

    /**
     * Will emit a warning that the logger used is null.
     *
     * @return our personal logger.
     */
    private static Logger warnNullLogger() {
        getLogger().warn("Requested logging with null logger - using " + LoggerUtils.class.getName());

        return getLogger();
    }

    /**
     * Default constructor not allowed.
     */
    private LoggerUtils() {
    }

    /**
     * If <code>logger</code> is null, return our personal logger. Otherwise return the logger presented.
     *
     * @param logger to check for null.
     *
     * @return our personal logger if <code>logger</code>. Otherwise return the logger presented.
     */
    static Logger computeLogger(final Logger logger) {
        return null == logger ? warnNullLogger() : logger;
    }

    /**
     * Log a debug message with a stack trace.
     *
     * @param logger    the logger to use.
     * @param throwable an exception to be logged with <code>toLog</code>.
     * @param toLog     the debug message.
     */
    public static void logDebug(final Logger logger, final Throwable throwable, final Object... toLog) {
        computeLogger(logger).debug(StringUtils.join(toLog), throwable);
    }

    /**
     * Log a debug message.
     *
     * @param logger the logger to use.
     * @param toLog  the debug message.
     */
    public static void logDebug(final Logger logger, final Object... toLog) {
        computeLogger(logger).debug(StringUtils.join(toLog));
    }

    /**
     * Log a debug message.
     *
     * @param logger    the logger to use.
     * @param throwable an exception to be logged with <code>toLog</code>.
     * @param toLog     the debug message.
     */
    public static void logIfDebug(final Logger logger, final Throwable throwable, final Object... toLog) {
        if (computeLogger(logger).isDebugEnabled()) {
            logDebug(logger, throwable, toLog);
        }
    }

    /**
     * Log a debug message.
     *
     * @param logger the logger to use.
     * @param toLog  the debug message.
     */
    public static void logIfDebug(final Logger logger, final Object... toLog) {
        if (computeLogger(logger).isDebugEnabled()) {
            logDebug(logger, toLog);
        }
    }

    /**
     * Log an info message with a stack trace.
     *
     * @param logger    the logger to use.
     * @param throwable an exception to be logged with <code>toLog</code>.
     * @param toLog     the debug message.
     */
    public static void logInfo(final Logger logger, final Throwable throwable, final Object... toLog) {
        computeLogger(logger).info(StringUtils.join(toLog), throwable);
    }

    /**
     * Log an info message.
     *
     * @param logger the logger to use.
     * @param toLog  the debug message.
     */
    public static void logInfo(final Logger logger, final Object... toLog) {
        computeLogger(logger).info(StringUtils.join(toLog));
    }

    /**
     * Log an info message with a stack trace.
     *
     * @param logger    the logger to use.
     * @param throwable an exception to be logged with <code>toLog</code>.
     * @param toLog     the info message.
     */
    public static void logIfInfo(final Logger logger, final Throwable throwable, final Object... toLog) {
        if (computeLogger(logger).isInfoEnabled()) {
            logInfo(logger, throwable, toLog);
        }
    }

    /**
     * Log an info message.
     *
     * @param logger the logger to use.
     * @param toLog  the info message.
     */
    public static void logIfInfo(final Logger logger, final Object... toLog) {
        if (computeLogger(logger).isInfoEnabled()) {
            logInfo(logger, toLog);
        }
    }

    /**
     * Log a warning message with a stack trace.
     *
     * @param logger    the logger to use.
     * @param throwable an exception to be logged with <code>toLog</code>.
     * @param toLog     the warning message.
     */
    public static void logWarning(final Logger logger, final Throwable throwable, final Object... toLog) {
        computeLogger(logger).warn(StringUtils.join(toLog), throwable);
    }

    /**
     * Log a warning message.
     *
     * @param logger the logger to use.
     * @param toLog  the warning message.
     */
    public static void logWarning(final Logger logger, final Object... toLog) {
        computeLogger(logger).warn(StringUtils.join(toLog));
    }

    /**
     * Log an error message with a stack trace.
     *
     * @param logger    the logger to use.
     * @param throwable an exception to be logged with <code>toLog</code>.
     * @param toLog     the error message.
     */
    public static void logError(final Logger logger, final Throwable throwable, final Object... toLog) {
        computeLogger(logger).error(StringUtils.join(toLog), throwable);
    }

    /**
     * Log an error message.
     *
     * @param logger the logger to use.
     * @param toLog  the error message.
     */
    public static void logError(final Logger logger, final Object... toLog) {
        computeLogger(logger).error(StringUtils.join(toLog));
    }
}

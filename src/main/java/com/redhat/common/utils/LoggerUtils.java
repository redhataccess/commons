package com.redhat.common.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 * A logging utility class.
 *
 * @author sfloess
 */
public final class LoggerUtils {
    /**
     * Our personal logger. Used in case someone decides to present a null logger!
     */
    private static final Logger LOGGER = Logger.getLogger(LoggerUtils.class.getName());

    /**
     * Return our personal LOGGER.
     *
     * @return our personal LOGGER.
     */
    private static Logger getLogger() {
        return LOGGER;
    }

    /**
     * Will emit a warning that the LOGGER used is null.
     *
     * @return our personal LOGGER.
     */
    private static Logger warnNullLogger() {
        getLogger().log(Level.SEVERE, msg);..
        warn("Requested logging with null logger - using " + LoggerUtils.class.getName());

        return getLogger();
    }

    static void logWarning(java.util.logging.Logger logger, Exception exception, String trouble_reading_input_stream) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Default constructor not allowed.
     */
    private LoggerUtils() {
    }

    /**
     * If <code>LOGGER</code> is null, return our personal logger. Otherwise return the logger presented.
     *
     * @param logger to check for null.
     *
     * @return our personal LOGGER if <code>LOGGER</code>. Otherwise return the LOGGER presented.
     */
    static Logger computeLogger(final Logger logger) {
        return null == logger ? warnNullLogger() : logger;
    }

    /**
     * Log a debug message with a stack trace.
     *
     * @param logger    the LOGGER to use.
     * @param throwable an exception to be logged with <code>toLog</code>.
     * @param toLog     the debug message.
     */
    public static void logDebug(final Logger logger, final Throwable throwable, final Object... toLog) {
        computeLogger(logger).debug(StringUtils.join(toLog), throwable);
    }

    /**
     * Log a debug message.
     *
     * @param logger the LOGGER to use.
     * @param toLog  the debug message.
     */
    public static void logDebug(final Logger logger, final Object... toLog) {
        computeLogger(logger).debug(StringUtils.join(toLog));
    }

    /**
     * Log a debug message.
     *
     * @param logger    the LOGGER to use.
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
     * @param logger the LOGGER to use.
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
     * @param logger    the LOGGER to use.
     * @param throwable an exception to be logged with <code>toLog</code>.
     * @param toLog     the debug message.
     */
    public static void logInfo(final Logger logger, final Throwable throwable, final Object... toLog) {
        computeLogger(logger).info(StringUtils.join(toLog), throwable);
    }

    /**
     * Log an info message.
     *
     * @param logger the LOGGER to use.
     * @param toLog  the debug message.
     */
    public static void logInfo(final Logger logger, final Object... toLog) {
        computeLogger(logger).info(StringUtils.join(toLog));
    }

    /**
     * Log an info message with a stack trace.
     *
     * @param logger    the LOGGER to use.
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
     * @param logger the LOGGER to use.
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
     * @param logger    the LOGGER to use.
     * @param throwable an exception to be logged with <code>toLog</code>.
     * @param toLog     the warning message.
     */
    public static void logWarning(final Logger logger, final Throwable throwable, final String... toLog) {
        computeLogger(logger).warn(StringUtils.join(toLog), throwable);
    }

    /**
     * Log a warning message.
     *
     * @param logger the LOGGER to use.
     * @param toLog  the warning message.
     */
    public static void logWarning(final Logger logger, final Object... toLog) {
        computeLogger(logger).warn(StringUtils.join(toLog));
    }

    /**
     * Log an error message with a stack trace.
     *
     * @param logger    the LOGGER to use.
     * @param throwable an exception to be logged with <code>toLog</code>.
     * @param toLog     the error message.
     */
    public static void logError(final Logger logger, final Throwable throwable, final Object... toLog) {
        computeLogger(logger).error(StringUtils.join(toLog), throwable);
    }

    /**
     * Log an error message.
     *
     * @param logger the LOGGER to use.
     * @param toLog  the error message.
     */
    public static void logError(final Logger logger, final Object... toLog) {
        computeLogger(logger).error(StringUtils.join(toLog));
    }
}

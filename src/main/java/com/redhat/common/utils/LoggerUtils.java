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
        getLogger().log(Level.WARNING, "Requested logging with null logger - using " + LoggerUtils.class.getName());

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
     * If <code>logger</code> is null, return our personal logger. Otherwise return the logger presented.
     *
     * @param logger to check for null.
     *
     * @return our personal logger if <code>LOGGER</code>. Otherwise return the logger presented.
     */
    static Logger computeLogger(final Logger logger) {
        return null == logger ? warnNullLogger() : logger;
    }

    static void log(final Logger logger, final Level level, final Throwable throwable, final Object... toLog) {
        computeLogger(logger).log(level, StringUtils.join(toLog), throwable);
    }

    static void log(final Logger logger, final Level level, final Object... toLog) {
        computeLogger(logger).log(level, StringUtils.join(toLog));
    }

    /**
     * Return if <code>logger</code> is set to log level <code>level</level>.
     *
     * @param logger to check its logging level.
     * @param level  the log level to checj.
     *
     * @return if <code>logger</code> is set to log level <code>level</level> or false if not.
     */
    static boolean isLogLevel(final Logger logger, final Level level) {
        return level == computeLogger(logger).getLevel();
    }

    public static boolean isDebugLevel(final Logger logger) {
        return isLogLevel(logger, Level.FINE);
    }

    public static boolean isInfoLevel(final Logger logger) {
        return isLogLevel(logger, Level.INFO);
    }

    public static boolean isWarningLevel(final Logger logger) {
        return isLogLevel(logger, Level.WARNING);
    }

    public static boolean isErrorLevel(final Logger logger) {
        return isLogLevel(logger, Level.SEVERE);
    }

    /**
     * Log a debug message with a stack trace.
     *
     * @param logger    the LOGGER to use.
     * @param throwable an exception to be logged with <code>toLog</code>.
     * @param toLog     the debug message.
     */
    public static void logDebug(final Logger logger, final Throwable throwable, final Object... toLog) {
        log(logger, Level.FINE, StringUtils.join(toLog), throwable);
    }

    /**
     * Log a debug message.
     *
     * @param logger the LOGGER to use.
     * @param toLog  the debug message.
     */
    public static void logDebug(final Logger logger, final Object... toLog) {
        log(logger, Level.FINE, StringUtils.join(toLog));
    }

    /**
     * Log a debug message.
     *
     * @param logger    the LOGGER to use.
     * @param throwable an exception to be logged with <code>toLog</code>.
     * @param toLog     the debug message.
     */
    public static void logIfDebug(final Logger logger, final Throwable throwable, final Object... toLog) {
        if (isDebugLevel(logger)) {
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
        if (isDebugLevel(logger)) {
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
        log(logger, Level.INFO, StringUtils.join(toLog), throwable);
    }

    /**
     * Log an info message.
     *
     * @param logger the LOGGER to use.
     * @param toLog  the debug message.
     */
    public static void logInfo(final Logger logger, final Object... toLog) {
        log(logger, Level.INFO, StringUtils.join(toLog));
    }

    /**
     * Log an info message with a stack trace.
     *
     * @param logger    the LOGGER to use.
     * @param throwable an exception to be logged with <code>toLog</code>.
     * @param toLog     the info message.
     */
    public static void logIfInfo(final Logger logger, final Throwable throwable, final Object... toLog) {
        if (isInfoLevel(logger)) {
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
        if (isInfoLevel(logger)) {
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
        log(logger, Level.WARNING, StringUtils.join(toLog), throwable);
    }

    /**
     * Log a warning message.
     *
     * @param logger the LOGGER to use.
     * @param toLog  the warning message.
     */
    public static void logWarning(final Logger logger, final Object... toLog) {
        log(logger, Level.WARNING, StringUtils.join(toLog));
    }

    /**
     * Log an error message with a stack trace.
     *
     * @param logger    the LOGGER to use.
     * @param throwable an exception to be logged with <code>toLog</code>.
     * @param toLog     the error message.
     */
    public static void logError(final Logger logger, final Throwable throwable, final Object... toLog) {
        log(logger, Level.SEVERE, StringUtils.join(toLog), throwable);
    }

    /**
     * Log an error message.
     *
     * @param logger the LOGGER to use.
     * @param toLog  the error message.
     */
    public static void logError(final Logger logger, final Object... toLog) {
        log(logger, Level.SEVERE, StringUtils.join(toLog));
    }
}

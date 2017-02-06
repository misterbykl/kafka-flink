package util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * misterbykl
 * <p>
 * 5/2/17 15:21
 */
public class LogUtil {
    private static final String EXCEPTION_LOGGER_NAME = "exceptionLogger";
    private static final String ROOT_LOGGER_NAME = "rootLogger";
    public static final String SLF4J_CONFIGURATION_FILE = "slf4j.configurationFile";

    /**
     * Instantiates a new Log util.
     * <p>
     * misterbykl
     * <p>
     * 5/2/17 15:21
     */
    public LogUtil() {
    }

    /**
     * Gets logger.
     *
     * @return the logger
     * <p>
     * misterbykl
     * <p>
     * 5/2/17 15:21
     */
    private static Logger getLogger(String argLoggerName) {
        return LoggerFactory.getLogger(argLoggerName);
    }

    /**
     * Gets root logger.
     *
     * @return the root logger
     * <p>
     * misterbykl
     * <p>
     * 5/2/17 15:21
     */
    public static Logger getRootLogger() {
        return LogUtil.getLogger(LogUtil.ROOT_LOGGER_NAME);
    }

    /**
     * Gets exception logger.
     *
     * @return the exception logger
     * <p>
     * misterbykl
     * <p>
     * 5/2/17 15:21
     */
    public static Logger getExceptionLogger() {
        return LogUtil.getLogger(LogUtil.EXCEPTION_LOGGER_NAME);
    }
}

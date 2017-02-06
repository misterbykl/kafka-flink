package util;

import java.util.Properties;

/**
 * misterbykl
 * <p>
 * 5/2/17 15:21
 */
public class ApplicationUtil {
    private static final String SLF4J_PROPERTIES = "file:./conf/logback.xml";
    public static final String APPLICATION_PROPERTIES = "file:./conf/application.properties";

    /**
     * Sets system properties.
     * <p>
     * misterbykl
     * <p>
     * 5/2/17 15:21
     */
    public static void setSystemProperties() {
        Properties properties = System.getProperties();
        properties.setProperty(LogUtil.SLF4J_CONFIGURATION_FILE, ApplicationUtil.SLF4J_PROPERTIES);
    }
}

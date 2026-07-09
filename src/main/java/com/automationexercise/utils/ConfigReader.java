package com.automationexercise.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads configuration properties from config.properties on the classpath.
 * Implements lazy singleton loading.
 */
public class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties;

    private ConfigReader() {}

    /**
     * Loads properties file once and caches it.
     */
    public static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            try (InputStream inputStream = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream(CONFIG_FILE)) {
                if (inputStream == null) {
                    throw new RuntimeException("Configuration file not found: " + CONFIG_FILE);
                }
                properties.load(inputStream);
                log.info("Loaded configuration from '{}'", CONFIG_FILE);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE, e);
            }
        }
        return properties;
    }

    /** Returns a property value as String. */
    public static String get(String key) {
        String value = getProperties().getProperty(key);
        if (value == null) {
            throw new RuntimeException("Missing configuration key: " + key);
        }
        return value.trim();
    }

    /** Returns a property value as String with a default fallback. */
    public static String get(String key, String defaultValue) {
        return getProperties().getProperty(key, defaultValue).trim();
    }

    /** Returns a property value as int. */
    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    /** Returns a property value as boolean. */
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    // ──────────── Convenience accessors ────────────

    public static String getBaseUrl() {
        return get("base.url");
    }

    public static String getBrowser() {
        return get("browser", "chrome");
    }

    public static boolean isHeadless() {
        return getBoolean("headless");
    }

    public static int getExplicitWait() {
        return getInt("explicit.wait");
    }

    public static String getLoginEmail() {
        return get("login.email");
    }

    public static String getLoginPassword() {
        return get("login.password");
    }

    public static String getApiBaseUrl() {
        return get("api.base.url");
    }
}

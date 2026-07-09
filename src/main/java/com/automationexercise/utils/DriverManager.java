package com.automationexercise.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Thread-safe WebDriver Manager using ThreadLocal pattern.
 * Supports Chrome, Firefox, and Edge browsers.
 */
public class DriverManager {

    private static final Logger log = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {
        // Utility class — prevent instantiation
    }

    /**
     * Initializes and returns the WebDriver instance for the current thread.
     *
     * @param browser  Browser name: chrome | firefox | edge
     * @param headless Whether to run in headless mode
     * @return WebDriver instance
     */
    public static WebDriver initDriver(String browser, boolean headless) {
        if (driverThreadLocal.get() == null) {
            WebDriver driver = createDriver(browser.toLowerCase().trim(), headless);
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0)); // Explicit waits only
            driverThreadLocal.set(driver);
            log.info("Browser '{}' initialized (headless={})", browser, headless);
        }
        return driverThreadLocal.get();
    }

    /**
     * Returns the current thread's WebDriver instance.
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver not initialized. Call initDriver() first.");
        }
        return driver;
    }

    /**
     * Quits the WebDriver and removes it from ThreadLocal.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                log.info("Browser closed successfully.");
            } catch (Exception e) {
                log.error("Error closing browser: {}", e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    private static WebDriver createDriver(String browser, boolean headless) {
        return switch (browser) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                if (headless) options.addArguments("--headless");
                yield new FirefoxDriver(options);
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                if (headless) options.addArguments("--headless");
                yield new EdgeDriver(options);
            }
            default -> { // chrome (default)
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=1920,1080");
                }
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("--disable-notifications");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--remote-allow-origins=*");
                options.setPageLoadStrategy(PageLoadStrategy.EAGER);
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                yield new ChromeDriver(options);
            }
        };
    }
}

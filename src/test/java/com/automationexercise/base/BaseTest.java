package com.automationexercise.base;

import com.automationexercise.utils.ConfigReader;
import com.automationexercise.utils.DriverManager;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Base test class for all UI tests.
 * Handles browser lifecycle and Allure screenshot capture on failure.
 */
public abstract class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        String browser = ConfigReader.getBrowser();
        boolean headless = ConfigReader.isHeadless();
        driver = DriverManager.initDriver(browser, headless);
        driver.get(ConfigReader.getBaseUrl());
        log.info("=== Test Started: {} ===", this.getClass().getSimpleName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            log.error("TEST FAILED: {}", result.getName());
            takeScreenshot();
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            log.info("TEST PASSED: {}", result.getName());
        } else {
            log.warn("TEST SKIPPED: {}", result.getName());
        }
        DriverManager.quitDriver();
        log.info("=== Test Ended: {} ===", this.getClass().getSimpleName());
    }

    @Attachment(value = "Screenshot on Failure", type = "image/png")
    public byte[] takeScreenshot() {
        try {
            return ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            log.error("Failed to capture screenshot: {}", e.getMessage());
            return new byte[0];
        }
    }

    /**
     * Helper to navigate to the base URL.
     */
    protected void navigateToHome() {
        driver.get(ConfigReader.getBaseUrl());
    }
}

package com.automationexercise.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Centralized explicit wait utilities.
 * All waits use WebDriverWait — no Thread.sleep() usage.
 */
public class WaitUtils {

    private static final Logger log = LogManager.getLogger(WaitUtils.class);
    private static final int DEFAULT_TIMEOUT = ConfigReader.getExplicitWait();

    private WaitUtils() {}

    public static WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    public static WebDriverWait getWait(WebDriver driver, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    /**
     * Waits until the element is visible.
     */
    public static WebElement waitForVisible(WebDriver driver, By locator) {
        log.debug("Waiting for element to be visible: {}", locator);
        return getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForVisible(WebDriver driver, By locator, int timeout) {
        return getWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForVisible(WebDriver driver, WebElement element) {
        return getWait(driver).until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits until the element is clickable.
     */
    public static WebElement waitForClickable(WebDriver driver, By locator) {
        log.debug("Waiting for element to be clickable: {}", locator);
        return getWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, WebElement element) {
        return getWait(driver).until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits until element is present in DOM (not necessarily visible).
     */
    public static WebElement waitForPresence(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement waitForPresence(WebDriver driver, By locator, int timeoutSeconds) {
        return getWait(driver, timeoutSeconds).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Waits until the element is invisible/gone.
     */
    public static boolean waitForInvisible(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Waits until the element's text contains the expected string.
     */
    public static boolean waitForTextPresent(WebDriver driver, By locator, String text) {
        return getWait(driver).until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Waits until the page URL contains the expected substring.
     */
    public static boolean waitForUrlContains(WebDriver driver, String urlFragment) {
        return getWait(driver).until(ExpectedConditions.urlContains(urlFragment));
    }

    /**
     * Waits until an alert is present and returns it.
     */
    public static Alert waitForAlert(WebDriver driver) {
        return getWait(driver).until(ExpectedConditions.alertIsPresent());
    }

    /**
     * Waits until the number of elements matching the locator is greater than zero.
     */
    public static boolean waitForElementCount(WebDriver driver, By locator, int expectedCount) {
        return getWait(driver).until(
                d -> d.findElements(locator).size() >= expectedCount
        );
    }

    /**
     * Waits for a custom condition (lambda-friendly).
     */
    public static <T> T waitFor(WebDriver driver, ExpectedCondition<T> condition) {
        return getWait(driver).until(condition);
    }
}

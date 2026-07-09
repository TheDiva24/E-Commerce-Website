package com.automationexercise.base;

import com.automationexercise.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Abstract base class for all Page Objects.
 * Provides common WebDriver interactions with built-in explicit waits.
 *
 * KEY DESIGN DECISIONS:
 * 1. click() uses JS as fallback when ElementClickInterceptedException is thrown
 *    (automationexercise.com shows full-page Google Ad iframes that block normal clicks)
 * 2. dismissAds() removes overlapping ad iframes via JavaScript before sensitive interactions
 * 3. checkCheckbox() always uses JS to avoid ad-iframe interception
 * 4. scrollUpArrow is checked via element presence (not visibility) since it starts hidden
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final Logger log;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.log = LogManager.getLogger(this.getClass());
    }

    // ──────────────── Ad / Overlay Management ────────────────

    /**
     * Remove full-page Google Ad iframes that block element interactions.
     * These iframes use width:100vw; height:100vh and sit on top of page content.
     * Called proactively in sensitive interactions.
     */
    protected void dismissAds() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                "var ads = document.querySelectorAll('iframe[id^=\"aswift\"]');" +
                "for(var i=0;i<ads.length;i++){" +
                "  var s=ads[i].style;" +
                "  if(s.width==='100vw'||s.position==='absolute'||s.position==='fixed'){" +
                "    ads[i].remove();" +
                "  }" +
                "}" +
                // Also remove any fixed/absolute positioned divs that might be ad containers
                "var overlays=document.querySelectorAll('ins.adsbygoogle,div[id*=\"google_ads\"]');" +
                "for(var j=0;j<overlays.length;j++){overlays[j].remove();}"
            );
            Thread.sleep(300); // brief pause for DOM to settle
        } catch (Exception e) {
            log.debug("dismissAds: {}", e.getMessage());
        }
    }

    // ──────────────── Navigation ────────────────

    public void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    // ──────────────── Element Interactions ────────────────

    protected WebElement findElement(By locator) {
        return WaitUtils.waitForVisible(driver, locator);
    }

    protected List<WebElement> findElements(By locator) {
        WaitUtils.waitForPresence(driver, locator);
        return driver.findElements(locator);
    }

    /**
     * Click with automatic JS fallback when an ad iframe intercepts the click.
     * This handles the most common failure mode on automationexercise.com.
     */
    protected void click(By locator) {
        log.debug("Clicking element: {}", locator);
        dismissAds();
        try {
            WebElement element = WaitUtils.waitForClickable(driver, locator);
            element.click();
        } catch (ElementClickInterceptedException e) {
            log.warn("Click intercepted (ad iframe?), using JS click for: {}", locator);
            jsClick(locator);
        } catch (StaleElementReferenceException e) {
            log.warn("Stale element, retrying click: {}", locator);
            jsClick(locator);
        }
    }

    protected void click(WebElement element) {
        dismissAds();
        try {
            WaitUtils.waitForClickable(driver, element).click();
        } catch (ElementClickInterceptedException e) {
            log.warn("Click intercepted (ad iframe?), using JS click");
            jsClick(element);
        }
    }

    protected void type(By locator, String text) {
        log.debug("Typing '{}' into: {}", text, locator);
        dismissAds();
        WebElement element = WaitUtils.waitForVisible(driver, locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void typeWithoutClear(By locator, String text) {
        dismissAds();
        WaitUtils.waitForVisible(driver, locator).sendKeys(text);
    }

    protected String getText(By locator) {
        return WaitUtils.waitForVisible(driver, locator).getText().trim();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return WaitUtils.waitForVisible(driver, locator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    protected boolean isDisplayed(By locator, int timeoutSeconds) {
        try {
            return WaitUtils.waitForVisible(driver, locator, timeoutSeconds).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    // ──────────────── Select Dropdowns ────────────────

    protected void selectByVisibleText(By locator, String text) {
        WebElement element = WaitUtils.waitForVisible(driver, locator);
        new Select(element).selectByVisibleText(text);
    }

    protected void selectByValue(By locator, String value) {
        WebElement element = WaitUtils.waitForVisible(driver, locator);
        new Select(element).selectByValue(value);
    }

    // ──────────────── Scrolling ────────────────

    protected void scrollToElement(By locator) {
        WebElement element = WaitUtils.waitForPresence(driver, locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth',block:'center'});", element);
        try { Thread.sleep(300); } catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth',block:'center'});", element);
        try { Thread.sleep(300); } catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
    }

    protected void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        try { Thread.sleep(500); } catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
    }

    protected void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        try { Thread.sleep(300); } catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
    }

    protected void scrollBy(int x, int y) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(" + x + ", " + y + ");");
    }

    // ──────────────── JavaScript Clicks ────────────────

    /**
     * Directly click via JavaScript – bypasses CSS overlays and ad iframes entirely.
     */
    protected void jsClick(By locator) {
        WebElement element = WaitUtils.waitForPresence(driver, locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // ──────────────── Hover ────────────────

    protected void hoverOver(By locator) {
        WebElement element = WaitUtils.waitForVisible(driver, locator);
        new Actions(driver).moveToElement(element).perform();
    }

    protected void hoverOver(WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    // ──────────────── Alerts ────────────────

    protected void acceptAlert() {
        WaitUtils.waitForAlert(driver).accept();
    }

    protected String getAlertText() {
        return WaitUtils.waitForAlert(driver).getText();
    }

    // ──────────────── Waits ────────────────

    /**
     * Wait for element to be visible (via explicit wait) and return it.
     */
    protected WebElement waitForElement(By locator) {
        return WaitUtils.waitForVisible(driver, locator);
    }

    /**
     * Wait for element to be PRESENT in DOM (even if hidden) – used for scrollUp arrow.
     */
    protected WebElement waitForPresence(By locator) {
        return WaitUtils.waitForPresence(driver, locator);
    }

    /**
     * Check if element is present in DOM (regardless of visibility).
     */
    protected boolean isPresent(By locator) {
        try {
            WaitUtils.waitForPresence(driver, locator, 5);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean waitForUrlContains(String fragment) {
        return WaitUtils.waitForUrlContains(driver, fragment);
    }

    protected boolean isTextPresent(By locator, String text) {
        return WaitUtils.waitForTextPresent(driver, locator, text);
    }

    // ──────────────── Checkbox ────────────────

    /**
     * Check a checkbox via JavaScript to avoid ad iframe interception.
     * Google Ads on automationexercise.com/signup intercept normal checkbox clicks.
     */
    protected void checkCheckbox(By locator) {
        dismissAds();
        try {
            // Try JS click first (more reliable with overlapping iframes)
            WebElement cb = WaitUtils.waitForPresence(driver, locator);
            Boolean isChecked = (Boolean) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].checked;", cb);
            if (!Boolean.TRUE.equals(isChecked)) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cb);
            }
        } catch (Exception e) {
            log.warn("JS checkbox click failed, trying regular click: {}", e.getMessage());
            WebElement cb = WaitUtils.waitForClickable(driver, locator);
            if (!cb.isSelected()) cb.click();
        }
    }
}

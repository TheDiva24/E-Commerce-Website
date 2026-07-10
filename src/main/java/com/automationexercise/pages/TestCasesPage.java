package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Test Cases page (/test_cases)
 */
public class TestCasesPage extends BasePage {

    // The test cases page title area
    private final By testCasesHeading = By.cssSelector("div.title.text-center");
    // Individual test case panels (accordion)
    private final By testCasesPanels  = By.cssSelector("div.panel-default");
    // Fallback: any container visible on the page
    private final By testCasesSection = By.cssSelector("div.container");

    public TestCasesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Returns true if the browser is on the /test_cases URL and the page container is visible.
     */
    public boolean isTestCasesPageVisible() {
        return driver.getCurrentUrl().contains("/test_cases") && isDisplayed(testCasesSection);
    }

    /**
     * URL-only check – lighter and more reliable than waiting for a specific element.
     */
    public boolean isOnTestCasesPage() {
        return driver.getCurrentUrl().contains("/test_cases");
    }

    public int getNumberOfTestCases() {
        return findElements(testCasesPanels).size();
    }
}

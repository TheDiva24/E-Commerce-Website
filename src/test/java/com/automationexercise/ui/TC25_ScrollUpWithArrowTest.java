package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("UI Behavior")
@Feature("Scroll")
public class TC25_ScrollUpWithArrowTest extends BaseTest {

    @Test(description = "TC25: Verify Scroll Up using 'Arrow' button and Scroll Down functionality")
    @Story("Scroll up using the back-to-top arrow")
    @Severity(SeverityLevel.MINOR)
    @Description("Scroll down to footer, verify SUBSCRIPTION visible, use arrow to scroll up, verify hero text.")
    public void testScrollUpWithArrowButton() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        homePage.scrollDown();
        Assert.assertTrue(homePage.isSubscriptionHeadingVisible(), "'SUBSCRIPTION' should be visible after scroll down");

        homePage.clickScrollUpArrow();

        // Wait for scroll animation to complete
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        Assert.assertTrue(homePage.isHeroTextVisible(),
                "Hero text 'Full-Fledged practice website' should be visible after scrolling up with arrow");
    }
}

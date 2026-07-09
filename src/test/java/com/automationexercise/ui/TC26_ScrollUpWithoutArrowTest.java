package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("UI Behavior")
@Feature("Scroll")
public class TC26_ScrollUpWithoutArrowTest extends BaseTest {

    @Test(description = "TC26: Verify Scroll Up without 'Arrow' button and Scroll Down functionality")
    @Story("Scroll up using keyboard/programmatic scroll")
    @Severity(SeverityLevel.MINOR)
    @Description("Scroll down to footer, verify SUBSCRIPTION visible, scroll up without arrow, verify hero text.")
    public void testScrollUpWithoutArrowButton() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        homePage.scrollDown();
        Assert.assertTrue(homePage.isSubscriptionHeadingVisible(), "'SUBSCRIPTION' should be visible after scroll down");

        homePage.scrollUpWithKeyboard();

        // Wait for scroll animation to complete
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        Assert.assertTrue(homePage.isHeroTextVisible(),
                "Hero text should be visible after scrolling up without arrow button");
    }
}

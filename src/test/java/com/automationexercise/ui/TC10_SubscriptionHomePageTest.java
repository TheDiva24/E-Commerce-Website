package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import com.automationexercise.utils.DataGenerator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Subscription")
@Feature("Home Page Subscription")
public class TC10_SubscriptionHomePageTest extends BaseTest {

    @Test(description = "TC10: Verify Subscription in home page")
    @Story("Subscribe from home page footer")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that email subscription from home page footer works correctly.")
    public void testSubscriptionOnHomePage() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        Assert.assertTrue(homePage.isSubscriptionHeadingVisible(), "'SUBSCRIPTION' heading should be visible");

        String email = DataGenerator.generateEmail("subscribe");
        homePage.subscribeWithEmail(email);

        Assert.assertTrue(homePage.isSubscriptionSuccessVisible(),
                "'You have been successfully subscribed!' message should be visible");
    }
}

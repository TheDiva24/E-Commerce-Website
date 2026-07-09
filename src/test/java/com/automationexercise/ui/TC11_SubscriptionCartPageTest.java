package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import com.automationexercise.utils.DataGenerator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Subscription")
@Feature("Cart Page Subscription")
public class TC11_SubscriptionCartPageTest extends BaseTest {

    @Test(description = "TC11: Verify Subscription in Cart page")
    @Story("Subscribe from cart page footer")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that email subscription from the cart page footer works correctly.")
    public void testSubscriptionOnCartPage() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        CartPage cartPage = homePage.clickCart();
        Assert.assertTrue(cartPage.isSubscriptionHeadingVisible(), "'SUBSCRIPTION' heading should be visible on cart page");

        String email = DataGenerator.generateEmail("cartsub");
        cartPage.subscribeWithEmail(email);

        Assert.assertTrue(cartPage.isSubscriptionSuccessVisible(),
                "'You have been successfully subscribed!' message should be visible");
    }
}

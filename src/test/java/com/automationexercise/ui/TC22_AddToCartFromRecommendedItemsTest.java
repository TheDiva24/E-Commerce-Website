package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Cart")
@Feature("Recommended Items")
public class TC22_AddToCartFromRecommendedItemsTest extends BaseTest {

    @Test(description = "TC22: Add to cart from Recommended items")
    @Story("Add recommended item to cart")
    @Severity(SeverityLevel.MINOR)
    @Description("Verifies that products from the 'Recommended Items' section can be added to cart.")
    public void testAddToCartFromRecommendedItems() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        Assert.assertTrue(homePage.isRecommendedItemsVisible(), "'RECOMMENDED ITEMS' section should be visible");

        homePage.clickAddToCartOnFirstRecommendedItem();
        CartPage cartPage = homePage.clickViewCartFromModal();

        Assert.assertTrue(cartPage.getCartItemCount() > 0,
                "Cart should have at least one item from recommended section");
    }
}

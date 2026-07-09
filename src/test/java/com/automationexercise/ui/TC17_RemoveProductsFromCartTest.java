package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Cart")
@Feature("Remove Products")
public class TC17_RemoveProductsFromCartTest extends BaseTest {

    @Test(description = "TC17: Remove Products From Cart")
    @Story("Remove product from cart")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that a product can be removed from the shopping cart.")
    public void testRemoveProductsFromCart() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        ProductsPage productsPage = homePage.clickProducts();
        productsPage.addFirstProductToCart();
        CartPage cartPage = productsPage.clickViewCart();

        int itemsBefore = cartPage.getCartItemCount();
        Assert.assertTrue(itemsBefore > 0, "Cart should have items");

        cartPage.removeFirstProduct();

        // Allow UI to update
        try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        int itemsAfter = cartPage.getCartItemCount();
        Assert.assertTrue(itemsAfter < itemsBefore, "Cart should have fewer items after removal");
    }
}

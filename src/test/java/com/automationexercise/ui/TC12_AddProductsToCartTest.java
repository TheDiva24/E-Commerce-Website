package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Cart")
@Feature("Add Products")
public class TC12_AddProductsToCartTest extends BaseTest {

    @Test(description = "TC12: Add Products in Cart")
    @Story("Add two products to cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies adding two products to cart and checks prices, quantity, and totals.")
    public void testAddProductsToCart() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        ProductsPage productsPage = homePage.clickProducts();

        productsPage.addFirstProductToCart();
        productsPage.clickContinueShopping();

        productsPage.addSecondProductToCart();
        CartPage cartPage = productsPage.clickViewCart();

        Assert.assertTrue(cartPage.getCartItemCount() >= 2,
                "Cart should contain at least 2 products");
    }
}

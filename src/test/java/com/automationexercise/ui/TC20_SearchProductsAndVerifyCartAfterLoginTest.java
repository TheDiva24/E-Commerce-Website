package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.models.User;
import com.automationexercise.pages.*;
import com.automationexercise.utils.DataGenerator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Products")
@Feature("Search and Cart")
public class TC20_SearchProductsAndVerifyCartAfterLoginTest extends BaseTest {

    @Test(description = "TC20: Search Products and Verify Cart After Login")
    @Story("Search products, add to cart, login, verify cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that products added to cart before login remain in cart after login.")
    public void testSearchProductsAndVerifyCartAfterLogin() {
        User user = DataGenerator.generateUser();

        // First register the user
        LoginPage loginPage = new HomePage(driver).clickSignupLogin();
        SignupPage signupPage = loginPage.signupWithNameAndEmail(user.getName(), user.getEmail());
        signupPage.registerUser(user);
        signupPage.clickContinue();
        new HomePage(driver).clickLogout();

        // Search and add to cart while NOT logged in
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.clickProducts();
        Assert.assertTrue(productsPage.isAllProductsPageVisible(), "Should be on All Products page");

        productsPage.searchProduct("dress");
        Assert.assertTrue(productsPage.isSearchedProductsTitleVisible(), "'SEARCHED PRODUCTS' should be visible");
        Assert.assertTrue(productsPage.getSearchedProductCount() > 0, "Search results should be visible");

        productsPage.addFirstProductToCart();
        productsPage.clickContinueShopping();

        CartPage cartBefore = new HomePage(driver).clickCart();
        int cartCountBefore = cartBefore.getCartItemCount();
        Assert.assertTrue(cartCountBefore > 0, "Cart should have items before login");

        // Login
        LoginPage loginPage2 = new HomePage(driver).clickSignupLogin();
        loginPage2.loginWith(user.getEmail(), user.getPassword());

        // Verify cart still has items after login
        CartPage cartAfter = new HomePage(driver).clickCart();
        Assert.assertTrue(cartAfter.getCartItemCount() > 0, "Cart should still have items after login");

        // Clean up
        new HomePage(driver).clickDeleteAccount();
        new SignupPage(driver).clickContinueAfterDelete();
    }
}

package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.models.User;
import com.automationexercise.pages.*;
import com.automationexercise.utils.DataGenerator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Orders")
@Feature("Place Order")
public class TC16_PlaceOrderLoginBeforeCheckoutTest extends BaseTest {

    @Test(description = "TC16: Place Order: Login before Checkout")
    @Story("Login before adding to cart, then checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Login user first, add products to cart, proceed to checkout and place order.")
    public void testPlaceOrderLoginBeforeCheckout() {
        User user = DataGenerator.generateUser();

        // Register first
        LoginPage loginPage = new HomePage(driver).clickSignupLogin();
        SignupPage signupPage = loginPage.signupWithNameAndEmail(user.getName(), user.getEmail());
        signupPage.registerUser(user);
        signupPage.clickContinue();
        new HomePage(driver).clickLogout();

        // Login
        LoginPage loginPage2 = new HomePage(driver).clickSignupLogin();
        Assert.assertTrue(loginPage2.isLoginHeadingVisible(), "Login page should be visible");
        HomePage homePage = loginPage2.loginWith(user.getEmail(), user.getPassword());
        Assert.assertTrue(homePage.isLoggedIn(), "User should be logged in");

        ProductsPage productsPage = homePage.clickProducts();
        productsPage.addFirstProductToCart();
        productsPage.clickContinueShopping();

        CartPage cartPage = new HomePage(driver).clickCart();
        CheckoutPage checkoutPage = cartPage.clickProceedToCheckout();
        checkoutPage.enterOrderComment("Login before checkout order");
        PaymentPage paymentPage = checkoutPage.clickPlaceOrder();

        PaymentDonePage donePage = paymentPage.enterPaymentDetails(
                user.getFirstName() + " " + user.getLastName(),
                "4111111111111111", "789", "08", "2029"
        ).clickPayAndConfirmOrder();

        Assert.assertTrue(donePage.isOrderPlacedSuccessfully(), "Order should be placed successfully");

        donePage.clickContinue();
        new HomePage(driver).clickDeleteAccount();
        Assert.assertTrue(new SignupPage(driver).isAccountDeletedVisible(), "Account should be deleted");
        new SignupPage(driver).clickContinueAfterDelete();
    }
}

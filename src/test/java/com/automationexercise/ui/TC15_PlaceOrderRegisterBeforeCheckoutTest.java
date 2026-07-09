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
public class TC15_PlaceOrderRegisterBeforeCheckoutTest extends BaseTest {

    @Test(description = "TC15: Place Order: Register before Checkout")
    @Story("Register before adding to cart, then checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Registers a user first, adds products, then completes checkout.")
    public void testPlaceOrderRegisterBeforeCheckout() {
        User user = DataGenerator.generateUser();

        LoginPage loginPage = new HomePage(driver).clickSignupLogin();
        SignupPage signupPage = loginPage.signupWithNameAndEmail(user.getName(), user.getEmail());
        signupPage.registerUser(user);
        HomePage homeAfterReg = signupPage.clickContinue();
        Assert.assertTrue(homeAfterReg.isLoggedIn(), "User should be logged in");

        ProductsPage productsPage = homeAfterReg.clickProducts();
        productsPage.addFirstProductToCart();
        productsPage.clickContinueShopping();

        CartPage cartPage = new HomePage(driver).clickCart();
        CheckoutPage checkoutPage = cartPage.clickProceedToCheckout();
        Assert.assertTrue(checkoutPage.isDeliveryAddressVisible(), "Delivery address should be visible");

        checkoutPage.enterOrderComment("Pre-registration order");
        PaymentPage paymentPage = checkoutPage.clickPlaceOrder();

        PaymentDonePage donePage = paymentPage.enterPaymentDetails(
                user.getFirstName() + " " + user.getLastName(),
                "4111111111111111", "456", "06", "2027"
        ).clickPayAndConfirmOrder();

        Assert.assertTrue(donePage.isOrderPlacedSuccessfully(), "Order should be placed successfully");

        donePage.clickContinue();
        new HomePage(driver).clickDeleteAccount();
        Assert.assertTrue(new SignupPage(driver).isAccountDeletedVisible(), "Account should be deleted");
        new SignupPage(driver).clickContinueAfterDelete();
    }
}

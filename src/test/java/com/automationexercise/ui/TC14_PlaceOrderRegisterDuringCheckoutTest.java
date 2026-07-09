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
public class TC14_PlaceOrderRegisterDuringCheckoutTest extends BaseTest {

    @Test(description = "TC14: Place Order: Register while Checkout")
    @Story("Register during checkout and place order")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Adds products to cart, proceeds to checkout, registers, then completes order.")
    public void testPlaceOrderRegisterWhileCheckout() {
        User user = DataGenerator.generateUser();

        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        ProductsPage productsPage = homePage.clickProducts();
        productsPage.addFirstProductToCart();
        productsPage.clickContinueShopping();

        CartPage cartPage = new HomePage(driver).clickCart();
        CheckoutPage checkoutPage = cartPage.clickProceedToCheckout();

        LoginPage loginPage = cartPage.clickRegisterLogin();
        SignupPage signupPage = loginPage.signupWithNameAndEmail(user.getName(), user.getEmail());
        signupPage.registerUser(user);
        signupPage.clickContinue();

        CartPage cartPage2 = new HomePage(driver).clickCart();
        checkoutPage = cartPage2.clickProceedToCheckout();
        Assert.assertTrue(checkoutPage.isDeliveryAddressVisible(), "Delivery address should be visible");

        checkoutPage.enterOrderComment("Automation test order");
        PaymentPage paymentPage = checkoutPage.clickPlaceOrder();

        PaymentDonePage donePage = paymentPage.enterPaymentDetails(
                user.getFirstName() + " " + user.getLastName(),
                "4111111111111111", "123", "12", "2028"
        ).clickPayAndConfirmOrder();

        Assert.assertTrue(donePage.isOrderPlacedSuccessfully(), "Order should be placed successfully");

        donePage.clickContinue();
        new HomePage(driver).clickDeleteAccount();
        Assert.assertTrue(new SignupPage(driver).isAccountDeletedVisible(), "Account should be deleted");
        new SignupPage(driver).clickContinueAfterDelete();
    }
}

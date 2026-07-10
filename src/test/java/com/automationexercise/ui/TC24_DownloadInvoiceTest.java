package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.models.User;
import com.automationexercise.pages.*;
import com.automationexercise.utils.DataGenerator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Orders")
@Feature("Invoice")
public class TC24_DownloadInvoiceTest extends BaseTest {

    @Test(description = "TC24: Download Invoice after purchase order")
    @Story("Download invoice after placing an order")
    @Severity(SeverityLevel.NORMAL)
    @Description("Completes an order and verifies the 'Download Invoice' button is available.")
    public void testDownloadInvoiceAfterOrder() {
        User user = DataGenerator.generateUser();

        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        ProductsPage productsPage = homePage.clickProducts();
        productsPage.addFirstProductToCart();
        productsPage.clickContinueShopping();

        CartPage cartPage = new HomePage(driver).clickCart();
        cartPage.clickProceedToCheckout();

        LoginPage loginPage = cartPage.clickRegisterLogin();
        SignupPage signupPage = loginPage.signupWithNameAndEmail(user.getName(), user.getEmail());
        signupPage.registerUser(user);
        signupPage.clickContinue();

        CartPage cartPage2 = new HomePage(driver).clickCart();
        CheckoutPage checkoutPage = cartPage2.clickProceedToCheckout();
        checkoutPage.enterOrderComment("Invoice download test order");
        PaymentPage paymentPage = checkoutPage.clickPlaceOrder();

        PaymentDonePage donePage = paymentPage.enterPaymentDetails(
                user.getFirstName() + " " + user.getLastName(),
                "4111111111111111", "321", "10", "2030"
        ).clickPayAndConfirmOrder();

        Assert.assertTrue(donePage.isOrderPlacedSuccessfully(), "Order should be placed successfully");

        // Click download invoice button
        donePage.clickDownloadInvoice();

        donePage.clickContinue();
        new HomePage(driver).clickDeleteAccount();
        Assert.assertTrue(new SignupPage(driver).isAccountDeletedVisible(), "Account should be deleted");
        new SignupPage(driver).clickContinueAfterDelete();
    }
}

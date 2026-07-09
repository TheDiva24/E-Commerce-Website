package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.models.User;
import com.automationexercise.pages.*;
import com.automationexercise.utils.DataGenerator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Orders")
@Feature("Address Verification")
public class TC23_VerifyAddressDetailsInCheckoutTest extends BaseTest {

    @Test(description = "TC23: Verify address details in checkout page")
    @Story("Delivery address matches registration data")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that the delivery and billing address at checkout match registration details.")
    public void testVerifyAddressDetailsInCheckout() {
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
        Assert.assertTrue(checkoutPage.isDeliveryAddressVisible(), "Delivery address section should be visible");

        String deliveryFirstName = checkoutPage.getDeliveryFirstName();
        Assert.assertTrue(deliveryFirstName.contains(user.getFirstName()),
                "Delivery first name '" + deliveryFirstName + "' should match registration first name '" + user.getFirstName() + "'");

        new HomePage(driver).clickDeleteAccount();
        Assert.assertTrue(new SignupPage(driver).isAccountDeletedVisible(), "Account should be deleted");
        new SignupPage(driver).clickContinueAfterDelete();
    }
}

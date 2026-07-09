package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.models.User;
import com.automationexercise.pages.*;
import com.automationexercise.utils.DataGenerator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("User Authentication")
@Feature("Registration")
public class TC05_RegisterWithExistingEmailTest extends BaseTest {

    @Test(description = "TC05: Register User with existing email")
    @Story("Cannot register with duplicate email")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that proper error is shown when trying to register with an already existing email.")
    public void testRegisterWithExistingEmail() {
        User user = DataGenerator.generateUser();

        // First registration
        LoginPage loginPage = new HomePage(driver).clickSignupLogin();
        SignupPage signupPage = loginPage.signupWithNameAndEmail(user.getName(), user.getEmail());
        signupPage.registerUser(user);
        SignupPage sp = new SignupPage(driver);
        Assert.assertTrue(sp.isAccountCreatedVisible(), "Account should be created first time");
        sp.clickContinue();
        new HomePage(driver).clickDeleteAccount();
        sp.clickContinueAfterDelete();

        // Register a fresh user to test duplicate email
        User user2 = DataGenerator.generateUser();
        LoginPage loginPage2 = new HomePage(driver).clickSignupLogin();
        SignupPage signupPage2 = loginPage2.signupWithNameAndEmail(user2.getName(), user2.getEmail());
        signupPage2.registerUser(user2);
        new SignupPage(driver).clickContinue();
        new HomePage(driver).clickLogout();

        // Now attempt to register with the same email
        LoginPage loginPage3 = new HomePage(driver).clickSignupLogin();
        loginPage3.signupWithExistingEmail("Another Name", user2.getEmail());

        String error = loginPage3.getSignupErrorMessage();
        Assert.assertTrue(error.contains("Email Address already exist!"),
                "Error 'Email Address already exist!' should be visible. Actual: " + error);
    }
}

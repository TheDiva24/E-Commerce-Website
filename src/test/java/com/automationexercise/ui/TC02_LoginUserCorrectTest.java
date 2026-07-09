package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.models.User;
import com.automationexercise.pages.*;
import com.automationexercise.utils.DataGenerator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("User Authentication")
@Feature("Login")
public class TC02_LoginUserCorrectTest extends BaseTest {

    @Test(description = "TC02: Login User with correct email and password")
    @Story("Login with valid credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies that a user can login with correct credentials and then delete account.")
    public void testLoginWithCorrectCredentials() {
        User user = DataGenerator.generateUser();

        // First register the user
        LoginPage loginPage = new HomePage(driver).clickSignupLogin();
        SignupPage signupPage = loginPage.signupWithNameAndEmail(user.getName(), user.getEmail());
        signupPage.registerUser(user);
        HomePage homeAfterReg = signupPage.clickContinue();
        homeAfterReg.clickLogout();

        // Now login with that user
        loginPage = new HomePage(driver).clickSignupLogin();
        Assert.assertTrue(loginPage.isLoginHeadingVisible(), "'Login to your account' should be visible");

        HomePage homeAfterLogin = loginPage.loginWith(user.getEmail(), user.getPassword());
        Assert.assertTrue(homeAfterLogin.isLoggedIn(), "User should be logged in");

        homeAfterLogin.clickDeleteAccount();
        Assert.assertTrue(signupPage.isAccountDeletedVisible(), "'ACCOUNT DELETED!' should be visible");
    }
}

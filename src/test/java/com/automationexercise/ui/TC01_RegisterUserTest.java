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
public class TC01_RegisterUserTest extends BaseTest {

    @Test(description = "TC01: Register User")
    @Story("Register a new user successfully")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies that a new user can register, account is created, and account can be deleted.")
    public void testRegisterUser() {
        User user = DataGenerator.generateUser();

        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        LoginPage loginPage = homePage.clickSignupLogin();
        Assert.assertTrue(loginPage.isSignupHeadingVisible(), "'New User Signup!' should be visible");

        SignupPage signupPage = loginPage.signupWithNameAndEmail(user.getName(), user.getEmail());
        Assert.assertTrue(signupPage.isAccountInfoHeadingVisible(), "'ENTER ACCOUNT INFORMATION' should be visible");

        signupPage.registerUser(user);
        Assert.assertTrue(signupPage.isAccountCreatedVisible(), "'ACCOUNT CREATED!' should be visible");

        HomePage homeAfterReg = signupPage.clickContinue();
        Assert.assertTrue(homeAfterReg.isLoggedIn(), "User should be logged in after registration");

        homeAfterReg.clickDeleteAccount();
        Assert.assertTrue(signupPage.isAccountDeletedVisible(), "'ACCOUNT DELETED!' should be visible");

        signupPage.clickContinueAfterDelete();
    }
}

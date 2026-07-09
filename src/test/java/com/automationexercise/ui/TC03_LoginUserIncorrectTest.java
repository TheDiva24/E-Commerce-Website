package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("User Authentication")
@Feature("Login")
public class TC03_LoginUserIncorrectTest extends BaseTest {

    @Test(description = "TC03: Login User with incorrect email and password")
    @Story("Login with invalid credentials")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that proper error message is shown when login fails with wrong credentials.")
    public void testLoginWithIncorrectCredentials() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        LoginPage loginPage = homePage.clickSignupLogin();
        Assert.assertTrue(loginPage.isLoginHeadingVisible(), "'Login to your account' should be visible");

        loginPage.loginWithInvalidCredentials("wrong_email@invalid.com", "WrongPassword123");
        String errorMsg = loginPage.getLoginErrorMessage();
        Assert.assertTrue(errorMsg.contains("Your email or password is incorrect!"),
                "Error message should contain 'Your email or password is incorrect!'");
    }
}

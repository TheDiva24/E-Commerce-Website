package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.models.User;
import com.automationexercise.pages.*;
import com.automationexercise.utils.DataGenerator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("User Authentication")
@Feature("Logout")
public class TC04_LogoutUserTest extends BaseTest {

    @Test(description = "TC04: Logout User")
    @Story("User can logout successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that a logged-in user can logout and is redirected to the login page.")
    public void testLogoutUser() {
        User user = DataGenerator.generateUser();

        // Register user
        LoginPage loginPage = new HomePage(driver).clickSignupLogin();
        SignupPage signupPage = loginPage.signupWithNameAndEmail(user.getName(), user.getEmail());
        signupPage.registerUser(user);
        HomePage homeAfterReg = signupPage.clickContinue();
        Assert.assertTrue(homeAfterReg.isLoggedIn(), "User should be logged in");

        homeAfterReg.clickLogout();
        LoginPage loginAfterLogout = new LoginPage(driver);
        Assert.assertTrue(loginAfterLogout.isLoginHeadingVisible(), "User should be redirected to login page after logout");
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "URL should contain '/login'");
    }
}

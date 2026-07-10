package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Login / Signup page (/login)
 */
public class LoginPage extends BasePage {

    // ──────────────── Locators ────────────────
    private final By loginHeading          = By.xpath("//h2[text()='Login to your account']");
    private final By signupHeading         = By.xpath("//h2[text()='New User Signup!']");
    private final By loginEmailField       = By.cssSelector("input[data-qa='login-email']");
    private final By loginPasswordField    = By.cssSelector("input[data-qa='login-password']");
    private final By loginButton           = By.cssSelector("button[data-qa='login-button']");
    // Error paragraph after failed login – inside div.login-form
    private final By loginErrorMsg         = By.cssSelector("div.login-form p");
    private final By signupNameField       = By.cssSelector("input[data-qa='signup-name']");
    private final By signupEmailField      = By.cssSelector("input[data-qa='signup-email']");
    private final By signupButton          = By.cssSelector("button[data-qa='signup-button']");
    // Error after trying to signup with existing email
    private final By signupErrorMsg        = By.cssSelector("div.signup-form p");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ──────────────── Verifications ────────────────

    public boolean isLoginHeadingVisible() {
        return isDisplayed(loginHeading);
    }

    public boolean isSignupHeadingVisible() {
        return isDisplayed(signupHeading);
    }

    public String getLoginErrorMessage() {
        return getText(loginErrorMsg);
    }

    public String getSignupErrorMessage() {
        return getText(signupErrorMsg);
    }

    // ──────────────── Login Actions ────────────────

    public HomePage loginWith(String email, String password) {
        log.info("Logging in with email: {}", email);
        type(loginEmailField, email);
        type(loginPasswordField, password);
        click(loginButton);
        return new HomePage(driver);
    }

    public LoginPage loginWithInvalidCredentials(String email, String password) {
        log.info("Attempting login with invalid credentials");
        type(loginEmailField, email);
        type(loginPasswordField, password);
        click(loginButton);
        return this;
    }

    // ──────────────── Signup Actions ────────────────

    public SignupPage signupWithNameAndEmail(String name, String email) {
        log.info("Signing up with name='{}' email='{}'", name, email);
        type(signupNameField, name);
        type(signupEmailField, email);
        click(signupButton);
        return new SignupPage(driver);
    }

    public LoginPage signupWithExistingEmail(String name, String email) {
        log.info("Attempting signup with existing email: {}", email);
        type(signupNameField, name);
        type(signupEmailField, email);
        click(signupButton);
        return this;
    }
}

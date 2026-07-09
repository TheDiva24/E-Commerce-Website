package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import com.automationexercise.models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Account Information page (/signup)
 * This page captures full registration details.
 */
public class SignupPage extends BasePage {

    // ──────────────── Locators ────────────────
    private final By accountInfoHeading    = By.xpath("//b[text()='Enter Account Information']");
    private final By titleMrRadio          = By.id("id_gender1");
    private final By titleMrsRadio         = By.id("id_gender2");
    private final By nameField             = By.id("name");
    private final By emailField            = By.id("email");
    private final By passwordField         = By.id("password");
    private final By dayDropdown           = By.id("days");
    private final By monthDropdown         = By.id("months");
    private final By yearDropdown          = By.id("years");
    private final By newsletterCheckbox    = By.id("newsletter");
    private final By offersCheckbox        = By.id("optin");

    // Address info
    private final By firstNameField        = By.id("first_name");
    private final By lastNameField         = By.id("last_name");
    private final By companyField          = By.id("company");
    private final By address1Field         = By.id("address1");
    private final By address2Field         = By.id("address2");
    private final By countryDropdown       = By.id("country");
    private final By stateField            = By.id("state");
    private final By cityField             = By.id("city");
    private final By zipcodeField          = By.id("zipcode");
    private final By mobileNumberField     = By.id("mobile_number");
    private final By createAccountBtn      = By.cssSelector("button[data-qa='create-account']");

    // Success page
    private final By accountCreatedHeading = By.xpath("//b[text()='Account Created!']");
    private final By continueButton        = By.cssSelector("a[data-qa='continue-button']");

    // Delete success
    private final By accountDeletedHeading = By.xpath("//b[text()='Account Deleted!']");

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    // ──────────────── Verifications ────────────────

    public boolean isAccountInfoHeadingVisible() {
        return isDisplayed(accountInfoHeading);
    }

    public boolean isAccountCreatedVisible() {
        return isDisplayed(accountCreatedHeading);
    }

    public boolean isAccountDeletedVisible() {
        return isDisplayed(accountDeletedHeading);
    }

    // ──────────────── Registration Flow ────────────────

    public SignupPage fillAccountInformation(User user) {
        log.info("Filling account information for user: {}", user.getName());

        // Select title
        if ("Mr".equalsIgnoreCase(user.getTitle())) {
            click(titleMrRadio);
        } else {
            click(titleMrsRadio);
        }

        // Name & password (email is pre-filled)
        type(passwordField, user.getPassword());

        // Date of birth
        selectByVisibleText(dayDropdown, user.getDay());
        selectByVisibleText(monthDropdown, user.getMonth());
        selectByVisibleText(yearDropdown, user.getYear());

        // Optional checkboxes
        checkCheckbox(newsletterCheckbox);
        checkCheckbox(offersCheckbox);

        return this;
    }

    public SignupPage fillAddressInformation(User user) {
        log.info("Filling address information");
        type(firstNameField, user.getFirstName());
        type(lastNameField, user.getLastName());
        type(companyField, user.getCompany());
        type(address1Field, user.getAddress1());
        type(address2Field, user.getAddress2());
        selectByVisibleText(countryDropdown, user.getCountry());
        type(stateField, user.getState());
        type(cityField, user.getCity());
        type(zipcodeField, user.getZipcode());
        type(mobileNumberField, user.getMobileNumber());
        return this;
    }

    public SignupPage clickCreateAccount() {
        log.info("Clicking Create Account button");
        click(createAccountBtn);
        return this;
    }

    public HomePage clickContinue() {
        log.info("Clicking Continue button after account creation");
        click(continueButton);
        return new HomePage(driver);
    }

    public HomePage clickContinueAfterDelete() {
        log.info("Clicking Continue button after account deletion");
        click(continueButton);
        return new HomePage(driver);
    }

    /**
     * Full registration flow: fills all details and creates account.
     */
    public SignupPage registerUser(User user) {
        fillAccountInformation(user);
        fillAddressInformation(user);
        clickCreateAccount();
        return this;
    }
}

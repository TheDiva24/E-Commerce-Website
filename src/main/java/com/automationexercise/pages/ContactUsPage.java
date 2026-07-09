package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Contact Us page (/contact_us)
 */
public class ContactUsPage extends BasePage {

    // ──────────────── Locators ────────────────
    private final By getInTouchHeading  = By.xpath("//h2[contains(text(),'Get In Touch')]");
    private final By nameField          = By.cssSelector("input[data-qa='name']");
    private final By emailField         = By.cssSelector("input[data-qa='email']");
    private final By subjectField       = By.cssSelector("input[data-qa='subject']");
    private final By messageField       = By.cssSelector("textarea[data-qa='message']");
    private final By uploadFileInput    = By.cssSelector("input[name='upload_file']");
    private final By submitButton       = By.cssSelector("input[data-qa='submit-button']");
    private final By successMessage     = By.cssSelector("div.alert-success");
    private final By homeButton         = By.cssSelector("a[href='/']");

    public ContactUsPage(WebDriver driver) {
        super(driver);
    }

    // ──────────────── Verifications ────────────────

    public boolean isGetInTouchHeadingVisible() {
        return isDisplayed(getInTouchHeading);
    }

    public boolean isSuccessMessageVisible() {
        return isDisplayed(successMessage);
    }

    // ──────────────── Actions ────────────────

    public ContactUsPage fillContactForm(String name, String email,
                                         String subject, String message) {
        log.info("Filling contact form with name='{}', email='{}'", name, email);
        type(nameField, name);
        type(emailField, email);
        type(subjectField, subject);
        type(messageField, message);
        return this;
    }

    public ContactUsPage uploadFile(String filePath) {
        log.info("Uploading file: {}", filePath);
        findElement(uploadFileInput).sendKeys(filePath);
        return this;
    }

    public ContactUsPage clickSubmitAndAcceptAlert() {
        log.info("Clicking Submit and accepting alert");
        click(submitButton);
        acceptAlert();
        return this;
    }

    public HomePage clickHome() {
        log.info("Clicking Home button from Contact Us page");
        click(homeButton);
        return new HomePage(driver);
    }
}

package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Payment page (/payment)
 */
public class PaymentPage extends BasePage {

    // ──────────────── Locators ────────────────
    private final By nameOnCardField       = By.cssSelector("input[data-qa='name-on-card']");
    private final By cardNumberField       = By.cssSelector("input[data-qa='card-number']");
    private final By cvcField              = By.cssSelector("input[data-qa='cvc']");
    private final By expiryMonthField      = By.cssSelector("input[data-qa='expiry-month']");
    private final By expiryYearField       = By.cssSelector("input[data-qa='expiry-year']");
    private final By payAndConfirmBtn      = By.cssSelector("button[data-qa='pay-button']");

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    // ──────────────── Actions ────────────────

    public PaymentPage enterPaymentDetails(String nameOnCard, String cardNumber,
                                           String cvc, String expiryMonth, String expiryYear) {
        log.info("Entering payment details for card: {}", cardNumber);
        type(nameOnCardField, nameOnCard);
        type(cardNumberField, cardNumber);
        type(cvcField, cvc);
        type(expiryMonthField, expiryMonth);
        type(expiryYearField, expiryYear);
        return this;
    }

    public PaymentDonePage clickPayAndConfirmOrder() {
        log.info("Clicking Pay and Confirm Order");
        click(payAndConfirmBtn);
        return new PaymentDonePage(driver);
    }
}

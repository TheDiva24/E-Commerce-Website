package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Payment Done / Order Success page
 */
public class PaymentDonePage extends BasePage {

    // ──────────────── Locators ────────────────
    private final By orderSuccessMsg    = By.cssSelector("div#form h2.title");
    private final By orderSuccessAlert  = By.cssSelector("div.col-sm-9.col-sm-offset-1 p");
    private final By downloadInvoiceBtn = By.cssSelector("a.btn.btn-default.check_out");
    private final By continueButton     = By.cssSelector("a[data-qa='continue-button']");

    public PaymentDonePage(WebDriver driver) {
        super(driver);
    }

    // ──────────────── Verifications ────────────────

    public boolean isOrderPlacedSuccessfully() {
        try {
            // First try the heading (using XPath to be case-insensitive)
            boolean headingVisible = isDisplayed(By.xpath("//h2[@data-qa='order-placed' or contains(text(), 'ORDER PLACED!') or contains(text(), 'Order Placed')]"));
            if (headingVisible) return true;
        } catch (Exception e) {}
        
        try {
            // Fallback: check if the success paragraph exists
            String alert = getText(By.cssSelector("div.col-sm-9 p"));
            return alert.toLowerCase().contains("successfully") || alert.toLowerCase().contains("order has been placed");
        } catch (Exception ex) {
            return false;
        }
    }

    public String getSuccessMessage() {
        try {
            return getText(By.cssSelector("div#form p"));
        } catch (Exception e) {
            return "";
        }
    }

    // ──────────────── Actions ────────────────

    public PaymentDonePage clickDownloadInvoice() {
        log.info("Clicking Download Invoice");
        click(downloadInvoiceBtn);
        return this;
    }

    public HomePage clickContinue() {
        log.info("Clicking Continue from order success page");
        click(continueButton);
        return new HomePage(driver);
    }
}

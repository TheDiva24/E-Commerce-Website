package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Checkout page (/checkout)
 */
public class CheckoutPage extends BasePage {

    // ──────────────── Locators ────────────────
    private final By deliveryAddressSection    = By.id("address_delivery");
    private final By billingAddressSection     = By.id("address_invoice");
    private final By deliveryFirstName         = By.cssSelector("#address_delivery .address_firstname");
    private final By deliveryLastName          = By.cssSelector("#address_delivery .address_lastname");
    private final By deliveryAddress1          = By.cssSelector("#address_delivery .address_address1");
    private final By deliveryCity              = By.cssSelector("#address_delivery .address_city");
    private final By deliveryState             = By.cssSelector("#address_delivery .address_state_name");
    private final By deliveryPostcode          = By.cssSelector("#address_delivery .address_postcode");
    private final By deliveryCountry           = By.cssSelector("#address_delivery .address_country_name");
    private final By deliveryPhone             = By.cssSelector("#address_delivery .address_phone");
    private final By billingFirstName          = By.cssSelector("#address_invoice .address_firstname");
    private final By billingAddress1           = By.cssSelector("#address_invoice .address_address1");
    private final By orderReviewSection        = By.cssSelector("div.cart_info");
    private final By commentTextArea           = By.cssSelector("textarea.form-control");
    private final By placeOrderButton          = By.cssSelector("a.btn.btn-default.check_out");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // ──────────────── Verifications ────────────────

    public boolean isDeliveryAddressVisible() {
        return isDisplayed(deliveryAddressSection);
    }

    public boolean isOrderReviewVisible() {
        return isDisplayed(orderReviewSection);
    }

    public String getDeliveryFirstName() {
        return getText(deliveryFirstName);
    }

    public String getBillingFirstName() {
        return getText(billingFirstName);
    }

    public String getDeliveryAddress1() {
        // Returns first address line element text
        try {
            return findElements(deliveryAddress1).get(0).getText();
        } catch (Exception e) {
            return "";
        }
    }

    // ──────────────── Actions ────────────────

    public CheckoutPage enterOrderComment(String comment) {
        log.info("Entering order comment: {}", comment);
        type(commentTextArea, comment);
        return this;
    }

    public PaymentPage clickPlaceOrder() {
        log.info("Clicking Place Order");
        click(placeOrderButton);
        return new PaymentPage(driver);
    }
}

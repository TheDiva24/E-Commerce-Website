package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object for the Shopping Cart page (/view_cart)
 * Locators verified against live HTML.
 */
public class CartPage extends BasePage {

    // ──────────────── Locators (verified from live HTML) ────────────────
    private final By cartItems             = By.cssSelector("tbody tr");
    private final By cartProductNames      = By.cssSelector("td.cart_description h4 a");
    private final By cartPrices            = By.cssSelector("td.cart_price p");
    // Quantity cell: <td class="cart_quantity"><button class="disabled" type="button">1</button></td>
    private final By cartQuantities        = By.cssSelector("td.cart_quantity button");
    private final By cartTotals            = By.cssSelector("td.cart_total p");
    private final By deleteItemButtons     = By.cssSelector("td.cart_delete a.cart_quantity_delete");
    // Proceed to checkout: <a href="/checkout" class="btn btn-default check_out">
    private final By proceedToCheckoutBtn  = By.cssSelector("a.btn.btn-default.check_out");
    // Modal when not logged-in: div#checkoutModal
    private final By checkoutModal         = By.id("checkoutModal");
    private final By registerLoginLink     = By.cssSelector("#checkoutModal a[href='/login']");
    // Empty cart indicator
    private final By emptyCartMsg          = By.cssSelector("#empty_cart");
    // Subscription (footer)
    private final By subscriptionEmail     = By.id("susbscribe_email");
    private final By subscriptionButton    = By.id("subscribe");
    private final By subscriptionSuccess   = By.cssSelector("div.alert-success");
    private final By subscriptionHeading   = By.cssSelector("div.single-widget h2");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /** Constructor that optionally navigates directly to /view_cart. */
    public CartPage(WebDriver driver, boolean navigate) {
        super(driver);
        if (navigate) {
            driver.get("https://automationexercise.com/view_cart");
        }
    }

    // ──────────────── Verifications ────────────────

    public boolean isCartPageVisible() {
        return driver.getCurrentUrl().contains("/view_cart");
    }

    public int getCartItemCount() {
        try {
            return findElements(cartItems).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isProductInCart(String productName) {
        List<WebElement> names = findElements(cartProductNames);
        return names.stream().anyMatch(e -> e.getText().equalsIgnoreCase(productName));
    }

    public String getQuantityOfFirstItem() {
        return getText(cartQuantities);
    }

    public List<String> getAllProductNames() {
        return findElements(cartProductNames).stream()
                .map(WebElement::getText)
                .toList();
    }

    public boolean isCartEmpty() {
        try {
            return isDisplayed(emptyCartMsg, 3);
        } catch (Exception e) {
            return getCartItemCount() == 0;
        }
    }

    // ──────────────── Actions ────────────────

    public CartPage removeFirstProduct() {
        log.info("Removing first product from cart");
        List<WebElement> deleteBtns = findElements(deleteItemButtons);
        deleteBtns.get(0).click();
        return this;
    }

    public CheckoutPage clickProceedToCheckout() {
        log.info("Clicking Proceed To Checkout");
        click(proceedToCheckoutBtn);
        // If not logged in, a modal appears – caller may then call clickRegisterLogin()
        return new CheckoutPage(driver);
    }

    public LoginPage clickRegisterLogin() {
        log.info("Clicking Register/Login from checkout modal");
        click(registerLoginLink);
        return new LoginPage(driver);
    }

    // ──────────────── Subscription ────────────────

    public boolean isSubscriptionHeadingVisible() {
        scrollToBottom();
        return isDisplayed(subscriptionHeading);
    }

    public void subscribeWithEmail(String email) {
        log.info("Subscribing from cart page with email: {}", email);
        scrollToBottom();
        type(subscriptionEmail, email);
        click(subscriptionButton);
    }

    public boolean isSubscriptionSuccessVisible() {
        return isDisplayed(subscriptionSuccess, 10);
    }
}

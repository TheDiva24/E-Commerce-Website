package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object for the Product Detail page (/product_details/N)
 * Locators verified against live HTML of /product_details/1.
 */
public class ProductDetailPage extends BasePage {

    // ──────────────── Locators (verified from live HTML) ────────────────
    private final By productName          = By.cssSelector("div.product-information h2");
    // Category is the 1st <p> inside product-information
    private final By productCategory      = By.cssSelector("div.product-information p:nth-of-type(1)");
    // Price: <span><span>Rs. 500</span></span>
    private final By productPrice         = By.cssSelector("div.product-information span span");
    // Availability / Condition / Brand paragraphs hold a <b> with the label text
    private final By productAvailability  = By.xpath("//div[@class='product-information']//p[b[contains(.,'Availability')]]");
    private final By productCondition     = By.xpath("//div[@class='product-information']//p[b[contains(.,'Condition')]]");
    private final By productBrand         = By.xpath("//div[@class='product-information']//p[b[contains(.,'Brand')]]");
    private final By quantityField        = By.id("quantity");
    // Add to cart: <button type="button" class="btn btn-default cart">
    private final By addToCartButton      = By.cssSelector("button.btn.btn-default.cart");
    // Modal links after clicking Add to cart
    private final By continueShoppingBtn  = By.cssSelector("button.close-modal");
    private final By viewCartLink         = By.cssSelector("#cartModal a[href='/view_cart']");

    // ──────────────── Review section ────────────────
    // Tab: <a href="#reviews">Write Your Review</a>
    private final By writeReviewTab       = By.cssSelector("a[href='#reviews']");
    private final By reviewNameField      = By.id("name");
    private final By reviewEmailField     = By.id("email");
    private final By reviewTextArea       = By.id("review");
    private final By reviewSubmitBtn      = By.id("button-review");
    private final By reviewSuccessMsg     = By.cssSelector("div.alert-success");

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    // ──────────────── Verifications ────────────────

    public boolean isProductDetailPageVisible() {
        return isDisplayed(productName);
    }

    public String getProductName() {
        return getText(productName);
    }

    public boolean isCategoryVisible() {
        return isDisplayed(productCategory);
    }

    public boolean isPriceVisible() {
        return isDisplayed(productPrice);
    }

    public boolean isAvailabilityVisible() {
        return isDisplayed(productAvailability);
    }

    public boolean isBrandVisible() {
        try {
            return findElements(By.cssSelector("div.product-information p b")).size() >= 2;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isWriteReviewVisible() {
        return isDisplayed(writeReviewTab);
    }

    public boolean isReviewSuccessVisible() {
        return isDisplayed(reviewSuccessMsg, 10);
    }

    // ──────────────── Cart Actions ────────────────

    public ProductDetailPage setQuantity(String quantity) {
        log.info("Setting product quantity to: {}", quantity);
        WebElement qtyInput = findElement(quantityField);
        qtyInput.clear();
        qtyInput.sendKeys(quantity);
        return this;
    }

    public ProductDetailPage clickAddToCart() {
        log.info("Clicking Add to Cart on product detail page");
        click(addToCartButton);
        return this;
    }

    public ProductDetailPage clickContinueShopping() {
        click(continueShoppingBtn);
        return this;
    }

    public CartPage clickViewCart() {
        log.info("Clicking View Cart from modal");
        click(viewCartLink);
        return new CartPage(driver);
    }

    // ──────────────── Review Actions ────────────────

    /**
     * Legacy method: fills and submits the review in one call.
     */
    public ProductDetailPage writeReview(String name, String email, String review) {
        log.info("Writing review by: {} ({})", name, email);
        type(reviewNameField, name);
        type(reviewEmailField, email);
        type(reviewTextArea, review);
        click(reviewSubmitBtn);
        return this;
    }

    /**
     * Click the "Write Your Review" tab to ensure the form is active.
     */
    public ProductDetailPage clickWriteReviewTab() {
        click(writeReviewTab);
        return this;
    }

    /**
     * Fill the review form without submitting.
     */
    public ProductDetailPage fillReview(String name, String email, String review) {
        log.info("Filling review form for: {}", name);
        type(reviewNameField, name);
        type(reviewEmailField, email);
        type(reviewTextArea, review);
        return this;
    }

    /**
     * Submit the already-filled review form.
     */
    public ProductDetailPage submitReview() {
        click(reviewSubmitBtn);
        return this;
    }
}

package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object for the Home Page (https://automationexercise.com/)
 * Locators verified against live HTML.
 */
public class HomePage extends BasePage {

    // ──────────────── Locators (verified from live HTML) ────────────────
    private final By logo                   = By.cssSelector("div.logo a img");
    private final By signupLoginLink        = By.cssSelector("a[href='/login']");
    private final By logoutLink             = By.cssSelector("a[href='/logout']");
    private final By deleteAccountLink      = By.cssSelector("a[href='/delete_account']");
    private final By loggedInUsername       = By.xpath("//a[contains(., 'Logged in as')]");
    private final By productsLink           = By.cssSelector("a[href='/products']");
    private final By cartLink               = By.cssSelector("a[href='/view_cart']");
    private final By contactUsLink          = By.cssSelector("a[href='/contact_us']");
    private final By testCasesLink          = By.cssSelector("a[href='/test_cases']");
    // Active slider h2 - wait for slider animation before checking
    private final By heroText               = By.cssSelector("#slider-carousel .item.active h2");
    private final By subscriptionHeading    = By.cssSelector("div.single-widget h2");
    // Note: 'susbscribe' typo is intentional – matches live site HTML
    private final By subscriptionEmail      = By.id("susbscribe_email");
    private final By subscriptionButton     = By.id("subscribe");
    // Success div is hidden by default; after subscribe the 'hide' class is removed
    private final By subscriptionSuccess    = By.cssSelector("div.alert-success");
    private final By recommendedSection     = By.cssSelector("div.recommended_items");
    // The add-to-cart inside recommended items is an <a> tag
    private final By addToCartRecommended   = By.cssSelector("div.recommended_items a.add-to-cart");
    // Continue Shopping button: class="btn btn-success close-modal btn-block"
    private final By continueShoppingBtn    = By.cssSelector("button.close-modal");
    // Cart modal view-cart link
    private final By viewCartFromModal      = By.cssSelector("#cartModal a[href='/view_cart']");
    // scrollUp plugin creates <a id="scrollUp">
    private final By scrollUpArrow          = By.id("scrollUp");
    // Women accordion collapse toggle
    private final By womenCategoryAccordion = By.cssSelector("a[href='#Women']");
    private final By viewProductLinks       = By.cssSelector("a[href*='/product_details/']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ──────────────── Verifications ────────────────

    public boolean isHomePageVisible() {
        return isDisplayed(logo);
    }

    public boolean isLoggedIn() {
        return isDisplayed(loggedInUsername, 5);
    }

    public String getLoggedInUsername() {
        String fullText = getText(loggedInUsername);
        return fullText.replace("Logged in as", "").trim();
    }

    public boolean isDeleteAccountLinkVisible() {
        return isDisplayed(deleteAccountLink, 5);
    }

    // ──────────────── Navigation Actions ────────────────

    public LoginPage clickSignupLogin() {
        log.info("Clicking Signup/Login link");
        click(signupLoginLink);
        return new LoginPage(driver);
    }

    public void clickLogout() {
        log.info("Clicking Logout");
        click(logoutLink);
    }

    public void clickDeleteAccount() {
        log.info("Clicking Delete Account");
        click(deleteAccountLink);
    }

    public ProductsPage clickProducts() {
        log.info("Clicking Products link");
        click(productsLink);
        return new ProductsPage(driver);
    }

    public CartPage clickCart() {
        log.info("Clicking Cart link");
        click(cartLink);
        return new CartPage(driver);
    }

    public ContactUsPage clickContactUs() {
        log.info("Clicking Contact Us link");
        click(contactUsLink);
        return new ContactUsPage(driver);
    }

    public TestCasesPage clickTestCases() {
        log.info("Clicking Test Cases link");
        click(testCasesLink);
        return new TestCasesPage(driver);
    }

    public ProductDetailPage clickViewProduct(int index) {
        List<WebElement> links = findElements(viewProductLinks);
        scrollToElement(links.get(index));
        links.get(index).click();
        return new ProductDetailPage(driver);
    }

    // ──────────────── Subscription ────────────────

    public boolean isSubscriptionHeadingVisible() {
        scrollToBottom();
        return isDisplayed(subscriptionHeading);
    }

    public void subscribeWithEmail(String email) {
        log.info("Subscribing with email: {}", email);
        scrollToBottom();
        type(subscriptionEmail, email);
        click(subscriptionButton);
    }

    public boolean isSubscriptionSuccessVisible() {
        return isDisplayed(subscriptionSuccess, 10);
    }

    // ──────────────── Recommended Items ────────────────

    public boolean isRecommendedItemsVisible() {
        scrollToBottom();
        return isDisplayed(recommendedSection);
    }

    public void clickAddToCartOnFirstRecommendedItem() {
        scrollToBottom();
        // The recommended items use a carousel; some slides are hidden (not interactable).
        // Use JS click on the first add-to-cart in the ACTIVE slide.
        try {
            List<WebElement> activeBtns = findElements(
                By.cssSelector("div.recommended_items .item.active a.add-to-cart"));
            if (!activeBtns.isEmpty()) {
                jsClick(activeBtns.get(0));
                return;
            }
        } catch (Exception e) {
            log.debug("Active slide selector failed, trying all buttons: {}", e.getMessage());
        }
        // Fallback: JS click the first button regardless of slide state
        List<WebElement> btns = findElements(addToCartRecommended);
        jsClick(btns.get(0));
    }


    public CartPage clickViewCartFromModal() {
        click(viewCartFromModal);
        return new CartPage(driver);
    }

    // ──────────────── Scrolling ────────────────

    public void scrollDown() {
        scrollToBottom();
    }

    public void clickScrollUpArrow() {
        // Scroll to bottom to trigger the scrollUp plugin to show the arrow
        scrollToBottom();
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        // The arrow starts hidden (display:none) – wait for it to be present in DOM,
        // then use JS click to avoid any lingering ad interference
        jsClick(scrollUpArrow);
    }


    public void scrollUpWithKeyboard() {
        scrollToTop();
    }

    public boolean isHeroTextVisible() {
        // Give the slider a moment to animate to active state
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return isDisplayed(heroText, 5);
    }

    public String getHeroText() {
        return getText(heroText);
    }
}

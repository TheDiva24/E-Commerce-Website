package com.automationexercise.pages;

import com.automationexercise.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object for the All Products page (/products)
 * Locators verified against live HTML.
 *
 * KEY NOTE: Add-to-cart on the products list is an <a> tag (not a <button>)
 * inside an overlay that appears on hover. The correct flow is:
 *   hoverOver(card) → click the overlay add-to-cart <a>.
 */
public class ProductsPage extends BasePage {

    // ──────────────── Locators (verified from live HTML) ────────────────
    private final By allProductsTitle       = By.xpath("//h2[contains(@class,'title') and contains(text(),'All Products')]");
    private final By productsList           = By.cssSelector("div.features_items");
    private final By productCards           = By.cssSelector("div.features_items .product-image-wrapper");
    // "View Product" links inside the "choose" div
    private final By viewProductButtons     = By.cssSelector("div.choose a[href*='/product_details/']");
    private final By searchInput            = By.id("search_product");
    private final By searchButton           = By.id("submit_search");
    private final By searchedProductsTitle  = By.xpath("//h2[contains(text(),'Searched Products')]");
    // Add-to-cart <a> inside .productinfo overlay
    private final By addToCartOverlayBtns   = By.cssSelector(".productinfo a.add-to-cart");
    // Continue Shopping: <button class="btn btn-success close-modal btn-block" data-dismiss="modal">
    private final By continueShoppingBtn    = By.cssSelector("button.close-modal");
    private final By viewCartFromModal      = By.cssSelector("#cartModal a[href='/view_cart']");
    private final By brandsSidebar          = By.cssSelector("div.brands_products");
    private final By brandLinks             = By.cssSelector("div.brands_products .brands-name ul li a");
    private final By brandPageTitle         = By.cssSelector("div.features_items h2.title");
    // Category accordion toggles
    private final By womenCategoryAccordion = By.cssSelector("a[href='#Women']");
    private final By womenSubCategoryLinks  = By.cssSelector("#Women a");
    private final By menCategoryAccordion   = By.cssSelector("a[href='#Men']");
    private final By menSubCategoryLinks    = By.cssSelector("#Men a");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    // ──────────────── Verifications ────────────────

    public boolean isAllProductsPageVisible() {
        // Check URL first (reliable), then verify the heading is present in DOM
        if (driver.getCurrentUrl().contains("/products")) {
            return isPresent(allProductsTitle) || isPresent(productsList);
        }
        return isDisplayed(allProductsTitle);
    }


    public boolean isProductsListVisible() {
        return isDisplayed(productsList);
    }

    public boolean isSearchedProductsTitleVisible() {
        return isDisplayed(searchedProductsTitle);
    }

    public boolean areBrandsVisible() {
        return isDisplayed(brandsSidebar);
    }

    public int getSearchedProductCount() {
        return findElements(productCards).size();
    }

    // ──────────────── Search ────────────────

    public ProductsPage searchProduct(String productName) {
        log.info("Searching for product: {}", productName);
        type(searchInput, productName);
        click(searchButton);
        return this;
    }

    // ──────────────── Product Navigation ────────────────

    public ProductDetailPage clickViewProductAtIndex(int index) {
        List<WebElement> viewBtns = findElements(viewProductButtons);
        log.info("Clicking View Product at index: {}", index);
        scrollToElement(viewBtns.get(index));
        viewBtns.get(index).click();
        return new ProductDetailPage(driver);
    }

    // ──────────────── Add to Cart (JS click – bypasses hover overlay & ad iframes) ────────────────

    /**
     * Add a product to cart using JS click on the add-to-cart <a> element.
     * The overlay approach (CSS hover) is unreliable with Selenium + Google Ads on this site.
     * All add-to-cart <a> tags have data-product-id attribute and class "add-to-cart".
     */
    public ProductsPage addFirstProductToCart() {
        log.info("Adding first product to cart via JS click");
        dismissAds();
        List<WebElement> addBtns = findElements(By.cssSelector("div.features_items a.add-to-cart"));
        jsClick(addBtns.get(0));
        return this;
    }

    public ProductsPage addSecondProductToCart() {
        log.info("Adding second product to cart via JS click");
        dismissAds();
        List<WebElement> addBtns = findElements(By.cssSelector("div.features_items a.add-to-cart"));
        // Each product has 2 buttons (info and overlay), so index 2 is the second product
        jsClick(addBtns.get(2));
        return this;
    }

    public ProductsPage addAllSearchedProductsToCart() {
        dismissAds();
        List<WebElement> addBtns = findElements(By.cssSelector("div.features_items a.add-to-cart"));
        // Each product has 2 buttons. Limit to 5 products -> 10 buttons. Stride by 2.
        int limit = Math.min(addBtns.size() / 2, 5);
        for (int i = 0; i < limit; i++) {
            try {
                jsClick(addBtns.get(i * 2));
                if (isDisplayed(continueShoppingBtn, 3)) {
                    click(continueShoppingBtn);
                    addBtns = findElements(By.cssSelector("div.features_items a.add-to-cart"));
                }
            } catch (Exception e) {
                log.warn("Failed to add product at index {}: {}", i, e.getMessage());
            }
        }
        return this;
    }



    public ProductsPage clickContinueShopping() {
        click(continueShoppingBtn);
        return this;
    }

    public CartPage clickViewCart() {
        click(viewCartFromModal);
        return new CartPage(driver);
    }

    // ──────────────── Category Navigation ────────────────

    public ProductsPage clickWomenCategory() {
        click(womenCategoryAccordion);
        return this;
    }

    public ProductsPage clickWomenSubCategory(int index) {
        List<WebElement> links = findElements(womenSubCategoryLinks);
        links.get(index).click();
        return this;
    }

    public ProductsPage clickMenCategory() {
        click(menCategoryAccordion);
        return this;
    }

    public ProductsPage clickMenSubCategory(int index) {
        List<WebElement> links = findElements(menSubCategoryLinks);
        links.get(index).click();
        return this;
    }

    public String getCategoryPageTitle() {
        return getText(By.cssSelector("div.features_items h2.title"));
    }

    // ──────────────── Brand Navigation ────────────────

    public String clickFirstBrandAndGetTitle() {
        List<WebElement> brands = findElements(brandLinks);
        String brandName = brands.get(0).getText().trim();
        // Strip trailing " (N)" count if present
        if (brandName.contains("(")) {
            brandName = brandName.substring(0, brandName.lastIndexOf("(")).trim();
        }
        log.info("Clicking brand: {}", brandName);
        brands.get(0).click();
        return brandName;
    }

    public String clickSecondBrandAndGetTitle() {
        List<WebElement> brands = findElements(brandLinks);
        String brandName = brands.get(1).getText().trim();
        if (brandName.contains("(")) {
            brandName = brandName.substring(0, brandName.lastIndexOf("(")).trim();
        }
        log.info("Clicking second brand: {}", brandName);
        brands.get(1).click();
        return brandName;
    }

    public boolean isBrandPageTitleContaining(String brandName) {
        String title = getText(brandPageTitle);
        return title.toUpperCase().contains(brandName.toUpperCase());
    }
}

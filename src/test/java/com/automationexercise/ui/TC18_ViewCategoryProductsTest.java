package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

@Epic("Products")
@Feature("Categories")
public class TC18_ViewCategoryProductsTest extends BaseTest {

    @Test(description = "TC18: View Category Products")
    @Story("Browse products by category")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that clicking a category/subcategory shows the correct products.")
    public void testViewCategoryProducts() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        // Verify categories sidebar is present
        boolean categoriesVisible = driver.findElements(
                By.cssSelector("div.left-sidebar")).size() > 0;
        Assert.assertTrue(categoriesVisible, "Categories should be visible on left sidebar");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // ── Step 1: Expand Women category accordion ──
        // Dismiss ads first, then JS-click the accordion toggle
        js.executeScript(
            "var ads=document.querySelectorAll('iframe[id^=\"aswift\"]');" +
            "for(var i=0;i<ads.length;i++) ads[i].remove();"
        );
        WebElement womenToggle = driver.findElement(By.cssSelector("a[href='#Women']"));
        js.executeScript("arguments[0].click();", womenToggle);

        // ── Step 2: Wait for sub-category links to be visible (accordion animates) ──
        By womenSubLinks = By.cssSelector("#Women a[href*='category_products']");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(womenSubLinks));
        } catch (Exception e) {
            // Try with presence if visibility fails
            wait.until(ExpectedConditions.presenceOfElementLocated(womenSubLinks));
        }

        List<WebElement> subLinks = driver.findElements(womenSubLinks);
        Assert.assertTrue(subLinks.size() > 0, "Women subcategories should expand");
        js.executeScript("arguments[0].click();", subLinks.get(0));

        // ── Step 3: Verify category products page ──
        wait.until(ExpectedConditions.urlContains("category_products"));
        By categoryTitle = By.cssSelector("div.features_items h2.title");
        wait.until(ExpectedConditions.presenceOfElementLocated(categoryTitle));
        String title = driver.findElement(categoryTitle).getText().toUpperCase();
        Assert.assertTrue(
                title.length() > 0,
                "Category page title should be shown. Actual: " + title);

        // ── Step 4: Navigate back to home ──
        driver.navigate().back();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.logo a img")));
        js.executeScript(
            "var ads=document.querySelectorAll('iframe[id^=\"aswift\"]');" +
            "for(var i=0;i<ads.length;i++) ads[i].remove();"
        );

        // ── Step 5: Expand Men category accordion ──
        WebElement menToggle = driver.findElement(By.cssSelector("a[href='#Men']"));
        js.executeScript("arguments[0].click();", menToggle);

        By menSubLinks = By.cssSelector("#Men a[href*='category_products']");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(menSubLinks));
        } catch (Exception e) {
            wait.until(ExpectedConditions.presenceOfElementLocated(menSubLinks));
        }

        List<WebElement> menLinks = driver.findElements(menSubLinks);
        Assert.assertTrue(menLinks.size() > 0, "Men subcategories should expand");
        js.executeScript("arguments[0].click();", menLinks.get(0));

        // ── Step 6: Verify Men category products ──
        wait.until(ExpectedConditions.urlContains("category_products"));
        wait.until(ExpectedConditions.presenceOfElementLocated(categoryTitle));
        String menTitle = driver.findElement(categoryTitle).getText().toUpperCase();
        Assert.assertTrue(
                menTitle.length() > 0,
                "Men category page should be loaded. Actual: " + menTitle);
    }
}

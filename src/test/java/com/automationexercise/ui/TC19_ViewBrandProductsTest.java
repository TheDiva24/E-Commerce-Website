package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Products")
@Feature("Brands")
public class TC19_ViewBrandProductsTest extends BaseTest {

    @Test(description = "TC19: View & Cart Brand Products")
    @Story("Browse products by brand")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies navigating to brand pages shows correct brand products.")
    public void testViewBrandProducts() {
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.clickProducts();

        Assert.assertTrue(productsPage.areBrandsVisible(), "Brands section should be visible on left sidebar");

        String brand1 = productsPage.clickFirstBrandAndGetTitle();
        Assert.assertTrue(productsPage.isBrandPageTitleContaining(brand1) ||
                        driver.getCurrentUrl().contains("brand_products"),
                "Should be on brand page for: " + brand1);

        // Go back to products and click second brand
        driver.navigate().back();
        productsPage = new ProductsPage(driver);
        String brand2 = productsPage.clickSecondBrandAndGetTitle();
        Assert.assertTrue(driver.getCurrentUrl().contains("brand_products"),
                "Should navigate to brand product page for: " + brand2);
    }
}

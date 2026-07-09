package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Products")
@Feature("Product Search")
public class TC09_SearchProductTest extends BaseTest {

    @Test(description = "TC09: Search Product")
    @Story("Search for a product")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that searching for a product returns relevant results.")
    public void testSearchProduct() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        ProductsPage productsPage = homePage.clickProducts();
        Assert.assertTrue(productsPage.isAllProductsPageVisible(), "Should be on All Products page");

        productsPage.searchProduct("top");
        Assert.assertTrue(productsPage.isSearchedProductsTitleVisible(), "'SEARCHED PRODUCTS' should be visible");
        Assert.assertTrue(productsPage.getSearchedProductCount() > 0,
                "At least one searched product should be visible");
    }
}

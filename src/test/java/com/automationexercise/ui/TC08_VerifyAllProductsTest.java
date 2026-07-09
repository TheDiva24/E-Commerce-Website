package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Products")
@Feature("All Products")
public class TC08_VerifyAllProductsTest extends BaseTest {

    @Test(description = "TC08: Verify All Products and product detail page")
    @Story("View all products and product detail")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies product list page loads and product detail page shows all required attributes.")
    public void testVerifyAllProductsAndProductDetail() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        ProductsPage productsPage = homePage.clickProducts();
        Assert.assertTrue(productsPage.isAllProductsPageVisible(), "'All Products' page title should be visible");
        Assert.assertTrue(productsPage.isProductsListVisible(), "Products list should be visible");

        ProductDetailPage productDetailPage = productsPage.clickViewProductAtIndex(0);
        Assert.assertTrue(productDetailPage.isProductDetailPageVisible(), "Product detail page should be open");
        Assert.assertFalse(productDetailPage.getProductName().isEmpty(), "Product name should be visible");
        Assert.assertTrue(productDetailPage.isCategoryVisible(), "Category should be visible");
        Assert.assertTrue(productDetailPage.isPriceVisible(), "Price should be visible");
    }
}

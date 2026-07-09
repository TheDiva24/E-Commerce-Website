package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Cart")
@Feature("Product Quantity")
public class TC13_VerifyProductQuantityTest extends BaseTest {

    @Test(description = "TC13: Verify Product quantity in Cart")
    @Story("Set product quantity and verify in cart")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that setting quantity to 4 on product detail page reflects correctly in cart.")
    public void testProductQuantityInCart() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        ProductsPage productsPage = homePage.clickProducts();
        ProductDetailPage productDetailPage = productsPage.clickViewProductAtIndex(0);
        Assert.assertTrue(productDetailPage.isProductDetailPageVisible(), "Product detail page should open");

        productDetailPage.setQuantity("4");
        productDetailPage.clickAddToCart();
        CartPage cartPage = productDetailPage.clickViewCart();

        Assert.assertEquals(cartPage.getQuantityOfFirstItem(), "4",
                "Product quantity in cart should be 4");
    }
}

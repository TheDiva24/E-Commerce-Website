package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Products")
@Feature("Product Review")
public class TC21_AddReviewOnProductTest extends BaseTest {

    @Test(description = "TC21: Add review on product")
    @Story("Submit a product review")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that a user can submit a review on a product and see success message.")
    public void testAddReviewOnProduct() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        ProductsPage productsPage = homePage.clickProducts();
        Assert.assertTrue(productsPage.isAllProductsPageVisible(), "Should be on All Products page");

        ProductDetailPage productDetailPage = productsPage.clickViewProductAtIndex(0);
        Assert.assertTrue(productDetailPage.isWriteReviewVisible(), "'Write Your Review' section should be visible");

        productDetailPage.writeReview(
                "Automation QA Tester",
                "reviewer@qatest.com",
                "This product is excellent! Tested via automation."
        );

        Assert.assertTrue(productDetailPage.isReviewSuccessVisible(),
                "'Thank you for your review.' should be visible after submission");
    }
}

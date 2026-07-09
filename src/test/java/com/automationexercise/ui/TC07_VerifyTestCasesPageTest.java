package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Navigation")
@Feature("Test Cases Page")
public class TC07_VerifyTestCasesPageTest extends BaseTest {

    @Test(description = "TC07: Verify Test Cases Page")
    @Story("Navigate to test cases page")
    @Severity(SeverityLevel.TRIVIAL)
    @Description("Verifies that clicking the Test Cases button navigates to the test cases page.")
    public void testVerifyTestCasesPage() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        TestCasesPage testCasesPage = homePage.clickTestCases();
        Assert.assertTrue(testCasesPage.isTestCasesPageVisible(),
                "Test Cases page heading should be visible");
        Assert.assertTrue(testCasesPage.isOnTestCasesPage(),
                "URL should contain '/test_cases'");
    }
}

package com.automationexercise.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

@Epic("Navigation")
@Feature("Contact Us")
public class TC06_ContactUsFormTest extends BaseTest {

    @Test(description = "TC06: Contact Us Form")
    @Story("Submit contact form")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that the Contact Us form can be submitted successfully with file upload.")
    public void testContactUsForm() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");

        ContactUsPage contactUsPage = homePage.clickContactUs();
        Assert.assertTrue(contactUsPage.isGetInTouchHeadingVisible(), "'GET IN TOUCH' should be visible");

        // Create a temp file for upload
        String filePath = System.getProperty("user.dir") + File.separator + "pom.xml";

        contactUsPage.fillContactForm("Test User", "testuser@qa.com",
                        "Automation Test Subject", "This is an automated test message.")
                .uploadFile(filePath)
                .clickSubmitAndAcceptAlert();

        Assert.assertTrue(contactUsPage.isSuccessMessageVisible(),
                "Success message 'Your details have been submitted successfully.' should be visible");

        HomePage homeAfterContact = contactUsPage.clickHome();
        Assert.assertTrue(homeAfterContact.isHomePageVisible(), "Should be back on home page");
    }
}

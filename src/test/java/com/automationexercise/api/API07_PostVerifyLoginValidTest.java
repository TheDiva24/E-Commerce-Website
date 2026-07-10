package com.automationexercise.api;

import com.automationexercise.base.BaseApiTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("API Tests")
@Feature("Login API")
public class API07_PostVerifyLoginValidTest extends BaseApiTest {

    private static final String TEST_EMAIL = "seleniumtesting123@gmail.com";
    private static final String TEST_PASSWORD = "Test@123";

    @Test(description = "API 7: POST To Verify Login with valid details")
    @Story("Valid login credentials return User exists")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies POST /api/verifyLogin with valid credentials returns 200 and 'User exists!'.")
    public void testVerifyLoginWithValidDetails() {
        // First create the account via API
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "Selenium Tester")
                .formParam("email", TEST_EMAIL)
                .formParam("password", TEST_PASSWORD)
                .formParam("title", "Mr")
                .formParam("birth_date", "1")
                .formParam("birth_month", "1")
                .formParam("birth_year", "1990")
                .formParam("firstname", "Selenium")
                .formParam("lastname", "Tester")
                .formParam("company", "QA Corp")
                .formParam("address1", "123 Test St")
                .formParam("address2", "")
                .formParam("country", "United States")
                .formParam("zipcode", "10001")
                .formParam("state", "New York")
                .formParam("city", "NYC")
                .formParam("mobile_number", "5551234567")
                .when()
                .post("/api/createAccount");

        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", TEST_EMAIL)
                .formParam("password", TEST_PASSWORD)
                .when()
                .post("/api/verifyLogin")
                .then()
                .extract().response();

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("User exists") || responseBody.contains("200"),
                "Response should contain 'User exists!'. Actual: " + responseBody);

        // Clean up
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", TEST_EMAIL)
                .formParam("password", TEST_PASSWORD)
                .when()
                .delete("/api/deleteAccount");
    }
}

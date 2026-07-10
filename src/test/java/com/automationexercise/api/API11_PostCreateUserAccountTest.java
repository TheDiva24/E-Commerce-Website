package com.automationexercise.api;

import com.automationexercise.base.BaseApiTest;
import com.automationexercise.utils.DataGenerator;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("API Tests")
@Feature("User Account API")
public class API11_PostCreateUserAccountTest extends BaseApiTest {

    @Test(description = "API 11: POST To Create/Register User Account")
    @Story("Create user account via API")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies POST /api/createAccount creates user and returns 201 'User created!'.")
    public void testCreateUserAccount() {
        String email = DataGenerator.generateEmail("apiuser");
        String password = "ApiTest@123";

        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "API Test User")
                .formParam("email", email)
                .formParam("password", password)
                .formParam("title", "Mr")
                .formParam("birth_date", "5")
                .formParam("birth_month", "8")
                .formParam("birth_year", "1988")
                .formParam("firstname", "Api")
                .formParam("lastname", "User")
                .formParam("company", "Test Corp")
                .formParam("address1", "789 API Ave")
                .formParam("address2", "")
                .formParam("country", "United States")
                .formParam("zipcode", "90210")
                .formParam("state", "California")
                .formParam("city", "Beverly Hills")
                .formParam("mobile_number", "5559990000")
                .when()
                .post("/api/createAccount")
                .then()
                .extract().response();

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("201") || responseBody.contains("User created"),
                "Response should contain 'User created!'. Actual: " + responseBody);

        // Clean up the created account
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .delete("/api/deleteAccount");
    }
}

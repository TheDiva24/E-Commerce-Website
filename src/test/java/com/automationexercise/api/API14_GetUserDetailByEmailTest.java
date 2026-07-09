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
public class API14_GetUserDetailByEmailTest extends BaseApiTest {

    @Test(description = "API 14: GET user account detail by email")
    @Story("GET user detail returns user JSON")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies GET /api/getUserDetailByEmail returns 200 and user detail JSON.")
    public void testGetUserDetailByEmail() {
        String email = DataGenerator.generateEmail("getuser");
        String password = "GetUser@123";

        // Create account first
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "Get Test User")
                .formParam("email", email)
                .formParam("password", password)
                .formParam("title", "Miss")
                .formParam("birth_date", "15")
                .formParam("birth_month", "3")
                .formParam("birth_year", "2000")
                .formParam("firstname", "Get")
                .formParam("lastname", "User")
                .formParam("company", "Fetch Corp")
                .formParam("address1", "Get Street")
                .formParam("address2", "")
                .formParam("country", "Canada")
                .formParam("zipcode", "M1M1M1")
                .formParam("state", "Ontario")
                .formParam("city", "Toronto")
                .formParam("mobile_number", "4161234567")
                .when()
                .post("/api/createAccount");

        // Get user detail by email
        Response response = given()
                .queryParam("email", email)
                .when()
                .get("/api/getUserDetailByEmail")
                .then()
                .statusCode(200)
                .extract().response();

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains(email) || responseBody.contains("user"),
                "Response should contain user details. Actual: " + responseBody);

        // Clean up
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .delete("/api/deleteAccount");
    }
}

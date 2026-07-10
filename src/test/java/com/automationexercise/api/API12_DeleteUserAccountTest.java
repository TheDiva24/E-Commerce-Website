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
public class API12_DeleteUserAccountTest extends BaseApiTest {

    @Test(description = "API 12: DELETE METHOD To Delete User Account")
    @Story("Delete user account via API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies DELETE /api/deleteAccount deletes user and returns 200 'Account deleted!'.")
    public void testDeleteUserAccount() {
        String email = DataGenerator.generateEmail("deluser");
        String password = "Delete@123";

        // Create account first
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "Del Test User")
                .formParam("email", email)
                .formParam("password", password)
                .formParam("title", "Mrs")
                .formParam("birth_date", "10")
                .formParam("birth_month", "5")
                .formParam("birth_year", "1992")
                .formParam("firstname", "Del")
                .formParam("lastname", "User")
                .formParam("company", "")
                .formParam("address1", "Del Street")
                .formParam("address2", "")
                .formParam("country", "United States")
                .formParam("zipcode", "11111")
                .formParam("state", "Texas")
                .formParam("city", "Austin")
                .formParam("mobile_number", "5550001111")
                .when()
                .post("/api/createAccount");

        // Now delete it
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .delete("/api/deleteAccount")
                .then()
                .extract().response();

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("200") || responseBody.contains("Account deleted"),
                "Response should contain 'Account deleted!'. Actual: " + responseBody);
    }
}

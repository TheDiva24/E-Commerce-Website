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
public class API13_PutUpdateUserAccountTest extends BaseApiTest {

    @Test(description = "API 13: PUT METHOD To Update User Account")
    @Story("Update user account via API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies PUT /api/updateAccount updates user info and returns 200 'User updated!'.")
    public void testUpdateUserAccount() {
        String email = DataGenerator.generateEmail("upduser");
        String password = "Update@123";

        // Create account first
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "Upd Test User")
                .formParam("email", email)
                .formParam("password", password)
                .formParam("title", "Mr")
                .formParam("birth_date", "20")
                .formParam("birth_month", "6")
                .formParam("birth_year", "1985")
                .formParam("firstname", "Original")
                .formParam("lastname", "Name")
                .formParam("company", "Old Corp")
                .formParam("address1", "Old Address")
                .formParam("address2", "")
                .formParam("country", "United States")
                .formParam("zipcode", "22222")
                .formParam("state", "Florida")
                .formParam("city", "Miami")
                .formParam("mobile_number", "5552222333")
                .when()
                .post("/api/createAccount");

        // Now update it
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "Updated Name")
                .formParam("email", email)
                .formParam("password", password)
                .formParam("title", "Mr")
                .formParam("birth_date", "20")
                .formParam("birth_month", "6")
                .formParam("birth_year", "1985")
                .formParam("firstname", "Updated")
                .formParam("lastname", "User")
                .formParam("company", "New Corp")
                .formParam("address1", "New Address")
                .formParam("address2", "Suite 100")
                .formParam("country", "United States")
                .formParam("zipcode", "33333")
                .formParam("state", "Nevada")
                .formParam("city", "Las Vegas")
                .formParam("mobile_number", "5554443333")
                .when()
                .put("/api/updateAccount")
                .then()
                .extract().response();

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("200") || responseBody.contains("User updated"),
                "Response should contain 'User updated!'. Actual: " + responseBody);

        // Clean up
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .delete("/api/deleteAccount");
    }
}

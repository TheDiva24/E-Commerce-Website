package com.automationexercise.api;

import com.automationexercise.base.BaseApiTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("API Tests")
@Feature("Login API")
public class API10_PostVerifyLoginInvalidTest extends BaseApiTest {

    @Test(description = "API 10: POST To Verify Login with invalid details")
    @Story("Invalid login credentials return 404 User not found")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies POST /api/verifyLogin with invalid credentials returns 'User not found!'.")
    public void testVerifyLoginWithInvalidDetails() {
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", "invalid_nonexistent_user_xyz@notreal.com")
                .formParam("password", "wrongpassword")
                .when()
                .post("/api/verifyLogin")
                .then()
                .extract().response();

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("404") || responseBody.contains("User not found"),
                "Response should contain 'User not found!'. Actual: " + responseBody);
    }
}

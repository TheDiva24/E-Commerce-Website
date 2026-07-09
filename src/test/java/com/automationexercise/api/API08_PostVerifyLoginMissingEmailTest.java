package com.automationexercise.api;

import com.automationexercise.base.BaseApiTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("API Tests")
@Feature("Login API")
public class API08_PostVerifyLoginMissingEmailTest extends BaseApiTest {

    @Test(description = "API 8: POST To Verify Login without email parameter")
    @Story("Login without email returns 400")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies POST /api/verifyLogin without email parameter returns 400 bad request.")
    public void testVerifyLoginMissingEmail() {
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("password", "SomePassword")
                .when()
                .post("/api/verifyLogin")
                .then()
                .extract().response();

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("400") || responseBody.contains("Bad request") ||
                        responseBody.contains("missing"),
                "Response should indicate missing email parameter. Actual: " + responseBody);
    }
}

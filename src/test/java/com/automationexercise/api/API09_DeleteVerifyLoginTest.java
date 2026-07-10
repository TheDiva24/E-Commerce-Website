package com.automationexercise.api;

import com.automationexercise.base.BaseApiTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("API Tests")
@Feature("Login API")
public class API09_DeleteVerifyLoginTest extends BaseApiTest {

    @Test(description = "API 9: DELETE To Verify Login")
    @Story("DELETE to verifyLogin returns 405")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies DELETE /api/verifyLogin returns 405 - method not supported.")
    public void testDeleteVerifyLogin() {
        Response response = given()
                .when()
                .delete("/api/verifyLogin")
                .then()
                .extract().response();

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("405") || responseBody.contains("not supported"),
                "Response should indicate DELETE method is not supported. Actual: " + responseBody);
    }
}

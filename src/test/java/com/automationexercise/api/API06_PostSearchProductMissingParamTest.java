package com.automationexercise.api;

import com.automationexercise.base.BaseApiTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("API Tests")
@Feature("Search API")
public class API06_PostSearchProductMissingParamTest extends BaseApiTest {

    @Test(description = "API 6: POST To Search Product without search_product parameter")
    @Story("POST search without param returns 400")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies POST /api/searchProduct without search_product param returns bad request.")
    public void testPostSearchProductMissingParam() {
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post("/api/searchProduct")
                .then()
                .extract().response();

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("400") || responseBody.contains("Bad request") ||
                        responseBody.contains("missing"),
                "Response should indicate bad request for missing parameter. Actual: " + responseBody);
    }
}

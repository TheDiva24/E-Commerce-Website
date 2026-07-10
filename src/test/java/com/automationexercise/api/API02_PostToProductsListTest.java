package com.automationexercise.api;

import com.automationexercise.base.BaseApiTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("API Tests")
@Feature("Products API")
public class API02_PostToProductsListTest extends BaseApiTest {

    @Test(description = "API 2: POST To All Products List")
    @Story("POST to products list returns 405")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies POST /api/productsList returns status 405 - method not supported.")
    public void testPostToProductsList() {
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post("/api/productsList")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 (API returns 200 with error message)");
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("405") || responseBody.contains("not supported"),
                "Response should indicate method not supported (405)");
    }
}

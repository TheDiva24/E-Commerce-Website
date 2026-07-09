package com.automationexercise.api;

import com.automationexercise.base.BaseApiTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("API Tests")
@Feature("Products API")
public class API01_GetAllProductsListTest extends BaseApiTest {

    @Test(description = "API 1: Get All Products List")
    @Story("GET all products returns 200")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies GET /api/productsList returns status 200 and non-empty product list.")
    public void testGetAllProductsList() {
        Response response = given()
                .when()
                .get("/api/productsList")
                .then()
                .statusCode(200)
                .extract().response();

        String responseBody = response.getBody().asString();
        Assert.assertNotNull(responseBody, "Response body should not be null");
        Assert.assertTrue(responseBody.contains("products"), "Response should contain products data");
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
    }
}

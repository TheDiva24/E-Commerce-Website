package com.automationexercise.api;

import com.automationexercise.base.BaseApiTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("API Tests")
@Feature("Brands API")
public class API03_GetAllBrandsListTest extends BaseApiTest {

    @Test(description = "API 3: Get All Brands List")
    @Story("GET all brands returns 200")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies GET /api/brandsList returns status 200 and brands data.")
    public void testGetAllBrandsList() {
        Response response = given()
                .when()
                .get("/api/brandsList")
                .then()
                .statusCode(200)
                .extract().response();

        String responseBody = response.getBody().asString();
        Assert.assertNotNull(responseBody, "Response body should not be null");
        Assert.assertTrue(responseBody.contains("brands"), "Response should contain brands data");
    }
}

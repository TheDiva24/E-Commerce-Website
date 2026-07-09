package com.automationexercise.api;

import com.automationexercise.base.BaseApiTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("API Tests")
@Feature("Brands API")
public class API04_PutToBrandsListTest extends BaseApiTest {

    @Test(description = "API 4: PUT To All Brands List")
    @Story("PUT to brands list returns 405")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies PUT /api/brandsList returns 405 - method not supported.")
    public void testPutToBrandsList() {
        Response response = given()
                .when()
                .put("/api/brandsList")
                .then()
                .extract().response();

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("405") || responseBody.contains("not supported"),
                "Response should indicate PUT method is not supported");
    }
}

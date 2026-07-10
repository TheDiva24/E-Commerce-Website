package com.automationexercise.api;

import com.automationexercise.base.BaseApiTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Epic("API Tests")
@Feature("Search API")
public class API05_PostSearchProductTest extends BaseApiTest {

    @Test(description = "API 5: POST To Search Product")
    @Story("POST search returns matching products")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies POST /api/searchProduct with search_product param returns matching products.")
    public void testPostSearchProduct() {
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("search_product", "top")
                .when()
                .post("/api/searchProduct")
                .then()
                .statusCode(200)
                .extract().response();

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("products"), "Response should contain matching products");
    }
}

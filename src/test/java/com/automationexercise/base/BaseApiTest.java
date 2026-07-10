package com.automationexercise.base;

import com.automationexercise.utils.ConfigReader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

/**
 * Base class for all API tests.
 * Configures RestAssured with base URI and Allure logging filter.
 */
public abstract class BaseApiTest {

    protected static final Logger log = LogManager.getLogger(BaseApiTest.class);

    @BeforeClass
    public void setUpApi() {
        RestAssured.baseURI = ConfigReader.getApiBaseUrl();
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter(),
                new AllureRestAssured()
        );
        log.info("API Base URI configured: {}", RestAssured.baseURI);
    }

    /**
     * Returns a base RequestSpecification with common headers.
     */
    protected RequestSpecification getRequestSpec() {
        return given()
                .contentType("application/x-www-form-urlencoded")
                .accept("application/json");
    }
}

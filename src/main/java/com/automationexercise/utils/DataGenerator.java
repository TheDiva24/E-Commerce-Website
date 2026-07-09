package com.automationexercise.utils;

import com.automationexercise.models.User;

import java.util.UUID;

/**
 * Generates unique, randomized test data for use across test cases.
 * Ensures test isolation by generating unique emails per run.
 */
public class DataGenerator {

    private DataGenerator() {}

    /**
     * Generates a unique email address using UUID prefix.
     *
     * @param prefix Short prefix like "user", "test", etc.
     * @return Unique email address
     */
    public static String generateEmail(String prefix) {
        String unique = UUID.randomUUID().toString().substring(0, 8);
        return prefix + "_" + unique + "@qatest.com";
    }

    /**
     * Generates a complete User object with all required fields for registration.
     */
    public static User generateUser() {
        String unique = UUID.randomUUID().toString().substring(0, 8);
        return User.builder()
                .name("TestUser_" + unique)
                .email("user_" + unique + "@qatest.com")
                .password("TestPass@123")
                .title("Mr")
                .day("15")
                .month("June")
                .year("1995")
                .firstName("John")
                .lastName("Doe_" + unique)
                .company("QA Corp")
                .address1("123 Test Street")
                .address2("Suite 456")
                .country("United States")
                .state("California")
                .city("Los Angeles")
                .zipcode("90001")
                .mobileNumber("5551234567")
                .build();
    }

    /**
     * Generates a User with a specific email (for login tests).
     */
    public static User generateUserWithEmail(String email, String password) {
        String unique = UUID.randomUUID().toString().substring(0, 8);
        return User.builder()
                .name("TestUser_" + unique)
                .email(email)
                .password(password)
                .title("Mrs")
                .day("10")
                .month("March")
                .year("1990")
                .firstName("Jane")
                .lastName("Smith")
                .company("Test Inc")
                .address1("456 Main Ave")
                .address2("Apt 7B")
                .country("United States")
                .state("New York")
                .city("New York City")
                .zipcode("10001")
                .mobileNumber("5559876543")
                .build();
    }
}

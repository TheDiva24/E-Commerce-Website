package com.automationexercise.models;

/**
 * POJO representing a user account for the AutomationExercise website.
 * Uses builder pattern for flexible object creation.
 */
public class User {

    private final String name;
    private final String email;
    private final String password;
    private final String title;
    private final String day;
    private final String month;
    private final String year;
    private final String firstName;
    private final String lastName;
    private final String company;
    private final String address1;
    private final String address2;
    private final String country;
    private final String state;
    private final String city;
    private final String zipcode;
    private final String mobileNumber;

    private User(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.title = builder.title;
        this.day = builder.day;
        this.month = builder.month;
        this.year = builder.year;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.company = builder.company;
        this.address1 = builder.address1;
        this.address2 = builder.address2;
        this.country = builder.country;
        this.state = builder.state;
        this.city = builder.city;
        this.zipcode = builder.zipcode;
        this.mobileNumber = builder.mobileNumber;
    }

    // ─────────────── Getters ───────────────
    public String getName()         { return name; }
    public String getEmail()        { return email; }
    public String getPassword()     { return password; }
    public String getTitle()        { return title; }
    public String getDay()          { return day; }
    public String getMonth()        { return month; }
    public String getYear()         { return year; }
    public String getFirstName()    { return firstName; }
    public String getLastName()     { return lastName; }
    public String getCompany()      { return company; }
    public String getAddress1()     { return address1; }
    public String getAddress2()     { return address2; }
    public String getCountry()      { return country; }
    public String getState()        { return state; }
    public String getCity()         { return city; }
    public String getZipcode()      { return zipcode; }
    public String getMobileNumber() { return mobileNumber; }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', email='" + email + "'}";
    }

    // ─────────────── Builder ───────────────
    public static class Builder {
        private String name;
        private String email;
        private String password;
        private String title;
        private String day;
        private String month;
        private String year;
        private String firstName;
        private String lastName;
        private String company;
        private String address1;
        private String address2;
        private String country;
        private String state;
        private String city;
        private String zipcode;
        private String mobileNumber;

        public Builder name(String name)               { this.name = name; return this; }
        public Builder email(String email)             { this.email = email; return this; }
        public Builder password(String password)       { this.password = password; return this; }
        public Builder title(String title)             { this.title = title; return this; }
        public Builder day(String day)                 { this.day = day; return this; }
        public Builder month(String month)             { this.month = month; return this; }
        public Builder year(String year)               { this.year = year; return this; }
        public Builder firstName(String firstName)     { this.firstName = firstName; return this; }
        public Builder lastName(String lastName)       { this.lastName = lastName; return this; }
        public Builder company(String company)         { this.company = company; return this; }
        public Builder address1(String address1)       { this.address1 = address1; return this; }
        public Builder address2(String address2)       { this.address2 = address2; return this; }
        public Builder country(String country)         { this.country = country; return this; }
        public Builder state(String state)             { this.state = state; return this; }
        public Builder city(String city)               { this.city = city; return this; }
        public Builder zipcode(String zipcode)         { this.zipcode = zipcode; return this; }
        public Builder mobileNumber(String mobile)     { this.mobileNumber = mobile; return this; }

        public User build() {
            return new User(this);
        }
    }
}

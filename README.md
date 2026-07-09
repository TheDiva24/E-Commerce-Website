# AutomationExercise Test Suite

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://openjdk.org/)
[![Selenium](https://img.shields.io/badge/Selenium-4.x-green.svg)](https://selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.x-orange.svg)](https://testng.org/)
[![RestAssured](https://img.shields.io/badge/RestAssured-5.x-yellow.svg)](https://rest-assured.io/)
[![Allure](https://img.shields.io/badge/Allure-Reports-purple.svg)](https://allurereport.org/)

A comprehensive, production-grade Java Selenium + RestAssured automation framework targeting **[automationexercise.com](https://automationexercise.com)**.

---

## 📦 Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 17 | Language |
| Selenium WebDriver | 4.21.0 | UI Automation |
| WebDriverManager | 5.8.0 | Auto ChromeDriver setup |
| TestNG | 7.10.2 | Test execution framework |
| RestAssured | 5.4.0 | API testing |
| Allure | 2.27.0 | HTML Test reporting |
| Log4j2 | 2.23.1 | Logging |
| Jackson | 2.17.1 | JSON processing |
| Maven | 3.8+ | Build tool |

---

## ✅ Test Coverage

### 26 UI Test Cases
| # | Test Case | Category |
|---|---|---|
| TC01 | Register User | Authentication |
| TC02 | Login with Correct Credentials | Authentication |
| TC03 | Login with Incorrect Credentials | Authentication |
| TC04 | Logout User | Authentication |
| TC05 | Register with Existing Email | Authentication |
| TC06 | Contact Us Form | Navigation |
| TC07 | Verify Test Cases Page | Navigation |
| TC08 | Verify All Products & Product Detail | Products |
| TC09 | Search Product | Products |
| TC10 | Subscription on Home Page | Subscription |
| TC11 | Subscription on Cart Page | Subscription |
| TC12 | Add Products to Cart | Cart |
| TC13 | Verify Product Quantity in Cart | Cart |
| TC14 | Place Order: Register During Checkout | Orders |
| TC15 | Place Order: Register Before Checkout | Orders |
| TC16 | Place Order: Login Before Checkout | Orders |
| TC17 | Remove Products from Cart | Cart |
| TC18 | View Category Products | Products |
| TC19 | View & Cart Brand Products | Products |
| TC20 | Search Products & Verify Cart After Login | Products |
| TC21 | Add Review on Product | Products |
| TC22 | Add to Cart from Recommended Items | Cart |
| TC23 | Verify Address Details in Checkout | Orders |
| TC24 | Download Invoice | Orders |
| TC25 | Scroll Up with Arrow Button | UI Behavior |
| TC26 | Scroll Up without Arrow Button | UI Behavior |

### 14 API Test Cases
| # | API | Method | Expected |
|---|---|---|---|
| API01 | /api/productsList | GET | 200 + products |
| API02 | /api/productsList | POST | 405 not supported |
| API03 | /api/brandsList | GET | 200 + brands |
| API04 | /api/brandsList | PUT | 405 not supported |
| API05 | /api/searchProduct | POST | 200 + searched products |
| API06 | /api/searchProduct (missing param) | POST | 400 bad request |
| API07 | /api/verifyLogin (valid) | POST | 200 User exists! |
| API08 | /api/verifyLogin (missing email) | POST | 400 bad request |
| API09 | /api/verifyLogin | DELETE | 405 not supported |
| API10 | /api/verifyLogin (invalid) | POST | 404 User not found! |
| API11 | /api/createAccount | POST | 201 User created! |
| API12 | /api/deleteAccount | DELETE | 200 Account deleted! |
| API13 | /api/updateAccount | PUT | 200 User updated! |
| API14 | /api/getUserDetailByEmail | GET | 200 + user JSON |

---

## 🚀 Getting Started

### Prerequisites
- **Java 17+**: `java -version`
- **Maven 3.8+**: `mvn -version`
- **Google Chrome**: Latest version installed

> ✅ **ChromeDriver is managed automatically** by WebDriverManager — no manual installation needed!

### Installation

```bash
# Clone or navigate to the project
cd "E:\Computer Engineering\AMIT\Automation\AutomationExercise"

# Install dependencies
mvn clean install -DskipTests
```

---

## 🧪 Running Tests

### Run All UI Tests
```bash
mvn test -Dsurefire.suiteXmlFiles=testng.xml
```

### Run All API Tests
```bash
mvn test -Dsurefire.suiteXmlFiles=testng-api.xml
```

### Run Specific Test Class
```bash
mvn test -Dtest=TC01_RegisterUserTest
```

### Run Both UI + API Tests
```bash
mvn test
```

### Run in Headless Mode
```bash
# Edit config.properties: headless=true
mvn test
```

---

## 📊 Reports

### Generate Allure Report
```bash
# Run tests first
mvn clean test

# Serve interactive HTML report
mvn allure:serve
```

### View Static Report
```bash
mvn allure:report
# Opens: target/site/allure-maven-plugin/index.html
```

---

## 🏗️ Architecture

```
src/
├── main/java/com/automationexercise/
│   ├── base/
│   │   └── BasePage.java              # Common WebDriver interactions
│   ├── pages/                         # Page Object Model classes
│   │   ├── HomePage.java
│   │   ├── LoginPage.java
│   │   ├── SignupPage.java
│   │   ├── ProductsPage.java
│   │   ├── ProductDetailPage.java
│   │   ├── CartPage.java
│   │   ├── CheckoutPage.java
│   │   ├── PaymentPage.java
│   │   ├── PaymentDonePage.java
│   │   ├── ContactUsPage.java
│   │   └── TestCasesPage.java
│   ├── models/
│   │   └── User.java                  # User POJO with Builder pattern
│   └── utils/
│       ├── DriverManager.java         # Thread-safe WebDriver (ThreadLocal)
│       ├── ConfigReader.java          # config.properties reader
│       ├── DataGenerator.java         # UUID-based test data
│       └── WaitUtils.java             # Explicit wait utilities
└── test/java/com/automationexercise/
    ├── base/
    │   ├── BaseTest.java              # @BeforeMethod/@AfterMethod + screenshots
    │   └── BaseApiTest.java           # RestAssured base URI setup
    ├── ui/                            # 26 UI Test Classes (TC01-TC26)
    └── api/                           # 14 API Test Classes (API01-API14)
```

---

## ⚙️ Configuration

Edit `src/test/resources/config.properties`:

```properties
# Browser: chrome | firefox | edge
browser=chrome
headless=false

# Timeouts
explicit.wait=15
page.load.timeout=30

# Base URLs
base.url=https://automationexercise.com
api.base.url=https://automationexercise.com
```

---

## 🎨 Design Patterns

| Pattern | Implementation |
|---|---|
| **Page Object Model** | Each page = 1 Java class with locators & actions |
| **Builder Pattern** | `User.builder()...build()` for test data |
| **ThreadLocal** | Thread-safe driver for future parallel execution |
| **Explicit Waits** | `WaitUtils` — zero `Thread.sleep()` in pages |
| **Singleton** | `ConfigReader` lazy-loads config once |

---

## 📸 Screenshots

Screenshots are automatically captured on test failure and attached to the **Allure report**.

---

## 🗂️ Project Structure

```
AutomationExercise/
├── pom.xml                    # Maven dependencies
├── testng.xml                 # UI test suite (26 tests)
├── testng-api.xml             # API test suite (14 tests)
├── README.md
└── src/
    ├── main/java/             # Pages, models, utils
    └── test/
        ├── java/              # Test classes
        └── resources/
            ├── config.properties
            ├── allure.properties
            └── log4j2.xml
```

---

## 👤 Author

Automation Framework built with ❤️ for **automationexercise.com** — covering all 26 UI and 14 API test cases documented on the website.

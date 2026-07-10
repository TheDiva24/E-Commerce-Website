# 🧪 AutomationExercise — Full-Stack Test Automation Framework

[![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=java)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.21.0-green?logo=selenium)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.x-blue)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-red?logo=apache-maven)](https://maven.apache.org/)
[![Allure](https://img.shields.io/badge/Allure-2.x-yellow)](https://allurereport.org/)
[![RestAssured](https://img.shields.io/badge/RestAssured-5.x-purple)](https://rest-assured.io/)
[![Tests](https://img.shields.io/badge/Tests-40%20%7C%2026%20UI%20%2B%2014%20API-brightgreen)]()
[![Status](https://img.shields.io/badge/Status-All%20Passing-success)]()

> A production-grade, Page Object Model automation framework that fully tests the [automationexercise.com](https://automationexercise.com) web application — covering all 26 official UI test cases and 14 REST API test cases, with beautiful Allure HTML reports.

---

## 📋 Table of Contents

- [About the Project](#about-the-project)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [1. Clone the Repository](#1-clone-the-repository)
  - [2. Configure Maven Path](#2-configure-maven-path)
  - [3. Extract the target.rar](#3-extract-the-targetrar)
  - [4. Verify Your Setup](#4-verify-your-setup)
- [Running the Tests](#running-the-tests)
- [Viewing the Allure Report](#viewing-the-allure-report)
- [Project Structure](#project-structure)
- [Test Cases Covered](#test-cases-covered)
- [Framework Architecture](#framework-architecture)
- [Configuration](#configuration)
- [Troubleshooting](#troubleshooting)

---

## About the Project

This framework was built as a **complete QA automation solution** for the `automationexercise.com` practice website, following best practices used by senior QA engineers:

- **Page Object Model (POM)** separates test logic from UI interaction details
- **Data-driven** with a `DataGenerator` utility that creates unique test users on the fly — no hardcoded credentials
- **Ad-resilient clicks** — the site serves full-page Google Ads that intercept Selenium clicks; the framework automatically falls back to JavaScript injection when this occurs
- **Eager page load strategy** — prevents 30-second renderer timeouts caused by heavy ad scripts
- **Allure reporting** — every test run generates a beautiful, interactive HTML report with steps, screenshots, and severity levels

---

## Tech Stack

| Layer | Technology | Purpose |
|---|---|---|
| Language | Java 17+ | Core language |
| UI Automation | Selenium WebDriver 4.21.0 | Browser control |
| API Testing | RestAssured 5.x | HTTP API assertions |
| Test Runner | TestNG 7.x | Test orchestration & suites |
| Build Tool | Apache Maven 3.6+ | Dependency management & lifecycle |
| Reporting | Allure Report 2.x | HTML test reports |
| Driver Management | WebDriverManager | Auto-downloads ChromeDriver |
| Logging | Log4j2 | Structured test logging |

---

## Prerequisites

Before running this framework, make sure the following are installed on your machine:

### 1. Java JDK 17 or higher
Download from [Adoptium (recommended)](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/).

Verify installation:
```bash
java -version
```
Expected output: `java version "17.x.x"` or higher.

> ⚠️ **Important:** Set the `JAVA_HOME` environment variable to your JDK installation folder, and add `%JAVA_HOME%\bin` to your system `PATH`.

### 2. Apache Maven 3.6+
Download from [maven.apache.org](https://maven.apache.org/download.cgi) and extract to any folder on your machine (e.g., `C:\tools\apache-maven-3.9.x`).

Verify:
```bash
mvn -version
```

> 💡 You do **not** need Maven in your `PATH` if you update the `run.bat` file (see [Configure Maven Path](#2-configure-maven-path) below).

### 3. Google Chrome Browser
Download from [google.com/chrome](https://www.google.com/chrome/).

> The framework uses **WebDriverManager** to automatically download the correct ChromeDriver version — **you do NOT need to manually install ChromeDriver**.

### 4. WinRAR or 7-Zip (for the target folder)
The `target/` build folder is included as `target.rar` in this repository.
Download [WinRAR](https://www.rarlab.com/) or [7-Zip](https://www.7-zip.org/) to extract it.

---

## Getting Started

### 1. Clone the Repository

Open a terminal and run:

```bash
git clone https://github.com/YOUR_USERNAME/AutomationExercise.git
cd AutomationExercise
```

Or download the ZIP from GitHub and extract it.

> 📝 The project can live **anywhere** on your machine — the path does not need to match the original developer's machine.

---

### 2. Configure Maven Path

> **Skip this step** if `mvn` is already in your system `PATH` (i.e., `mvn -version` works in a new terminal window). In that case, change line 5 in `run.bat` to:
> ```batch
> SET "MVN=mvn"
> ```

Open `run.bat` in any text editor (Notepad, VS Code, etc.) and update **line 5** with the full path to your Maven installation:

```batch
SET "MVN=C:\YOUR\PATH\TO\apache-maven-3.x.x\bin\mvn.cmd"
```

**Examples:**

| Scenario | Value for `MVN` |
|---|---|
| Maven installed to `C:\tools\` | `C:\tools\apache-maven-3.9.15\bin\mvn.cmd` |
| Maven installed to `D:\dev\` | `D:\dev\apache-maven-3.9.15\bin\mvn.cmd` |
| Maven is in system PATH | `mvn` |

---

### 3. Extract the target.rar

The `target/` folder contains the Allure report history and pre-compiled test artifacts. It is stored as `target.rar` in the repository to stay within GitHub's file limits.

**Steps to extract:**

1. Locate `target.rar` in the project root folder.
2. Right-click it → **Extract Here** (using WinRAR or 7-Zip).
3. A `target/` folder will be created in the same location.

Your project root should now look like this:
```
AutomationExercise/
├── src/
├── target/          ← extracted from target.rar
├── target.rar
├── pom.xml
├── run.bat
├── run.ps1
├── testng.xml
└── testng-api.xml
```

> ⚠️ If you skip this step, the **Allure report viewer will still work** — it will just start with an empty report history. The tests themselves will still run and generate fresh results.

---

### 4. Verify Your Setup

Open a **Command Prompt** window, navigate to the project root, and run:

```bash
run.bat compile
```

Expected output:
```
[SUCCESS] All sources compiled successfully - no errors!
```

If you see compilation errors, double-check your Java version and Maven path.

---

## Running the Tests

Open a **Command Prompt** window in the project root folder and use the `run.bat` script:

> 💡 **Tip:** You can open a CMD window directly in the project folder by clicking the address bar in Windows Explorer and typing `cmd`.

```bash
# Compile only (no browser opened - just verify the build)
run.bat compile

# Run all 26 UI tests (Chrome browser will open automatically)
run.bat ui

# Run all 14 API tests (no browser required)
run.bat api

# Run the full test suite (26 UI + 14 API = 40 tests)
run.bat all

# Open the Allure HTML report in your browser
run.bat report
```

### PowerShell Users

A `run.ps1` script is also provided. Right-click it and choose **Run with PowerShell**, or run from terminal:

```powershell
.\run.ps1 ui
.\run.ps1 api
.\run.ps1 report
```

### Test Execution Time

| Suite | Tests | Approx. Time |
|---|---|---|
| UI Suite | 26 tests | ~9–12 minutes |
| API Suite | 14 tests | ~1–2 minutes |
| Full Suite | 40 tests | ~11–14 minutes |

---

## Viewing the Allure Report

After tests run, open the interactive report:

```bash
run.bat report
```

This command launches a local web server and automatically opens the Allure report in your default browser.

The report includes:
- ✅ Pass/Fail status per test case
- 📊 Epic → Feature → Story grouping
- 🔴 Severity levels (Blocker / Critical / Normal / Trivial)
- 📝 Step-by-step execution logs
- ⏱️ Execution duration per test
- 📈 Historical trend charts (if previous results exist)

---

## Project Structure

```
AutomationExercise/
│
├── src/
│   ├── main/java/com/automationexercise/
│   │   ├── base/
│   │   │   └── BasePage.java          # Abstract base: all shared WebDriver actions
│   │   ├── models/
│   │   │   └── User.java              # POJO model for test user data
│   │   ├── pages/                     # Page Object Model classes (11 pages)
│   │   │   ├── HomePage.java
│   │   │   ├── LoginPage.java
│   │   │   ├── SignupPage.java
│   │   │   ├── ProductsPage.java
│   │   │   ├── ProductDetailPage.java
│   │   │   ├── CartPage.java
│   │   │   ├── CheckoutPage.java
│   │   │   ├── PaymentPage.java
│   │   │   ├── PaymentDonePage.java
│   │   │   ├── ContactUsPage.java
│   │   │   └── TestCasesPage.java
│   │   └── utils/
│   │       ├── ConfigReader.java      # Reads config.properties
│   │       ├── DataGenerator.java     # Generates unique test user data
│   │       ├── DriverManager.java     # Thread-safe WebDriver lifecycle
│   │       └── WaitUtils.java         # Centralized explicit wait library
│   │
│   └── test/java/com/automationexercise/
│       ├── base/
│       │   └── BaseTest.java          # Test lifecycle: setup, teardown, logging
│       ├── ui/                        # 26 UI test classes (TC01–TC26)
│       └── api/                       # 14 API test classes (API01–API14)
│
├── src/main/resources/
│   ├── config.properties              # Browser, URL, and timeout configuration
│   └── log4j2.xml                     # Logging configuration
│
├── target/                            # Build output & Allure results (extracted from target.rar)
├── target.rar                         # Compressed build artifacts for GitHub upload
├── pom.xml                            # Maven build file with all dependencies
├── testng.xml                         # UI test suite definition
├── testng-api.xml                     # API test suite definition
├── run.bat                            # Windows CMD runner (update Maven path inside)
└── run.ps1                            # Windows PowerShell runner
```

---

## Test Cases Covered

### 🖥️ UI Tests (26 Test Cases)

| # | Test Case | Feature | Severity |
|---|---|---|---|
| TC01 | Register User | Registration | 🔴 Blocker |
| TC02 | Login with Correct Credentials | Login | 🔴 Blocker |
| TC03 | Login with Incorrect Credentials | Login | 🟡 Normal |
| TC04 | Logout User | Logout | 🟠 Critical |
| TC05 | Register with Existing Email | Registration | 🟡 Normal |
| TC06 | Contact Us Form with File Upload | Contact Us | 🟡 Normal |
| TC07 | Verify Test Cases Page | Navigation | ⚪ Trivial |
| TC08 | Verify All Products & Product Detail | Products | 🟠 Critical |
| TC09 | Search Product | Product Search | 🟠 Critical |
| TC10 | Subscription on Home Page | Subscription | 🟡 Normal |
| TC11 | Subscription on Cart Page | Subscription | 🟡 Normal |
| TC12 | Add Two Products to Cart | Cart | 🟠 Critical |
| TC13 | Verify Product Quantity in Cart | Cart | 🟠 Critical |
| TC14 | Place Order: Register While Checkout | Orders | 🟠 Critical |
| TC15 | Place Order: Register Before Checkout | Orders | 🟠 Critical |
| TC16 | Place Order: Login Before Checkout | Orders | 🟠 Critical |
| TC17 | Remove Products from Cart | Cart | 🟡 Normal |
| TC18 | View Category Products | Categories | 🟡 Normal |
| TC19 | View Brand Products | Brands | 🟡 Normal |
| TC20 | Search & Verify Cart After Login | Cart | 🟠 Critical |
| TC21 | Add Review on Product | Reviews | 🟡 Normal |
| TC22 | Add to Cart from Recommended Items | Cart | 🟡 Normal |
| TC23 | Verify Address Details in Checkout | Orders | 🟠 Critical |
| TC24 | Download Invoice After Order | Orders | 🟡 Normal |
| TC25 | Scroll Up with Arrow Button | Scroll | ⚪ Trivial |
| TC26 | Scroll Up without Arrow Button | Scroll | ⚪ Trivial |

### 🔌 API Tests (14 Test Cases)

| # | Endpoint | Method | Validation |
|---|---|---|---|
| API01 | `/api/productsList` | GET | 200 OK, products list returned |
| API02 | `/api/productsList` | POST | 405 Method Not Allowed |
| API03 | `/api/brandsList` | GET | 200 OK, brands list returned |
| API04 | `/api/brandsList` | PUT | 405 Method Not Allowed |
| API05 | `/api/searchProduct` | POST | 200 OK, matching products returned |
| API06 | `/api/searchProduct` | POST (no param) | 400 Bad Request |
| API07 | `/api/verifyLogin` | POST | 200 OK, valid credentials |
| API08 | `/api/verifyLogin` | POST (no email) | 400 Bad Request |
| API09 | `/api/verifyLogin` | DELETE | 405 Method Not Allowed |
| API10 | `/api/verifyLogin` | POST (invalid) | 404 User not found |
| API11 | `/api/createAccount` | POST | 201 Account created |
| API12 | `/api/deleteAccount` | DELETE | 200 Account deleted |
| API13 | `/api/updateAccount` | PUT | 200 Account updated |
| API14 | `/api/getUserDetailByEmail` | GET | 200 OK, user details returned |

---

## Framework Architecture

```
┌─────────────────────────────────────────────────────┐
│                   Test Layer (TestNG)               │
│          TC01 ... TC26   |   API01 ... API14        │
└────────────────┬───────────────────────────────────-┘
                 │ extends
┌────────────────▼────────────────────────────────────┐
│             BaseTest.java                           │
│  @BeforeMethod → driver init, navigate to base URL  │
│  @AfterMethod  → quit driver, log pass/fail         │
└────────────────┬────────────────────────────────────┘
                 │ uses
┌────────────────▼────────────────────────────────────┐
│           Page Object Model (POM)                   │
│  HomePage / LoginPage / ProductsPage / ...          │
│  Each class: locators + actions (no assertions)     │
└────────────────┬────────────────────────────────────┘
                 │ extends
┌────────────────▼────────────────────────────────────┐
│             BasePage.java                           │
│  click()  → auto JS fallback on ad interception     │
│  type()   → clear + sendKeys                        │
│  dismissAds() → removes Google Ad iframes via JS    │
│  checkCheckbox() → JS click (ad-proof)              │
└────────────────┬────────────────────────────────────┘
                 │ uses
┌────────────────▼────────────────────────────────────┐
│      DriverManager  +  WaitUtils  +  ConfigReader   │
│  Thread-safe ThreadLocal | Explicit waits | Config  │
└─────────────────────────────────────────────────────┘
```

---

## Configuration

All framework settings are in [`src/main/resources/config.properties`](src/main/resources/config.properties):

```properties
# Browser to use: chrome | firefox | edge
browser=chrome

# Run in headless mode (no visible browser window): true | false
headless=false

# Base URL of the application under test
base.url=https://automationexercise.com

# Explicit wait timeout in seconds
explicit.wait=15

# Page load timeout in seconds
page.load.timeout=30
```

You can change the `browser` property to `firefox` or `edge` (they must be installed on your machine).

---

## Troubleshooting

### ❌ `mvn` command not found
Make sure the `MVN` variable in `run.bat` points to the correct `mvn.cmd` on your machine. See [Step 2](#2-configure-maven-path).

### ❌ `java` is not recognized
Ensure Java 17+ is installed and `JAVA_HOME` is set. Add `%JAVA_HOME%\bin` to your system `PATH`.

### ❌ ChromeDriver version mismatch
This should be handled automatically by `WebDriverManager`. If it fails, update your Chrome browser to the latest version and try again.

### ❌ Tests fail due to pop-ups or ads
The framework is built with ad-resilient logic (`dismissAds()` + JS click fallback). If ads still cause issues:
- Make sure you are **not** running in a Chrome profile that blocks JavaScript
- Try switching to headless mode in `config.properties`: `headless=true`

### ❌ Allure report says "No data"
Make sure you extracted `target.rar` first. If you ran the tests without it, you still need to run `run.bat ui` first to generate fresh Allure results, then run `run.bat report`.

### ❌ `run.bat` closes immediately
Open a **Command Prompt** (not double-click) and run `run.bat ui` from the prompt. This shows error messages before the window closes.

---

## 📄 License

This project is built for educational and training purposes. The application under test is [automationexercise.com](https://automationexercise.com).

---

## 👤 Author

Built by a QA Automation Engineer as a full-stack test automation showcase covering UI (Selenium) and API (RestAssured) testing with a professional POM architecture and Allure reporting.

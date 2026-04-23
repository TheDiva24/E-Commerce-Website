# E-Commerce Platform Testing Project

## 📌 Project Overview
This project focuses on the comprehensive Quality Assurance (QA) testing of a full-scale E-commerce platform. The goal was to ensure system reliability, security, and a seamless user experience through a combination of manual, API, database, and automated testing methodologies.

## 🔗 Project Management
All tasks, bug reports, and sprint progress are tracked via Jira.
* **Jira Board:** [View Project Tasks & Bug Reports](https://mhassan4406-1776543631131.atlassian.net/jira/software/projects/MT/summary)

---

## 🛠 Testing Methodologies

### 1. Manual Testing Plan
The manual testing phase ensured that the application meets the functional requirements from an end-user perspective.
* **Test Plan:** Includes Scope, Test Strategy, Entry/Exit Criteria, and Risk Analysis.
* **Test Cases:** Documented detailed steps for User Authentication, Product Search, Shopping Cart functionality, and Checkout processes.
* **Defect Reporting:** Bugs were logged in Jira with detailed reproduction steps, severity levels, and screenshots.

### 2. API Testing
Validated the backend communication and data integrity using tools like **Postman**.
* **Tested Endpoints:** User registration, login (JWT verification), product retrieval, and order placement.
* **Validations:** Verified Status Codes (200, 201, 400, 401, 404), Response Body structure, and Response Time.
* **Environment:** Collection variables and environment scripts were used to automate token handling.

### 3. Database Testing (Sakila Database)
Ensured data consistency and backend integrity.
* **Validation Points:** Verified that UI actions (e.g., updating a profile) correctly reflect in the database tables.
* **Integrity Checks:** Verified Primary/Foreign key constraints and data types for the `Users`, `Products`, and `Orders` tables.
* **Tools:** SQL queries were executed to validate data persistence and cleanup after testing cycles.

### 4. Automation Testing
Developed a robust automation framework to handle regression testing and repetitive UI tasks.
* **Framework:** [Selenium WebDriver with Java or Python]
* **Design Pattern:** Page Object Model (POM) for enhanced maintainability and reduced code duplication.
* **Features:**
    - Cross-browser testing (Chrome, Firefox, Edge).
    - Data-driven testing for login scenarios.
    - Automated HTML reporting.

---

## 🚀 Technical Stack
* **Project Management:** Jira
* **Automation:** Selenium WebDriver, Language: Java/Python
* **API Testing:** Postman
* **Database:** MySQL 
* **Version Control:** Git & GitHub

---

## 📂 Project Structure
├── Manual_Testing
│   ├── Test_Plan.pdf
│   └── Test_Cases.xlsx
├── API_Testing
│   └── Postman_Collections/
├── Automation_Framework
│   ├── src/
│   ├── tests/
│   └── reports/
└── README.md

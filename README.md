# osmos-assignment
This repository has been created for Osmos QA Lead Assignment purpose.

# QA Automation Framework - Playwright, Java & TestNG

## Overview
This repository contains the automated UI tests for the Lead Manager SaaS application. It fulfills the assignment requirement to automate the following primary business flow: `Login -> Create Lead -> List Lead`.

The framework is built to be robust and handles complex modern web application elements, including custom React/Radix UI dropdowns, state-based toggles, and strict HTML5 date inputs via JavaScript evaluation.

## Tools and Frameworks Used
* **Programming Language:** Java 11+
* **Browser Automation Tool:** Playwright for Java
* **Test Runner:** TestNG
* **Build Tool:** Maven
* **Design Pattern:** Page Object Model (POM)

## Prerequisites & Setup Instructions
* Ensure **Java JDK 11** (or higher) and **Apache Maven** are installed on your system.
* Clone this repository to your local machine.
* Open a terminal and navigate to the root directory of the project (where the `pom.xml` is located).
* Run the following command to download project dependencies and install the Playwright browser binaries required for execution:
   ```bash
   mvn compile exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"

## How to Execute Automated Tests
You can run the automated test suite using Maven from your terminal. The execution is driven by the testng.xml suite file.

* Run all tests (Default execution): 
   ```bash
   mvn test

* Toggle Headless / Headed Mode
  By default, the framework is configured to run in headed mode (visible browser) so you can watch the execution. To run tests invisibly (headless mode) for CI/CD pipelines, navigate to src/test/java/tests/BaseTest.java and change the boolean value in the launch options:
  
   ```Java
   BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(true);

## Viewing the Execution Report
Because this project utilizes TestNG integrated with the Maven Surefire plugin, detailed HTML execution reports are automatically generated after every test run.

* After running mvn test, navigate to the following directory in your project folder:
target/surefire-reports/

* Open index.html or emailable-report.html in your preferred web browser to view detailed results, execution times, and assertion statuses.




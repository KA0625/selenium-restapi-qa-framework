# 🎯 Enterprise QA Automation Framework

> A comprehensive, production-grade QA automation framework combining UI testing, API validation, and hybrid architecture patterns.

![Framework Architecture](https://img.shields.io/badge/Architecture-Hybrid%20Automation-blue)
![Test Framework](https://img.shields.io/badge/Framework-TestNG%2BCucumber-brightgreen)
![Selenium Grid](https://img.shields.io/badge/Parallel%20Execution-Selenium%20Grid-orange)
![Reporting](https://img.shields.io/badge/Reporting-Extent%20%2B%20Allure-red)

---

## 📋 Table of Contents

- [Overview](#overview)
- [Key Features](#-key-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [Project Structure](#-project-structure)
- [Configuration](#-configuration)
- [Test Execution](#-test-execution)
- [CI/CD Integration](#-cicd-integration)
- [Reporting](#-reporting)
- [Best Practices](#-best-practices)

---

## Overview

A sophisticated, enterprise-level QA automation framework designed to streamline testing workflows across multiple domains:

✅ **UI Testing** - Selenium-based web application testing  
✅ **API Testing** - REST API validation and link verification  
✅ **Data-Driven Testing** - Excel, JSON, and JDBC database support  
✅ **Parallel Execution** - Distributed testing via Selenium Grid  
✅ **CI/CD Ready** - Jenkins integration with automated pipeline triggers  
✅ **Advanced Reporting** - Extent and Allure reporting with rich visualizations  

---

## 🚀 Key Features

### 🎯 Dual Testing Capabilities
- **Selenium UI Automation** - WebDriver-based testing with advanced element interactions
- **REST API Testing** - Comprehensive API validation, response verification, and link health checks

### 🏗️ Design Patterns
- **Page Object Model (POM)** - Maintainable, scalable UI test structure
- **Page Factory** - Optimized element initialization and management
- **Data-Driven Framework** - Parameterized tests with external data sources

### 📊 Test Framework Integration
- **TestNG** - Powerful test configuration and execution control
- **Cucumber/Gherkin** - BDD-style scenario writing for business stakeholder alignment
- **Hybrid Architecture** - Seamless integration of procedural and BDD approaches

### ⚡ Parallel & Distributed Execution
- **Selenium Grid** - Scale tests across multiple machines and browsers
- **Multi-threaded Execution** - Reduce test suite runtime significantly
- **Cross-browser Testing** - Chrome, Firefox, Safari, Edge compatibility

### 💾 Data Management
- **JDBC Support** - Direct database connectivity for complex data validations
- **Excel Integration** - Read/write test data and validation matrices
- **JSON Handling** - Flexible data format for API and configuration management

### 📈 Comprehensive Reporting
- **Extent Reports** - Interactive HTML dashboards with screenshot attachments
- **Allure Reports** - Enhanced test analytics with historical trends
- **Detailed Logs** - Full execution traces for root cause analysis

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|------------|
| **Test Automation** | Selenium WebDriver, TestNG, Cucumber |
| **Programming Language** | Java (primary) |
| **API Testing** | REST Assured, HTTP Client, CDP |
| **Build Tool** | Maven|
| **Version Control** | Git/GitHub |
| **CI/CD Platform** | Jenkins |
| **Distributed Testing** | Selenium Grid |
| **Reporting** | Extent Reports, Allure Framework |
| **Database** | JDBC (MySQL, PostgreSQL, Oracle supported) |
| **Configuration** | YAML, Properties Files |

---

## 🏛️ Architecture

```
selenium-restapi-qa-framework/
│
├── 📁 src/main/java/
│   ├── pages/                    # Page Object Models
│   ├── api/                      # REST API utilities
│   ├── utils/                    # Helper functions
│   ├── config/                   # Configuration management
│   └── factory/                  # WebDriver factory
│
├── 📁 src/test/java/
│   ├── tests/                    # Test classes (UI & API)
│   └── hooks/                    # Cucumber hooks & setup/teardown
│
├── 📁 src/test/resources/
│   ├── features/                 # Gherkin feature files (BDD scenarios)
│   ├── testdata/                 # Excel, JSON test data
│   └── config/                   # Environment configurations
│
├── 📁 reports/                   # Extent & Allure reports
├── 📁 screenshots/               # Failure screenshots
├── pom.xml                       # Maven dependencies
└── README.md                     # This file
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 11+** - JDK installation
- **Maven 3.6+** - Dependency management
- **Git** - Version control
- **Chrome/Firefox** - Browser for local execution

### Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/KA0625/selenium-restapi-qa-framework.git
   cd selenium-restapi-qa-framework
   ```

2. **Install Dependencies**
   ```bash
   mvn clean install
   ```

3. **Configure Environment**
   - Update `src/test/resources/config/config.properties`
   - Set browser type, base URL, API endpoints
   - Configure database credentials (if needed)

4. **Verify Setup**
   ```bash
   mvn verify
   ```

---

## Selenium Grid Setup
```bash
# Start Selenium Grid Hub
java -jar selenium-server-standalone.jar -role hub

# Start Node (Chrome)
java -jar selenium-server-standalone.jar -role node -hub http://localhost:4444
```

---

## 🧪 Test Execution

### Run All Tests
```bash
mvn test
```

### Run Specific Feature File
```bash
mvn test -Dtest=LoginTests
mvn test -Dcucumber.options="src/test/resources/features/login.feature"
```

### Run Tests by Tag
```bash
mvn test -Dcucumber.options="--tags @smoke"
mvn test -Dcucumber.options="--tags @regression"
```

### Parallel Execution
```bash
mvn test -Dparallel=true -Dthreads=4
```

### Run on Selenium Grid
```bash
mvn test -DgridURL=http://localhost:4444 -Dbrowser=chrome
```



## 📊 Reporting

### Extent Reports
- **Location:** `reports/ExtentReport.html`
- **Features:**
  - Dashboard with pass/fail metrics
  - Screenshot attachments on failure
  - Timeline visualization
  - Test categorization

### Allure Reports
```bash
mvn allure:serve
```
- **Location:** `target/site/allure-report/`
- **Features:**
  - Historical trend analysis
  - Failed test analytics
  - Detailed timeline
  - Defect tracking integration

### Generate Reports
```bash
# Extent Report
mvn test -DreportType=extent

# Allure Report
mvn test
allure generate --clean -o allure-report
```

---

## 🎓 Best Practices

### ✅ Test Design
- Follow **Single Responsibility Principle** - One assertion per test scenario
- Use **Data-Driven Testing** for multiple input combinations
- Implement **Explicit Waits** instead of hard sleeps
- Maintain **DRY** (Don't Repeat Yourself) principle

### ✅ Code Quality
- Use **Page Object Model** for UI automation
- Implement **Proper Exception Handling**
- Write **Meaningful Assertions** with clear failure messages
- Keep **Test Data Externalized**

### ✅ Maintenance
- **Regular Dependency Updates** - Keep libraries current
- **Review Flaky Tests** - Identify and stabilize unreliable tests
- **Clean Up Reports** - Archive old execution results
- **Documentation** - Keep README and inline comments updated

### ✅ Performance
- **Parallel Test Execution** - Reduce overall runtime
- **Selective Test Runs** - Use tags for smoke/regression
- **Resource Optimization** - Close resources properly (WebDriver, connections)

---

## 📚 Additional Resources

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [TestNG Documentation](https://testng.org/doc/)
- [Cucumber Documentation](https://cucumber.io/docs/cucumber/)
- [Allure Reporting](https://docs.qameta.io/allure/)
- [Extent Reports](https://www.extentreports.com/)

---

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/YourFeature`)
3. Commit changes (`git commit -m 'Add YourFeature'`)
4. Push to branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👤 Author

**KA0625**

- 🔗 GitHub: [@KA0625](https://github.com/KA0625)
- LinkedIn: https://www.linkedin.com/in/aathirai-sethuraman/

---

## 📞 Support

For issues, questions, or suggestions:
- Open an [Issue](https://github.com/KA0625/selenium-restapi-qa-framework/issues)
- Check existing documentation
- Review code examples in `src/test/`

---

<div align="center">

**⭐ If you find this framework useful, please consider starring the repository!**

Built with ❤️ for Quality Assurance Professionals

</div>

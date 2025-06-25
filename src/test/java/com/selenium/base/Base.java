package com.selenium.base;

import com.aventstack.extentreports.Status;
import com.selenium.utils.ExtentManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.UUID;

public class Base {

    protected WebDriver driver;
    private static final Logger log = LogManager.getLogger(Base.class);

    @BeforeSuite
    public void beforeSuite() {
        // Initialize Extent Report once before test suite starts
        ExtentManager.initReport();
    }

    @AfterSuite
    public void afterSuite() {
        // Flush Extent Report once after all tests finish
        ExtentManager.flushReport();
    }

    @BeforeMethod
    public void setUp(Method method) {
        log.info("Launching browser");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Navigate to your app under test
        driver.get("https://demoqa.com/login");
        log.info("Navigated to: https://demoqa.com/login");

        // Create Extent Report test node dynamically for each test method
        ExtentManager.createTest(method.getName());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                log.error("Test FAILED: " + result.getName(), result.getThrowable());
                ExtentManager.getTest().fail(result.getThrowable());

                // Capture screenshot on failure
                String screenshotPath = captureScreenshot(result.getName());
                ExtentManager.getTest().addScreenCaptureFromPath(screenshotPath);
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                ExtentManager.getTest().log(Status.PASS, "Test PASSED");
            } else if (result.getStatus() == ITestResult.SKIP) {
                ExtentManager.getTest().log(Status.SKIP, "Test SKIPPED");
            }
        } catch (Exception e) {
            log.error("Error during tearDown reporting: ", e);
        } finally {
            if (driver != null) {
                driver.quit();
                log.info("Browser closed");
            }
        }
    }

    // Screenshot utility
    public String captureScreenshot(String testName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        String screenshotPath = "test-output/screenshots/" + testName + "_" + UUID.randomUUID() + ".png";

        try {
            FileUtils.copyFile(srcFile, new File(screenshotPath));
            log.info("Screenshot saved at: " + screenshotPath);
        } catch (IOException e) {
            log.error("Failed to save screenshot: ", e);
        }
        return screenshotPath;
    }
}




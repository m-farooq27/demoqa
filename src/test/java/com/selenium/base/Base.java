package com.selenium.base;

import java.time.Duration;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.*;
import org.testng.ITestResult;

import com.selenium.utils.PropertiesHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Base {
	
	public WebDriver driver;
	private static final Logger log = LogManager.getLogger(Base.class);

	protected static ExtentReports extent;
	protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@BeforeSuite
	public void setupExtentReports() {
		ExtentSparkReporter reporter = new ExtentSparkReporter ("test-output/ExtentReport.html");
		reporter.config().setDocumentTitle("Automation Report");
		reporter.config().setReportName("Selenium Test Results");
	
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}
	
	@BeforeMethod
	public void setUp(Method method) {

		ExtentTest extentTest = extent.createTest(method.getName());
		test.set(extentTest);

		String browserType = PropertiesHandler.get("browserType").toLowerCase();
		String applicationUnderTest = PropertiesHandler.get("applicationUnderTest");
		int timeout = PropertiesHandler.getInt("timeout", 10);
		
		log.info("Launching browser: {}", browserType);
		
		switch (browserType) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "ie":
			driver = new InternetExplorerDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default: //default browser
			System.out.println("Unsupported browser - defaulting to Chrome");
			System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
			driver = new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
		driver.get(applicationUnderTest);
		
		log.info("Navigated to: {}", applicationUnderTest);
		
	}
	
	
	@AfterMethod
	public void tearDown(ITestResult result){
		if(result.getStatus()==ITestResult.FAILURE) {
			test.get().fail(result.getThrowable());
			String screenshotPath =
			captureScreenshot(result.getMethod().getMethodName());
			try {
				test.get().addScreenCaptureFromPath(screenshotPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.get().pass("Test passed");
		} else if (result.getStatus()== ITestResult.SKIP){
			test.get().skip("Test Skipped");
		}

		if (driver != null) {
			driver.quit();
		}
	}

	@AfterSuite
	public void flushExtentReports(){
		extent.flush();
	}

	public String captureScreenshot(String testName){
		try {
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String screenshotDir = "test-output/screenshots";
			File destDir = new File(screenshotDir);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
			String path = screenshotDir + "/" + testName + "_" + timestamp + ".png";
			File destFile = new File(path);
			Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			return destFile.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}




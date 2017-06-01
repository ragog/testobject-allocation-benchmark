package com.testobject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class BasicTestSetup {

	private AppiumDriver driver;

	@Rule
	public TestName testName = new TestName();

	private String testNameString;
	private long startTime;

	@Before
	public void setUp() throws Exception {

		testNameString = "["+testName.getMethodName().toString().toUpperCase()+"]";
		startTime = new Date().getTime();

		String apiKey = System.getenv("TESTOBJECT_API_KEY");
		String device = System.getenv("TESTOBJECT_DEVICE");
		String appiumVersion = System.getenv("TESTOBJECT_APPIUM_VERSION");
		String automationName = System.getenv("TESTOBJECT_AUTOMATION_NAME");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("testobject_api_key", apiKey);
		capabilities.setCapability("testobject_device", device);
		capabilities.setCapability("testobject_appium_version", appiumVersion);
		capabilities.setCapability("automationName", automationName);

		printWithTimestamp("Test setup starting, initialising driver with capabilities:");
		System.out.println(capabilities.toString());

		driver = new IOSDriver(new URL("https://eu1.appium.testobject.com/wd/hub"), capabilities);

		printWithTimestamp("Session established.");

		printWithTimestamp(driver.getCapabilities().getCapability("testobject_test_report_url").toString());
		printWithTimestamp(driver.getCapabilities().getCapability("testobject_test_live_view_url").toString());

	}

	/* We disable the driver after EACH test has been executed. */
	@After
	public void tearDown() {
		printWithTimestamp("Test ended, tearing down session.");
		driver.quit();
		long endTime = new Date().getTime();
		printWithTimestamp("Session closed, total test duration: " + (endTime - startTime)/1000 + "s");
	}

	/* A simple addition, it expects the correct result to appear in the result field. */
	@Test
	public void iosAllocationTest() {
		printWithTimestamp("Test started, taking screenshot...");
		driver.getScreenshotAs(OutputType.BASE64);
		printWithTimestamp("Getting page source (not printing to save room...)");
		driver.getPageSource();

	}

	public void printWithTimestamp(String message) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd, HH:mm:ss").format(new Date());
		System.out.println(timeStamp + " " + testNameString + " " + message);
	}

}

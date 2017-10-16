package com.testobject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(Parameterized.class)
public class BasicTestSetup {

	private AppiumDriver driver;

	@Rule
	public TestName testName = new TestName();

	private String testNameString;
	private String testUUID;
	private long startTime;

	@Parameterized.Parameters
	public static List<Object[]> data() {
		return Arrays.asList(new Object[100][0]);
	}

	@Before
	public void setUp() throws Exception {

		testNameString = "["+testName.getMethodName().toString().toUpperCase()+"]";
		startTime = new Date().getTime();

		String url = System.getenv("TESTOBJECT_URL") != null ? System.getenv("TESTOBJECT_URL") : "https://eu1.appium.testobject.com/wd/hub";
		String apiKey = System.getenv("TESTOBJECT_API_KEY");
		String device = System.getenv("TESTOBJECT_DEVICE");
		String appiumVersion = System.getenv("TESTOBJECT_APPIUM_VERSION");
		String automationName = System.getenv("TESTOBJECT_AUTOMATION_NAME");
		String deviceCaching = System.getenv("TESTOBJECT_DEVICE_CACHING");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("testobject_api_key", apiKey);
		capabilities.setCapability("testobject_device", device);
		capabilities.setCapability("testobject_appium_version", appiumVersion);
		capabilities.setCapability("automationName", automationName);
		if (deviceCaching != null){
			capabilities.setCapability("testobject_cache_device", deviceCaching);
		}
		
		testUUID = UUID.randomUUID().toString();
		capabilities.setCapability("uuid", testUUID);

		driver = new IOSDriver(new URL(url), capabilities);

	}

	/* We disable the driver after EACH test has been executed. */
	@After
	public void tearDown() {
		driver.quit();
		long endTime = new Date().getTime();
		printWithTimestamp((endTime - startTime)/1000 + "");
	}

	/* A simple addition, it expects the correct result to appear in the result field. */
	@Test
	public void allocationTest() {
	}

	public void printWithTimestamp(String message) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd, HH:mm:ss").format(new Date());
		System.out.println(timeStamp + " " + testNameString + " " + message);
	}

}

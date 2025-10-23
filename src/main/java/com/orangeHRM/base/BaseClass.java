package com.orangeHRM.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.orangeHRM.actionDriver.ActionDriver;

public class BaseClass {

	protected static Properties prop;
	protected static WebDriver driver;
	private static ActionDriver actionDriver;

	// load the configuration file
	@BeforeSuite
	public void loadConfig() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);
	}

	@BeforeMethod
	public void setUp() throws IOException {
		System.out.println("Setting up WebDriver for: " + this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticWait(2);
		// Initialize the actionDriver only once
		if (actionDriver == null) {
			actionDriver = new ActionDriver(driver);
			System.out.println("ActionDriver instance is created.");
		}
	}

	// Initialize WebDriver based on browser defines in config.properties file
	private void launchBrowser() {
		String browser = prop.getProperty("browser");
		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else {
			throw new IllegalArgumentException(browser + " browser not supported!");
		}
	}

	// Configure browser settings
	private void configureBrowser() {
		// Implicit Wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		// Maximize the driver
		driver.manage().window().maximize();

		// Navigate to URL
		try {
			driver.get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Failed to Navigate to the URL" + e.getMessage());
		}
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				System.out.println("Unable to quit the driver" + e.getMessage());
			}
		}
		System.out.println("WebDriver instance is closed.");
		driver = null;
		actionDriver = null;

	}

	// Getter method for prop
	public static Properties getProp() {
		return prop;
	}

	/*
	// Driver getter method
	public WebDriver getDriver() {
		return driver;
	}
	*/
	
	// Getter Method for WebDriver
	public static WebDriver getDriver() {
		if (driver == null) {
			System.out.println("WebDriver is not initialized");
			throw new IllegalStateException("WebDriver is not initialized");
		}
		return driver;
	}
	
	// Getter Method for ActionDriver
		public static ActionDriver getActionDriver() {
			if (actionDriver == null) {
				System.out.println("ActionDriver is not initialized");
				throw new IllegalStateException("ActionDriver is not initialized");
			}
			return actionDriver;
		}

	// Driver setter method
	public void setDriver(WebDriver driver) {
		BaseClass.driver = driver;
	}

	// Static wait for pause
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}

}

package com.orangeHRM.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.orangeHRM.actionDriver.ActionDriver;
import com.orangeHRM.utilities.LoggerManager;

public class BaseClass {

	protected static Properties prop;
	//protected static WebDriver driver;
	//private static ActionDriver actionDriver;
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();
	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);

	// load the configuration file
	@BeforeSuite
	public void loadConfig() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);
		logger.info("config.properties file loaded");
	}

	@BeforeMethod
	public void setUp() throws IOException {
		System.out.println("Setting up WebDriver for: " + this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticWait(2);
		logger.info("WebDriver initialized and Browser maximized");
		logger.trace("This is a Trace message");
		logger.error("This is a Error message");
		logger.debug("This is a Debug Message");
		logger.fatal("This is a Fatal message");
		logger.warn("This is a Warn message");
		/*// Initialize the actionDriver only once
		if (actionDriver == null) {
			actionDriver = new ActionDriver(driver);
			logger.info("ActionDriver instance is created. "+Thread.currentThread().threadId());
		}*/
		//Initialize ActionDriver for the current Thread
		actionDriver.set(new ActionDriver(getDriver()));
		logger.info("ActionDriver initialized for thread: "+Thread.currentThread().threadId());
	}

	// Initialize WebDriver based on browser defines in config.properties file
	private void launchBrowser() {
		String browser = prop.getProperty("browser");
		if (browser.equalsIgnoreCase("chrome")) {
			//driver = new ChromeDriver();
			driver.set(new ChromeDriver());
			logger.info("ChromeDriver Instance is created.");
		} else if (browser.equalsIgnoreCase("firefox")) {
			//driver = new FirefoxDriver();
			driver.set(new FirefoxDriver());
			logger.info("FirefoxDriver Instance is created.");
		} else if (browser.equalsIgnoreCase("edge")) {
			//driver = new EdgeDriver();
			driver.set(new EdgeDriver());
			logger.info("EdgeDriver Instance is created.");
		} else {
			throw new IllegalArgumentException(browser + " browser not supported!");
		}
	}

	// Configure browser settings
	private void configureBrowser() {
		// Implicit Wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		// Maximize the driver
		getDriver().manage().window().maximize();

		// Navigate to URL
		try {
			getDriver().get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Failed to Navigate to the URL" + e.getMessage());
		}
	}

	@AfterMethod
	public void tearDown() {
		if (getDriver() != null) {
			try {
				getDriver().quit();
			} catch (Exception e) {
				System.out.println("Unable to quit the driver" + e.getMessage());
			}
		}
		logger.info("WebDriver instance is closed.");
		//driver = null;
		//actionDriver = null;
		driver.remove();
		actionDriver.remove();
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
		if (driver.get() == null) {
			System.out.println("WebDriver is not initialized");
			throw new IllegalStateException("WebDriver is not initialized");
		}
		return driver.get();
	}
	
	// Getter Method for ActionDriver
		public static ActionDriver getActionDriver() {
			if (actionDriver.get() == null) {
				System.out.println("ActionDriver is not initialized");
				throw new IllegalStateException("ActionDriver is not initialized");
			}
			return actionDriver.get();
		}

	// Driver setter method
	public void setDriver(ThreadLocal<WebDriver> driver) {
		BaseClass.driver = driver;
	}

	// Static wait for pause
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}

}

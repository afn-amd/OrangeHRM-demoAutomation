package com.orangeHRM.actionDriver;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionDriver {

	private WebDriver driver;
	private WebDriverWait wait;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	// Method to click an element
	public void click(By by) {
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
		} catch (Exception e) {
			System.out.println("unable to click element: " + e.getMessage());
		}
	}

	// Method to enter text into an input field
	public void enterText(By by, String value) {
		try {
			waitForElementToBeVisible(by);
			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(value);
		} catch (Exception e) {
			System.out.println("Unable to enter the value: " + e.getMessage());
		}
	}

	// Method to get text from an input field
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			return driver.findElement(by).getText();
		} catch (Exception e) {
			System.out.println("Unable to get the text: " + e.getMessage());
			return "";
		}
	}

	// Method to compare 2 texts
	public void compareText(By by, String expectedText) {
		try {
			waitForElementToBeVisible(by);
			String actualText = driver.findElement(by).getText();
			if (expectedText.equals(actualText)) {
				System.out.println("Texts are matching: " + actualText + " equals " + expectedText);
			} else {
				System.out.println("Texts are not matching: " + actualText + " does not equal " + expectedText);
			}
		} catch (Exception e) {
			System.out.println("unable to compare texts: " + e.getMessage());
		}
	}

	// Method to check if an element is displayed
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			boolean isDisplayed = driver.findElement(by).isDisplayed();
			if (isDisplayed) {
				System.out.println("Element is visible");
				return isDisplayed;
			} else {
				return isDisplayed;
			}
		} catch (Exception e) {
			System.out.println("element is not displayed: " + e.getMessage());
			return false;
		}
	}

	// Wait for the page to load
	public void waitForPageLoad(int timeOutInSec) {
		try {
			wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(WebDriver -> ((JavascriptExecutor) WebDriver)
					.executeScript("return document.readyState").equals("complete"));
			System.out.println("Page loaded successfully.");
		} catch (Exception e) {
			System.out.println("Page did not load within " + timeOutInSec + " seconds. Exception: " + e.getMessage());
		}
	}

	// Scroll to an element
	public void scrollToElement(By by) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("arguments[0], scrollInToView(true)", element);
		} catch (Exception e) {
			System.out.println("Unable to locate element: " + e.getMessage());
		}
	}

	// Wait for element to be clickable
	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			System.out.println("element is not clickable: " + e.getMessage());
		}
	}

	// Wait for element to be visible
	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			System.out.println("element is not visible: " + e.getMessage());
		}
	}
}

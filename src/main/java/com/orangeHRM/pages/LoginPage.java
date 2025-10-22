package com.orangeHRM.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangeHRM.actionDriver.ActionDriver;

public class LoginPage {

	private ActionDriver actionDriver;

	// Define locators using By class
	private By userNameField = By.name("username");
	private By passwordField = By.cssSelector("input[type='password']");
	private By loginButton = By.xpath("//button[text()=' Login ']");
	private By errorMessage = By.xpath("//p[text()='Invalid credentials']");

	public LoginPage(WebDriver driver) {
		actionDriver = new ActionDriver(driver);
	}

	// Method to perform login
	public void login(String userName, String passWord) {
		actionDriver.enterText(userNameField, "admin");
		actionDriver.enterText(passwordField, "admin123");
		actionDriver.click(loginButton);
	}

	// Method to check if error message is displayed
	public boolean isErrorMessageDisplayed() {
		return actionDriver.isDisplayed(errorMessage);
	}

	// Method to get the text from error message
	public String getErrorMessageText() {
		return actionDriver.getText(errorMessage);
	}

	// Verify if error is correct or not
	public void verifyErrorMessage(String expectedError) {
		actionDriver.compareText(errorMessage, expectedError);
	}

}

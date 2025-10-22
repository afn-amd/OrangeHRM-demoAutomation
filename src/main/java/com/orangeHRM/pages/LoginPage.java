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

	// Initialize ActionDriver object by passing WebDriver instance
	public LoginPage(WebDriver driver) {
		actionDriver = new ActionDriver(driver);
	}

	// Method to perform login
	public void login(String userName, String passWord) {
		actionDriver.enterText(userNameField, userName);
		actionDriver.enterText(passwordField, passWord);
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
	public boolean verifyErrorMessage(String expectedError) {
		return actionDriver.compareText(errorMessage, expectedError);
	}

}

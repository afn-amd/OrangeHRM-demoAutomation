package com.orangeHRM.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.pages.HomePage;
import com.orangeHRM.pages.LoginPage;
import com.orangeHRM.utilities.DataProviders;
import com.orangeHRM.utilities.ExtentManager;

public class LoginPageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}

	@Test(dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void verifyValidLoginTest(String username, String password) {
		// ExtentManager.startTest("Valid Login Test"); [TestListener]
		System.out.println("Running testMethod1 on thread: " + Thread.currentThread().threadId());
		ExtentManager.logStep("Navigating to Login Page entering username and password");
		loginPage.login(username, password);
		ExtentManager.logStep("Verifying Admin tab is visible or not");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab should be visible after succesfull login!");
		ExtentManager.logStep("Validation Successful!");
		homePage.logout();
		ExtentManager.logStep("Logged out Successfully!");
		staticWait(2);
	}

	@Test(dataProvider = "invalidLoginData", dataProviderClass = DataProviders.class)
	public void invalidLoginTest(String username, String password) {
		// ExtentManager.startTest("Invalid Login Test"); [TestListener]
		System.out.println("Running testMethod2 on thread: " + Thread.currentThread().threadId());
		ExtentManager.logStep("Navigating to Login Page entering username and password");
		loginPage.login(username, password);
		String expectedErrorMessage = "Invalid credentialss";
		Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage), "Test Failed: Invalid Error Message");
		ExtentManager.logStep("Validation Successful");
		ExtentManager.logStep("Logged out Successfully!");
	}

}

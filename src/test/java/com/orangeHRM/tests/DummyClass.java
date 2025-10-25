package com.orangeHRM.tests;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.utilities.ExtentManager;

public class DummyClass extends BaseClass {

	@Test
	public void dummyTest() {
		ExtentManager.startTest("DummyClass Test");
		String title = getDriver().getTitle();
		ExtentManager.logStep("Verifying the Title");
		assert title.equals("OrangeHRM"):"Test Failed - Title is NOT matching";
		System.out.println("Test Passes - Title is matching");
		ExtentManager.logSkip("This case is skipped");
		throw new SkipException("Skipping the test as part of Testing");
	}

}

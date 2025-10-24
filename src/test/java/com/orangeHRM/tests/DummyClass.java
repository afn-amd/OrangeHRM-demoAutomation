package com.orangeHRM.tests;

import org.testng.annotations.Test;

import com.orangeHRM.base.BaseClass;

public class DummyClass extends BaseClass {

	@Test
	public void dummyTest() {
		String title = getDriver().getTitle();
		assert title.equals("OrangeHRM"):"Test Failed - Title is NOT matching";
		
		System.out.println("Test Passes - Title is matching");
	}

}

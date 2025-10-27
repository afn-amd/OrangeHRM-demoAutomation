package com.orangeHRM.utilities;

import java.util.List;

import org.testng.annotations.DataProvider;

public class DataProviders {

	private static final String FILE_PATH = System.getProperty("user.dir")
			+ "/src/test/resources/testData/TestData.xlsx";

	@DataProvider(name = "validLoginData")
	public static Object[][] vaidLoginData() {
		return getSheetData("validLoginData");
	}

	@DataProvider(name = "invalidLoginData")
	public static Object[][] invaidLoginData() {
		return getSheetData("invalidLoginData");
	}

	private static Object[][] getSheetData(String sheetName) {
		List<String[]> sheetData = ExcelReaderUtility.getSheetData(FILE_PATH, sheetName);
		Object[][] data = new Object[sheetData.size()][sheetData.get(0).length];
		for (int i = 0; i < sheetData.size(); i++) {
			data[i] = sheetData.get(i);
		}
		return data;
	}

}

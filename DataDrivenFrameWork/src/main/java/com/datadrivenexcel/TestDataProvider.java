package com.datadrivenexcel;

import java.util.Hashtable;

public class TestDataProvider {

	
	
public static Object[][] getTestDat(String DataFilename, String Sheetname, String TestNAme) {

		ReadExcelfile readdata = new ReadExcelfile(
				System.getProperty("user.dir") + "\\src\\testdat\\folder" + DataFilename);
		String sheetName = Sheetname;
		String testName = TestNAme;

		// Find Start Row of TestCase
		int startRowNum = 0;
		while (!readdata.getCellData(sheetName, 0, startRowNum).equalsIgnoreCase(testName)) {
			startRowNum++;
		}
//		System.out.println("Test Starts from Row Number : " + startRowNum);

		int startTestColumn = startRowNum + 1;
		int startTestRow = startRowNum + 2;

		// Find Number of Rows of TestCase
		int rows = 0;
		while (!readdata.getCellData(sheetName, 0, startTestRow + rows).equals("")) {
			rows++;
		}
//		System.out.println("Total Numbe of Rows in Test : " +testName + " is - " +rows);

		// Find Number of Columns in Test
		int colmns = 0;
		while (!readdata.getCellData(sheetName, colmns, startTestColumn).equals("")) {
			colmns++;
		}
//		System.out.println("Total Number of Columns in Test : " +testName + " is - " +colmns);
		// Define Two Object Array
		Object[][] dataSet = new Object[rows][1];
		Hashtable<String, String> dataTable = null;
		int dataRowNumber = 0;
		for (int rowNumber = startTestRow; rowNumber <= startTestColumn + rows; rowNumber++) {
			dataTable = new Hashtable<String, String>();
			for (int colNumber = 0; colNumber < colmns; colNumber++) {
				String key = readdata.getCellData(sheetName, colNumber, startTestColumn);
				String value = readdata.getCellData(sheetName, colNumber, rowNumber);
				dataTable.put(key, value);
				// dataSet[dataRowNumber][colNumber]=readdata.getCellData(sheetName, colNumber,
				// rowNumber);
//				System.out.println(readdata.getCellData(sheetName, colNumber, rowNumber));
			}
			dataSet[dataRowNumber][0] = dataTable;
			dataRowNumber++;

		}
		return dataSet;
	}

}

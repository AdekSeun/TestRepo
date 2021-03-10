package com.datadrivenexcel;

public class readdata {
	
	
	public static void main(String agrs[]) {
		//Create the object of the ReadExcel file
		ReadExcelfile readData = new ReadExcelfile(System.getProperty("user.dir") + "\\Testdata\\Testing.xlsx");
	int xxx =	readData.getCellRowNum("Testing", "Name", "Adek");
	System.out.println(xxx);
	  int yyy =   readData.getRowCount("Testing");
		System.out.println(yyy);
		
	}
}

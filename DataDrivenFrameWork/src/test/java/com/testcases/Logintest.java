package com.testcases;

import java.util.Hashtable;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.baseUIclass.BaseUicommonmethod;
import com.datadrivenexcel.TestDataProvider;
import com.datadrivenexcel.TestDataProvider1;
import com.utilesclass.ExtentReportManger;

public class Logintest extends BaseUicommonmethod {

	
	
	@Test(dataProvider="getTestOneData")
	public void Testone(Hashtable<String, String> dataTable) {

		logger = report.createTest("Testone");

		logger.log(Status.INFO, "browser succesffully open");
		OpenBrowser("chrome");
		openURL("WebsiteUrl");
		enterText("UsernameText_CSS", dataTable.get("Username"));
		enterText("PassWordTextbox_Xpath", dataTable.get("Password"));
		elementClick("ClickElement_Xpath");
		takeScreenShotOnFailure();

	}
	
	
	
	@AfterTest
	public void endreport() {
		report.flush();
	}
	
	
//	//@Test
//	public void Testtwo() {
//		logger = report.createTest("Testwo");
//
//		logger.log(Status.INFO, "browser succesffully open");
//		OpenBrowser("chrome");
//		openURL("WebsiteUrl");
//		enterText("UsernameText_Name", "kunleseun35@yahoo.com");
//		enterText("PassWordTextbox_Xpath", "johncenaHHH35$");
//		elementClick("ClickElement_Xpath");
//		takeScreenShotOnFailure();
//	}
	
	
	@DataProvider
	public Object[][] getTestOneData(){
		return TestDataProvider1.getTestData("TestData_Testmanagement.xlsx", "Feature 1", "Test Three");
	}

	
	
	

}

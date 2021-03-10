package com.baseUIclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.utilesclass.DateUtils;
import com.utilesclass.ExtentReportManger;

public class BaseUicommonmethod {

	WebDriver d;
	public Properties prop;
	public ExtentReports report = ExtentReportManger.getReportInstance();
	public ExtentTest logger;

	// SoftAssert softAssert = new SoftAssert();

	/******************* OPENBROWSER ****************/

	public void OpenBrowser(String Browsername) {

		try {
			if (Browsername.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\Browserlib\\chromedriver.exe");
				d = new ChromeDriver();
				// dynamically reading this .System.out.println(System.getProperty("user.dir")

			} else if (Browsername.equalsIgnoreCase("Mozila")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\Browserlib\\geckodriver.exe");

				d = new FirefoxDriver();
			} else if (Browsername.equalsIgnoreCase("Opera")) {
				System.setProperty("webdriver.opera.driver",
						System.getProperty("user.dir") + "Browserlib\\operadriver.exe");
				d = new OperaDriver();

			} else {
				d = new SafariDriver();
			}

		} catch (Exception e) {
			logger.log(Status.FAIL, e.getMessage());
		}

		d.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		d.manage().window().maximize();
		d.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);

		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream file = new FileInputStream(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\ObjectRepository\\projectconfig.properties");
				prop.load(file);
			} catch (Exception e) {
				reportFail(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/****************** Open URL ***********************/

	public void openURL(String websiteURLKey) {
		try {
			d.get(prop.getProperty(websiteURLKey));
			reportPass(websiteURLKey + " Identified Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
//		d.get(prop.getProperty(websiteURLKey));

	}
	
	

	/****************** Close Browser ***********************/
	public void tearDown() {
		d.close();

	}

	/****************** Quit Browser ***********************/
	public void quitBrowser() {
		d.quit();

	}

	/****************** Enter Text ***********************/
	public void enterText(String xpathKey, String data) {
		try {
			getElement(xpathKey).sendKeys(data);
			reportPass(data + " - Entered successfully in locator Element : " + xpathKey);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
//		getElement(xpathKey).sendKeys(data);
		// d.findElement(By.xpath(prop.getProperty(xpathKey))).sendKeys(data);

	}

	/****************** Click Element ***********************/
	public void elementClick(String xpathKey) {
		try {
			getElement(xpathKey).click();
			reportPass(xpathKey + " : Element Clicked Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		// getElement(xpathKey).click();
		// d.findElement(By.xpath(prop.getProperty(xpathKey))).click();

	}
	
	
	public void verifyPageTitle(String pageTitle) {
		try {
			String actualTite = d.getTitle();
			logger.log(Status.INFO, "Actual Title is : " + actualTite);
			logger.log(Status.INFO, "Expected Title is : " + pageTitle);
			//Assert.assertEquals(actualTite, pageTitle);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	
	
	/****************** Select List Drop Down ******************/
	public void SelectElementInList(String locatorXpath, String Value) {
		try {
			List<WebElement> listElement = d.findElements(By.xpath(locatorXpath));
			for (WebElement listItem : listElement) {
				String prefix = listItem.getText();
				// System.out.println(prefix);
				if (prefix.contains(Value)) {
					// System.out.println("Inside if statenment");
					waitForPageLoad();
					listItem.click();
				}
			}
			logger.log(Status.INFO, "Selected the Defined Value : " + Value);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	
	
	/****************** Identify Element ***********************/
	public WebElement getElement(String locatorKey) {
		WebElement element = null;

		try {
			if (locatorKey.endsWith("_Id")) {
				element = d.findElement(By.id(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identidied : " + locatorKey);
			} else if (locatorKey.endsWith("_Xpath")) {
				element = d.findElement(By.xpath(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identidied : " + locatorKey);
			} else if (locatorKey.endsWith("_ClassName")) {
				element = d.findElement(By.className(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identidied : " + locatorKey);
			} else if (locatorKey.endsWith("_CSS")) {
				element = d.findElement(By.cssSelector(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identidied : " + locatorKey);
			} else if (locatorKey.endsWith("_LinkText")) {
				element = d.findElement(By.linkText(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identidied : " + locatorKey);
			} else if (locatorKey.endsWith("_PartialLinkText")) {
				element = d.findElement(By.partialLinkText(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identidied : " + locatorKey);
			} else if (locatorKey.endsWith("_Name")) {
				element = d.findElement(By.name(prop.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator Identidied : " + locatorKey);
			} else {
				reportFail("Failing the Testcase, Invalid Locator " + locatorKey);

			}

		} catch (Exception e) {

			// Fail the TestCase and Report the error
			reportFail(e.getMessage());
			e.printStackTrace();

			System.out.println("the message" + e.getMessage());
		}

		return element;
	}
	
	
	

	/****************** Handle Frames **********************/
	public void switchToFrame(String frameLocator) {
		try {
			logger.log(Status.INFO, "Switching Frame : " + frameLocator);
			d.switchTo().frame(frameLocator);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/****************** SwitchToFrame **********************/
	public void switchToFrameByIndex(int frameNumner) {
		try {
			logger.log(Status.INFO, "Switching Frame : " + frameNumner);
			d.switchTo().frame(frameNumner);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	
	
	/****************** Verify Element ***********************/
	public boolean isElementPresent(String locatorKey) {
		try {
			if (getElement(locatorKey).isDisplayed()) {
				reportPass(locatorKey + " : Element is Displayed");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}

	public boolean isElementSelected(String locatorKey) {
		try {
			if (getElement(locatorKey).isSelected()) {
				reportPass(locatorKey + " : Element is Selected");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}

	public boolean isElementEnabled(String locatorKey) {
		try {
			if (getElement(locatorKey).isEnabled()) {
				reportPass(locatorKey + " : Element is Enabled");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}

	
	

/****************** Capture Screen Shot ***********************/
	public void takeScreenShotOnFailure() {
		TakesScreenshot takeScreenShot = (TakesScreenshot) d;
		File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);
		// appache commoin io needs to be downloaded
		File destFile = new File(System.getProperty("user.dir") + "/ScreenShots/" + DateUtils.getTimeStamp() + ".png");
		try {
			FileUtils.copyFile(sourceFile, destFile);
			logger.addScreenCaptureFromPath(
					System.getProperty("user.dir") + "/ScreenShots/" + DateUtils.getTimeStamp() + ".png");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/****************** Handle Frames **********************/
	public void switchToFrme(String frameLocator) {
		try {
			logger.log(Status.INFO, "Switching Frame : " + frameLocator);
			d.switchTo().frame(frameLocator);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void switchToFrameByIndx(int frameNumner) {
		try {
			logger.log(Status.INFO, "Switching Frame : " + frameNumner);
			d.switchTo().frame(frameNumner);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void switchToDefaultFrame() {
		try {
			logger.log(Status.INFO, "Switching to Main Windpw");
			d.switchTo().defaultContent();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	
	/***************** Wait Functions in Framework *****************/
	public void waitForPageLoad() {
		JavascriptExecutor js = (JavascriptExecutor) d;

		int i = 0;
		while (i != 180) {
			String pageState = (String) js.executeScript("return document.readyState;");
			if (pageState.equals("complete")) {
				break;
			} else {
				waitLoad(1);
			}
		}

		waitLoad(2);

		i = 0;
		while (i != 180) {
			Boolean jsState = (Boolean) js.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
			if (jsState) {
				break;
			} else {
				waitLoad(1);
			}
		}
	}

	public void waitLoad(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	/****************** Reporting Functions ***********************/
	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenShotOnFailure();
		System.out.println("the test faile and this is the message" + reportString);

	}

	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	/****************** Assertion Functions ***********************/
//	public void assertTrue(boolean flag) {
//		softAssert.assertTrue(flag);
//	}
//
//	public void assertfalse(boolean flag) {
//		softAssert.assertFalse(flag);
//	}
//
//	public void assertequals(String actual, String expected) {
//		try{
//			logger.log(Status.INFO, "Assertion : Actual is -" + actual + " And Expacted is - " + expected);
//			softAssert.assertEquals(actual, expected);
//		}catch(Exception e){
//			reportFail(e.getMessage());
//		}	

}

//	@AfterMethod
//	public void afterTest() {
//		softAssert.assertAll();
//		driver.quit();




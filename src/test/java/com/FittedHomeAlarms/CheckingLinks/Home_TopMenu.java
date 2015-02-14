package com.FittedHomeAlarms.CheckingLinks;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FittedHomeAlarms.TestBase;
import com.FittedHomeAlarms.util.Constants;
import com.FittedHomeAlarms.util.TestDataProvider;
import com.FittedHomeAlarms.util.Utility;
import com.FittedHomeAlarms.util.Xls_Reader;

public class Home_TopMenu extends TestBase {
	
	@BeforeTest
	public void beFore(){
		initLogs(this.getClass());
	}
	
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="CheckingLinksDataProvider")
	public void Home_TopMenu(Hashtable<String, String> table) throws InterruptedException{ 
		String testmethod_name = new Object(){}.getClass().getEnclosingMethod().getName();
		//APPLICATION_LOG.debug("Executing Test:: "+testmethod_name);
		APPLICATION_LOG.debug("");
		validateRunmodes(testmethod_name, Constants.CHECKINGLINKS_SUITE, table.get(Constants.RUNMODE_COL));
		openBrowser(table.get(Constants.BROWSER_COL));
		navigate("testSiteURL");
		int i=2;
		String element = prop.getProperty("home_topmenu1_xpath")+i+prop.getProperty("home_topmenu2_xpath");
		//driver.findElement(By.xpath(element)).click();
		//System.out.println(driver.getTitle());
		while (isElementPresent(element)){
			//System.out.println();
			click(element);
			System.out.println(driver.findElement(By.xpath(element)).getText());
			String linktext=driver.findElement(By.xpath(element)).getText();
			if (linktext.contains("ABOUT"))
				//Assert.assertEquals(driver.getTitle().trim(), prop.getProperty("about").trim(), "not match");
			if (linktext.contains("NEW IN"))
				//Assert.assertEquals(driver.getTitle().trim(), prop.getProperty("newin").trim(), "not match");
			if (linktext.contains("HOME ALARMS"))
				Assert.assertEquals(driver.getTitle().trim(), prop.getProperty("homealarms").trim(), "not match");
			if (linktext.contains("SAFES"))
				Assert.assertEquals(driver.getTitle().trim(), prop.getProperty("safes").trim(), "not match");
			if (linktext.contains("CCTVS"))
				Assert.assertEquals(driver.getTitle().trim(), prop.getProperty("cctv").trim(), "not match");
			if (linktext.contains("ALARM SERVICING"))
				Assert.assertEquals(driver.getTitle().trim(), prop.getProperty("alarmservicing").trim(), "not match");
			i++;
			element = prop.getProperty("home_topmenu1_xpath")+i+prop.getProperty("home_topmenu2_xpath");
		}
	}

	@AfterMethod
	public void close(){
		quit();
	}
}

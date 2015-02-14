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

public class Home_HomeAlarms extends TestBase {
	
	@BeforeTest
	public void beFore(){
		initLogs(this.getClass());
	}
	
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="CheckingLinksDataProvider")
	public void Home_HomeAlarms(Hashtable<String, String> table) throws InterruptedException{ 
		String testmethod_name = new Object(){}.getClass().getEnclosingMethod().getName();
		//APPLICATION_LOG.debug("Executing Test:: "+testmethod_name);
		APPLICATION_LOG.debug("");
		validateRunmodes(testmethod_name, Constants.CHECKINGLINKS_SUITE, table.get(Constants.RUNMODE_COL));
		openBrowser(table.get(Constants.BROWSER_COL));
		navigate("testSiteURL");
		listall_homealarms();

	}


	@AfterMethod
	public void close(){
		//quit();
	}
}

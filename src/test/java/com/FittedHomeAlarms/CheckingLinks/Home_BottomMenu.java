package com.FittedHomeAlarms.CheckingLinks;
import java.util.Hashtable;

import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;

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
import com.thoughtworks.selenium.webdriven.commands.Click;

public class Home_BottomMenu extends TestBase {
	
	@BeforeTest
	public void beFore(){
		initLogs(this.getClass());
	}
	
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="CheckingLinksDataProvider")
	public void Home_BottomMenu(Hashtable<String, String> table) throws InterruptedException{ 
		String testmethod_name = new Object(){}.getClass().getEnclosingMethod().getName();
		//APPLICATION_LOG.debug("Executing Test:: "+testmethod_name);
		APPLICATION_LOG.debug("");
		validateRunmodes(testmethod_name, Constants.CHECKINGLINKS_SUITE, table.get(Constants.RUNMODE_COL));
		openBrowser(table.get(Constants.BROWSER_COL));
		navigate("testSiteURL");
		click(table.get("Menu1"));
		Thread.sleep(1000);
		click(table.get("Menu2"));
		Thread.sleep(1000);
		click(table.get("Menu3"));
		Thread.sleep(1000);
		click(table.get("Menu4"));
		Thread.sleep(1000);
		click(table.get("Menu5"));
		Thread.sleep(1000);
		click(table.get("Menu6"));
		Thread.sleep(1000);
		click(table.get("Menu7"));
		Thread.sleep(1000);
		click(table.get("Menu8"));
		Thread.sleep(1000);
		click(table.get("Menu9"));
		Thread.sleep(1000);
		click(table.get("Menu10"));
		Thread.sleep(1000);
		click(table.get("Menu11"));
		Thread.sleep(1000);
		click(table.get("Menu12"));
		Thread.sleep(1000);
		click(table.get("Menu13"));
		Thread.sleep(1000);
		
	}

	@AfterMethod
	public void close(){
		quit();
	}
}

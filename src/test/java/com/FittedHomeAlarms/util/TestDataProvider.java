package com.FittedHomeAlarms.util;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.FittedHomeAlarms.TestBase;

public class TestDataProvider extends TestBase {
	
	@DataProvider(name="CheckingLinksDataProvider")
		public static Object[][] getDataSuiteA(Method m){
		TestBase.init();
		Xls_Reader xls = new Xls_Reader(TestBase.prop.getProperty("xlsfileLocation")+Constants.CHECKINGLINKS_SUITE+".xlsx");	
		return Utility.getData(m.getName(), xls);
	}
	
}

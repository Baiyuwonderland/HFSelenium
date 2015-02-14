package com.FittedHomeAlarms.util;

import java.util.Hashtable;

public class Utility {
	
	// test suite is runnable or not
	public static boolean isSuiteRunnable(String suiteName, Xls_Reader xls){
		int rows= xls.getRowCount(Constants.SUITE_SHEET);
		for (int rNum = 2; rNum <= rows; rNum++) {
			String data =xls.getCellData(Constants.SUITE_SHEET, Constants.SUITENAME_COL, rNum);
			//System.out.println(data);
			if (data.equalsIgnoreCase(suiteName)){
				String runmode=xls.getCellData(Constants.SUITE_SHEET, Constants.RUNMODE_COL, rNum);
				if (runmode.equalsIgnoreCase(Constants.RUNMODE_YES))
					return true;
				else
					return false;
			}
		}
		return false;
	}

	// testcase is runnable or not
	 public static boolean isTestCaseRunnable(String testCase, Xls_Reader xls){
		 int rows= xls.getRowCount(Constants.TESTCASES_SHEET);
			for (int rNum = 2; rNum <= rows; rNum++) {
				String testNameXls =xls.getCellData(Constants.TESTCASES_SHEET, Constants.TESTCASES_COL, rNum);
				if (testNameXls.equalsIgnoreCase(testCase)){
					String runmode=xls.getCellData(Constants.TESTCASES_SHEET,Constants.RUNMODE_COL, rNum);
					if (runmode.equalsIgnoreCase(Constants.RUNMODE_YES))
						return true;
					else
						return false;
				}
			}
		 return false; 
	 }

	 
	 public static Object[][] getData(String testName, Xls_Reader xls){
			int rows= xls.getRowCount(Constants.DATA_SHEET);
			//System.out.println("total rows "+rows);
			// assume test case in row=1
			int testCaseRowNum=1;
			for (testCaseRowNum=1; testCaseRowNum<=rows; testCaseRowNum++) {
				String testNameXls=xls.getCellData(Constants.DATA_SHEET, 0, testCaseRowNum);
				if (testNameXls.equalsIgnoreCase(testName))
					break;
			}
			//System.out.println("Test starts from row number "+ testCaseRowNum);
			int dataStartRowNum=testCaseRowNum+2;
			int colStartRowNum=testCaseRowNum+1;
			//System.out.println("data start"+dataStartRowNum);
			// total number of rows of data
			int testRows=1;
			while (!xls.getCellData(Constants.DATA_SHEET, 0, dataStartRowNum+testRows).equals("")){
				testRows++;
			}
			//System.out.println("Total rows of data are "+testRows);
			// total cols of data
			int testcols=0;
			while (!xls.getCellData(Constants.DATA_SHEET, testcols, colStartRowNum).equals("")){
				testcols++;
			}
			//System.out.println("total cols of data are "+testcols);
			Object[][] data = new Object[testRows][1];
			int r=0;
			// print the complete data
			for (int rNum = dataStartRowNum; rNum < (dataStartRowNum+testRows); rNum++) {
				Hashtable<String, String> table = new Hashtable<String, String> ();
				for (int cNum = 0; cNum < testcols; cNum++) {
					//System.out.print(xls.getCellData(Constants.DATA_SHEET, cNum, rNum)+"\t");
					//data[r][cNum]=xls.getCellData(Constants.DATA_SHEET, cNum, rNum);
					table.put(xls.getCellData(Constants.DATA_SHEET, cNum, colStartRowNum), xls.getCellData(Constants.DATA_SHEET, cNum, rNum));
				}
				data[r][0]=table;
				r++;
				//System.out.println();
			}
			return data;
	 }
}

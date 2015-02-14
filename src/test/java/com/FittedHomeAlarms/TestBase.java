package com.FittedHomeAlarms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.SkipException;

import com.FittedHomeAlarms.util.Constants;
import com.FittedHomeAlarms.util.Utility;
import com.FittedHomeAlarms.util.Xls_Reader;

public class TestBase {
	
	public static Properties prop;
	public static Logger APPLICATION_LOG= Logger.getLogger("LogEntry");
	public  WebDriver driver;
	
	public static void init(){
		if (prop==null){
			String path=System.getProperty("user.dir")+"\\src\\test\\resources\\project.properties";
			prop= new Properties();
			try {
				FileInputStream fs = new FileInputStream(path);
				prop.load(fs);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	public void initLogs(Class<?> class1){
		FileAppender appender = new FileAppender();
		// configure the appender here, with file location, etc
		appender.setFile(System.getProperty("user.dir")+"//target//reports//"+CustomListener.resultFolderName+"//"+class1.getName()+".log");
		appender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
		appender.setAppend(false);
		appender.activateOptions();

		APPLICATION_LOG = Logger.getLogger(class1);
		APPLICATION_LOG.setLevel(Level.DEBUG);
		APPLICATION_LOG.addAppender(appender);
	}
	
	
	// validate runmode of any test names & suites
	public void validateRunmodes(String testName, String suiteName, String dataRunmode){
		init();
		// validate suite runmode
		boolean suiteRunmode=Utility.isSuiteRunnable(suiteName, new Xls_Reader(prop.getProperty("xlsfileLocation")+"Suite.xlsx"));
		boolean testRunmode= Utility.isTestCaseRunnable(testName, new Xls_Reader(prop.getProperty("xlsfileLocation")+suiteName+".xlsx"));
		boolean dataSetRunmode=false;
		if (dataRunmode.equals(Constants.RUNMODE_YES))
			dataSetRunmode=true;
		if (!(suiteRunmode && testRunmode && dataSetRunmode)){
			APPLICATION_LOG.debug("SKIPPING: ("+ testName + ") inside the Suite: ("+suiteName +") , [ SuiteRunmode:"+suiteRunmode +" | TestCaseRunmode:"+ testRunmode +" | DataSetRunmode:"+ dataSetRunmode+" ]");
			System.out.println("SKIPPING: ("+ testName + ") inside the Suite: ("+suiteName +") , [ SuiteRunmode:"+suiteRunmode +" | TestCaseRunmode:"+ testRunmode +" | DataSetRunmode:"+ dataSetRunmode+" ]");
			throw new SkipException("SKIPPING: ("+ testName + ") inside the Suite: ("+suiteName+")");
		}
		APPLICATION_LOG.debug("EXECUTING: ("+ testName + ") inside the Suite ("+suiteName+") , [ SuiteRunmode:"+suiteRunmode +" | TestCaseRunmode:"+ testRunmode +" | DataSetRunmode:"+ dataSetRunmode+" ]");
		System.out.println("EXECUTING: ("+ testName + ") inside the Suite ("+suiteName+") , [ SuiteRunmode:"+suiteRunmode +" | TestCaseRunmode:"+ testRunmode +" | DataSetRunmode:"+ dataSetRunmode+" ]");
	}
	
	

	/******************Generic functions*******************************************************************************************************/
	public void click(String identifier){
		APPLICATION_LOG.debug("\t\tClicking "+identifier+"="+ prop.getProperty(identifier));
		System.out.println("\t\tClicking "+identifier+"="+ prop.getProperty(identifier));
		try {
			//synchronize();
			if (identifier.endsWith("_xpath"))
				driver.findElement(By.xpath(prop.getProperty(identifier))).click();
			else if (identifier.endsWith("_id"))
				driver.findElement(By.id(prop.getProperty(identifier)));
			else if (identifier.endsWith("_linktext"))
				driver.findElement(By.linkText(prop.getProperty(identifier)));
			else if (identifier.endsWith("_css"))
				driver.findElement(By.cssSelector(prop.getProperty(identifier)));
			else 
				driver.findElement(By.xpath(identifier)).click();
		} catch (Throwable e) {
			Assert.fail("Element not found - "+identifier);
		}
	}
	
	public void openBrowser(String browsername){
		APPLICATION_LOG.debug("\t\tOpening "+ browsername);
		System.out.println("\t\tOpening "+ browsername);
		
		// normal
		/*
		if (browsername.equalsIgnoreCase("Mozilla")){
			driver = new FirefoxDriver();
			//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} else if (browsername.equalsIgnoreCase("Chrome")){
			System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriverexe"));
			driver = new ChromeDriver();
		} else if (browsername.equalsIgnoreCase("Safari")){
			driver = new SafariDriver();
			//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} else if (browsername.equalsIgnoreCase("IE")){
			System.setProperty("webdriver.ie.driver", prop.getProperty("IEDriverServerexe"));
			driver = new InternetExplorerDriver();
			
		} 
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		*/
		
		// grid
		
		try {
			DesiredCapabilities cap=new DesiredCapabilities();
			if (browsername.equalsIgnoreCase("windowsMozilla")){
				cap.setBrowserName("firefox");
				cap.setPlatform(Platform.WINDOWS);
			} else  if (browsername.equalsIgnoreCase("linuxMozilla")){
				cap.setBrowserName("firefox");
				cap.setPlatform(Platform.LINUX);
			} else if (browsername.equalsIgnoreCase("linuxChrome")){
				cap.setBrowserName("chrome");
				cap.setPlatform(Platform.LINUX);
			} else if (browsername.equalsIgnoreCase("windowsChrome")){
				cap.setBrowserName("chrome");
				cap.setPlatform(Platform.WINDOWS);
			} else if (browsername.equalsIgnoreCase("windowsSafari")){
				cap.setBrowserName("safari");
				cap.setPlatform(Platform.WINDOWS);
			} else if (browsername.equalsIgnoreCase("windowsIE")){
				cap.setBrowserName("iexplore");
				cap.setPlatform(Platform.WINDOWS);
			}
			
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				cap.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
				//cap.setCapability(CapabilityType.PROXY, "http://free-proxyserver.com/"); // browser will work on it
				try {
					driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			
			driver.manage().window().maximize();
			
		} catch (Throwable e) {
			Assert.fail("Not able to Open - "+ browsername);
		}
		
	}
	
	public void navigate(String urlKey){
		//synchronize();
		String url=prop.getProperty(urlKey);
		APPLICATION_LOG.debug("\t\tNavigating "+urlKey+"="+ url);
		System.out.println("\t\tNavigating "+urlKey+"="+ url);
		try {
			driver.get(url);
		} catch (Throwable e) {
			Assert.fail("Not able to Open - "+ url);
		}
	}
	
	public void input(String identifier, String data){
		//synchronize();
		APPLICATION_LOG.debug("\t\tInputting  "+ data +" into webelement "+ prop.getProperty(identifier));
		System.out.println("\t\tInputting  "+ data +" into webelement "+ prop.getProperty(identifier));
		try {
			if (identifier.endsWith("_xpath"))
				driver.findElement(By.xpath(prop.getProperty(identifier))).sendKeys(data);
			else if (identifier.endsWith("_id"))
				driver.findElement(By.id(prop.getProperty(identifier))).sendKeys(data);
			else if (identifier.endsWith("_name"))
				driver.findElement(By.name(prop.getProperty(identifier))).sendKeys(data);
			else if (identifier.endsWith("_css"))
				driver.findElement(By.cssSelector(prop.getProperty(identifier))).sendKeys(data);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			Assert.fail("Failed Inputting -"+ data +" into webelement "+ prop.getProperty(identifier));
		}
	}
	
	public boolean verifyTitle(String expectedTitleKey) {
		//synchronize();
		APPLICATION_LOG.debug("\t\tVerifying title "+ prop.getProperty(expectedTitleKey));
		System.out.println("\t\tVerifying title "+ prop.getProperty(expectedTitleKey));
		boolean istitle=false;
		String expectedTitle="";
		String actualTitle="";
		try {
			expectedTitle=prop.getProperty(expectedTitleKey);
			actualTitle=driver.getTitle();
			if (expectedTitle.equals(actualTitle))
				istitle= true;
			else
				istitle= false;
		} catch (Exception e) {
			Assert.fail("Failed verifying title -"+ expectedTitle);
		}
		return istitle;
	}
	
	public boolean isElementPresent(String identifier){
		APPLICATION_LOG.debug("\t\tChecking iselement present "+ identifier +"="+ prop.getProperty(identifier));
		System.out.println("\t\tChecking iselement present "+ identifier +"="+ prop.getProperty(identifier));
		//synchronize();
		int size=0;
		if (identifier.endsWith("_xpath"))
			size=driver.findElements(By.xpath(prop.getProperty(identifier))).size();
		else if (identifier.endsWith("_id"))
			size=driver.findElements(By.id(prop.getProperty(identifier))).size();
		else if (identifier.endsWith("_name"))
			size=driver.findElements(By.name(prop.getProperty(identifier))).size();
		else if (identifier.endsWith("_css"))
			size=driver.findElements(By.cssSelector(prop.getProperty(identifier))).size();
		else  // not in prop file
			size=driver.findElements(By.xpath(identifier)).size();
		if (size>0)
			return true;
		else
			return false;
		
	}
	public void windowManagement(){
			Set<String> winIds = driver.getWindowHandles();
			System.out.println("total browsers-> "+winIds.size());
			Iterator it = winIds.iterator();
			String mainWindowId=(String) it.next();
			String tabWindowId=(String) it.next();
			System.out.println(mainWindowId); // print the 1st window id
			System.out.println(tabWindowId); // print the 2nd window id
			driver.switchTo().window(mainWindowId); //switching to the mainwindow
			/*
			driver.switchTo().window(tabWindowId); //switching to the tabwindow
			driver.findElement(By.xpath(".//*[@id='TXName']")).sendKeys("momo is cooking spare ribs henni");
			driver.close(); // close the current window on which the focus is there
			driver.switchTo().window(mainWindowId); //switching to the mainwindow
			*/
	}
	

	public  void synchronize(){
		APPLICATION_LOG.debug("Waiting for page to load");
		Object result =((JavascriptExecutor) driver).executeScript(
				"function pageloadingtime()"+ 
				"{"+
				"return 'Page has completely loaded'"+ // page loading function returns the 'String' in single qoutes
				"}"+
				"return (window.onload=pageloadingtime());" ); // onpageload = call the pageloadingtime() function
		System.out.println(result);
		//return Constants.KEYWORD_PASS; 
	}
	
	public String getText(String identifier){
		//synchronize();
		APPLICATION_LOG.debug("\t\tGetting text from  "+ identifier +"="+ prop.getProperty(identifier));
		System.out.println("\t\tGetting text from  "+ identifier +"="+ prop.getProperty(identifier));
		String text="";
		if (identifier.endsWith("_xpath"))
			text=driver.findElement(By.xpath(prop.getProperty(identifier))).getText();
		else if (identifier.endsWith("_id"))
			text=driver.findElement(By.id(prop.getProperty(identifier))).getText();
		else if (identifier.endsWith("_name"))
			text=driver.findElement(By.name(prop.getProperty(identifier))).getText();
		else if (identifier.endsWith("_css"))
			text=driver.findElement(By.cssSelector(prop.getProperty(identifier))).getText();
		else
			text=driver.findElement(By.xpath(prop.getProperty(identifier))).getText();
		return text;
	}
	
	public void quit(){
		APPLICATION_LOG.debug("\t\tQuiting Driver");
		System.out.println("\t\tQuiting Driver");
		if (driver!=null){
			
			driver.quit();
			
			driver=null;
		}	
	}
	/************************* Application specific function ******************************************************************/
	public void listall_homealarms() {
		int i=4;
		String element = prop.getProperty("home_topmenu1_xpath")+i+prop.getProperty("home_topmenu2_xpath");
		if (isElementPresent(element)){
			click(element);
		}
		while (driver.findElement(By.xpath("//div[@class='more_prod_div']")).getAttribute("style")=="display:none"){
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,2500)", "");
		}
		while (driver.findElement(By.xpath("//div[@id='load_more_divs']")).isDisplayed()){
			click("loadmore_xpath");
			while (driver.findElement(By.xpath("//div[@class='more_prod_div']")).getAttribute("style")=="display:none"){
				((JavascriptExecutor) driver).executeScript("window.scrollBy(0,2500)", "");
			}
			if (!driver.findElement(By.xpath("//div[@id='load_more_divs']")).isDisplayed())
				break;
		}
	}

	
	public void doLogin(String browser, String username, String password) throws InterruptedException{
		//APPLICATION_LOG.debug("\t\tLogging into application from "+browser+" username="+username+" password="+password);
		//System.out.println("\t\t Logging into application from "+browser+" username="+username+" password="+password);
		openBrowser(browser);
		navigate("testSiteURL");
		Assert.assertTrue(isElementPresent("moneyLink_xpath"), "Element not found moneyLink_xpath");
		click("moneyLink_xpath");
		Thread.sleep(3000);
		click("myprotfolio_xpath");
		
		//verifyTitle("portfolioPage");
		//Assert.assertTrue(verifyTitle("portfolioPage"), "Title not match "+driver.getTitle()); // ifnot true error message is displayed
		
		input("loginUsername_xpath", username);
		click("continueButton_xpath");
		Thread.sleep(5000);
		input("loginPassword_xpath", password);
		
		click("loginButton_xpath");
		//click("mywatchlist_xpath");
		//System.out.println("Succes " + browser);
		//Thread.sleep(2000);
		
	}
	
	
	
	public void doDefaultLogin(String browser) throws InterruptedException{
		doLogin(browser, prop.getProperty("defaultUsername"), prop.getProperty("defaultPassword"));
	}
	
	public void doMainLinksCheck(String string) {
		// TODO Auto-generated method stub
		
	}
}

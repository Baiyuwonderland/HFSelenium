package roughwork;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WebDriverTemplate {
	
	public static Properties prop;
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
	
	@Test (dataProvider="browserData")
	public void testyahoo(String browsername) throws InterruptedException{		
		init();
			if (browsername.equals("Mozilla")){
				driver = new FirefoxDriver();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			} else if (browsername.equals("Chrome")){
				System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriverexe"));
				driver = new ChromeDriver();
			} else if (browsername.equals("Safari")){
				driver = new SafariDriver();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			} else if (browsername.equals("IE")){
				System.setProperty("webdriver.ie.driver", prop.getProperty("IEDriverServerexe"));
				driver = new InternetExplorerDriver();
				driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			} 
			
			driver.manage().window().maximize();
				driver.get("http://www.fittedhomealarms.co.uk/");
				driver.findElement(By.xpath("html/body/div[6]/div[2]/div[1]/div[2]/div[1]/ul/li[2]/a")).click();
				
				
				/*
				WebElement box = driver.findElement(By.xpath("html/body/div[6]/div[2]/div[1]/div[2]"));
				List<WebElement> boxlinks= box.findElements(By.tagName("a"));
				for (WebElement webElement : boxlinks) {
					driver.findElement(By.xpath(webElement.toString())).click();
					driver.navigate().back();
				}
				*/
	}

	@DataProvider()
	public Object[][] browserData(){
		Object[][] data = new Object[1][1];
		data[0][0]="Mozilla";
		//data[1][0]="Chrome";
		//data[2][0]="Safari";
		//data[3][0]="IE";
		return data;
	}
}

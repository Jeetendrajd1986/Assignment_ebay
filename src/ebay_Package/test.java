package ebay_Package;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class test {
	public static void main(String[] args) {
		System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"//DriversForBrowser//geckodriver1.exe");
		
		WebDriver driver=new FirefoxDriver();
		
		//logger.log(LogStatus.INFO, "Launch the Browser ");
		driver.manage().window().maximize();
	    //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    driver.get("https://www.ebay.com");
	}

}

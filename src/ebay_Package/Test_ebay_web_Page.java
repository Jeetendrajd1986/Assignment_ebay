package ebay_Package;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.ShutdownHookProcessDestroyer;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.server.handler.interactions.touch.Scroll;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class Test_ebay_web_Page {
	
	public static WebDriver driver;
	public static WebElement we ;
	
	public static ExtentReports reports;
	public static ExtentTest logger;
		
    public static SoftAssert softassert=new SoftAssert();
	
	
	// Package use as org.apache.log4j.Logger;
	//public static Logger log=Logger.getLogger("Test_ebay_web_Page");
    

@BeforeTest
public void Reportsetup()
{
	
	
BasicConfigurator.configure();
	
	//Configure Test4j Properties
PropertyConfigurator.configure("Log4j.properties");

		
reports =new ExtentReports(System.getProperty("user.dir")+"//PathExtentReportOFTestNG//"+"ebaytest_ExtentReport.html",true);	

reports.addSystemInfo("Host Name", "Jeetendra Dodke")
.addSystemInfo("Enviournment", "Web Automation for ebay wbsite").addSystemInfo("UserName", "Jeetendra Dodke");
reports.loadConfig(new File((System.getProperty("user.dir")+"\\ExtentReportXMlLoadConfig\\ExtentReport.xml")));


}
   


@BeforeMethod
public void Launch() throws Exception
	{
	System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//DriversForBrowser//chromedriver.exe");
	driver = new ChromeDriver();
	driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.get("https://www.ebay.com");
	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	
	}



@Test(priority=1, description="Verify the ebay functionality")	
public void addproductincart() throws Exception
{


	logger=reports.startTest("ebay Test","Verify that ebay search functionality for the prodcut")
			.assignCategory("Functional Test");

	File src=new File(System.getProperty("user.dir")+"//ExcelFiles//TestData.xlsx");	// .appache.common method"

	FileInputStream fis=new FileInputStream(src);
	XSSFWorkbook wb=new XSSFWorkbook(fis);
	XSSFSheet sheet1=wb.getSheetAt(0); //Focus on sheet 1 i.e sheet1
	
	int rowcount=sheet1.getLastRowNum();
	for(int i=0;i<=rowcount;i++)
	{
		String data=sheet1.getRow(i).getCell(0).getStringCellValue(); 
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		String exp_data=data;
		if(data.contains("Sony tv"))
		{
				
			driver.findElement(By.xpath(".//*[@id='gh-ac']")).sendKeys(exp_data);
			logger.log(LogStatus.INFO, "Launch the browser and search Sony Tv Product");
			
			boolean Expected_result=driver.findElement(By.xpath(".//*[@id='gh-ac']")).isDisplayed();
			softassert.assertTrue(Expected_result, "Expected text as "+ exp_data);
			logger.log(LogStatus.PASS, "Actual Result:: "+exp_data);
			
			driver.findElement(By.xpath(".//*[@id='gh-btn']")).click();
			logger.log(LogStatus.INFO, "Products of Soney TV Displayed on search result");
	
		}
	}
	
	
	JavascriptExecutor js=(JavascriptExecutor) driver;
	js.executeScript("window.scrollBy(0,400)");
	//js.executeScript("Scroll(0,400)");
	logger.log(LogStatus.INFO, "Scroll and Select the screen size 50 – 60 ");
	driver.findElement(By.xpath(".//*[@id='AnswersModule']/div/div[2]/button[2]")).click();
	
	boolean expected_screensize= driver.findElement(By.xpath(".//*[@id='AnswersModule']/div/div[2]/nav/ul/li[4]/a/div")).isDisplayed();
	if(expected_screensize=true)
	{logger.log(LogStatus.INFO, "Verify the screen Size");
		logger.log(LogStatus.PASS, "Actual Result:: "+expected_screensize);
	}
	
	//driver.findElement(By.linkText("50 - 60")).click();
	driver.findElement(By.xpath(".//*[@id='AnswersModule']/div/div[2]/nav/ul/li[4]/a/div")).click();
	
	js.executeScript("window.scrollBy(0,600)");
	String productname=driver.findElement(By.xpath(".//*[@id='item1a3e9b698c']/h3/a")).getText();
	driver.findElement(By.xpath(".//*[@id='item1a3e9b698c']/h3/a")).click();
	
	
	
	//-	Item condition: is not empty
	
	boolean itemcondition=driver.findElements(By.xpath(".//*[@id='vi-itm-cond']")).isEmpty();
	String itemcondition_text=driver.findElement(By.xpath(".//*[@id='vi-itm-cond']")).getText();
	if(itemcondition=true)
	{
		logger.log(LogStatus.INFO, "Verify the item condition");
		logger.log(LogStatus.PASS, "Item condition:: "+itemcondition_text);
	}else
	{
		logger.log(LogStatus.FAIL, "Expected Result:: "+itemcondition_text);
	}
	
	boolean Price=driver.findElement(By.xpath(".//*[@id='prcIsum-lbl']")).isDisplayed();
	
	String Priceformat=driver.findElement(By.xpath(".//*[@id='prcIsum']")).getText();
	   	
			if(Price=true)
			{
				logger.log(LogStatus.INFO, "Verify the price format");
				logger.log(LogStatus.PASS, "Price FOrmat displayed as:: "+Priceformat);
			
			}
	
			driver.findElement(By.xpath(".//*[@id='isCartBtn_btn']")).click();
			
		boolean shopping_card_is_showed=driver.findElement(By.xpath(".//*[@id='pageLevelMessaing']/div/div")).isDisplayed();
		 String shopping_card_is_showed_text=driver.findElement(By.xpath(".//*[@id='pageLevelMessaing']/div/div")).getText();
			if(shopping_card_is_showed=true)
			{
				logger.log(LogStatus.INFO, "Verify that that the shopping card is showed");
				logger.log(LogStatus.PASS, "INformation of shopping card shown as :: "+shopping_card_is_showed_text);
			
			}
	
			//Step 9: verify the information in Shopping Cart
			driver.findElement(By.xpath(".//*[@id='gh-cart-1']")).click();
			//String productname=driver.findElement(By.xpath(".//*[@id='item1a3e9b698c']/h3/a")).getText();
			
			String Expected_AddedProduct=driver.findElement(By.xpath(".//*[@id='112719522188_title']/a")).getText();
			boolean addcard_page=driver.findElement(By.xpath(".//*[@id='PageTitle']/h1")).isDisplayed();
			if(addcard_page=true)
		{
				logger.log(LogStatus.INFO, "Verify the added product name");
				logger.log(LogStatus.PASS, "Selected product name in  add to card screen :: "+productname);
				logger.log(LogStatus.PASS, "Added Prodcut Name is displayed as :: "+Expected_AddedProduct);
				
				
			String price_verifyin_cart=driver.findElement(By.xpath(".//*[@id='926891111']/div[1]/div[2]/div/div[2]/div[1]/span/div")).getText();
			logger.log(LogStatus.INFO, "Verify the added product Price");
			logger.log(LogStatus.PASS, "Selected product Price in added to card  screen:: "+Priceformat);
			logger.log(LogStatus.PASS, "Added Prodcut Price is displayed as :: "+price_verifyin_cart);
			
						
			
		}
			
			driver.findElement(By.xpath(".//*[@id='ptcBtnBottom']")).click();
			
			//Step 10: Proceed checkout as guest
			driver.findElement(By.xpath(".//*[@id='gtChk']")).click();
			
			//Step 11: check that the guest checkout screen is showed
		boolean gustcheckout_page=driver.findElement(By.xpath("html/body/h1")).isDisplayed();
		String gustcheckout_page_text=driver.findElement(By.xpath("html/body/h1")).getText();
		if(gustcheckout_page=true)
		{
				logger.log(LogStatus.INFO, "Verify the Guest Checkout page");
				logger.log(LogStatus.PASS, "Guest Check page displayed as :: "+productname);
				logger.log(LogStatus.PASS, "Added Checkout page displayed ass :: "+gustcheckout_page_text);
		
		}	
	wb.close();

}

@AfterMethod()

public void tearDown(ITestResult result) throws Exception {
 if(result.getStatus()==ITestResult.FAILURE)
  {
    String screeshot_path=getscreenshot(driver,result.getName());
    String image=logger.addScreenCapture(screeshot_path);
   

    logger.log(LogStatus.FAIL, "Test Case is Fail", image);
    logger.log(LogStatus.FAIL, result.getThrowable());
    reports.endTest(logger); // ending test
   
  }

 else if (result.getStatus()==ITestResult.SUCCESS) 
 {

	String screeshot_path=getscreenshot(driver,result.getName());
	String image=logger.addScreenCapture(screeshot_path);  // logger.addScreenCapture is a method to capture screenshot
	logger.log(LogStatus.PASS, "Test Case is Pass", image);
	reports.endTest(logger); // ending test
  }
else if (result.getStatus()==ITestResult.SKIP) {
{
	logger.log(LogStatus.SKIP, "Test Case is Skip");
	logger.log(LogStatus.SKIP, result.getThrowable());
	reports.endTest(logger); // ending test
}


 // reports.endTest(logger); // ending test  using here giving error ask sir
 //reports.close(); //may be use here
 driver.quit();
 System.out.println("After---------------Mthod");

}
}

@AfterTest
public void extentreportsetupend()
{
	
	reports.flush();  // writing everything to document
    reports.close(); // closed your all reports
}






public static String getscreenshot(WebDriver screenshot,String screenshotName) throws IOException 
{

SimpleDateFormat sdf=new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
Date date=new Date();// Date is java.util
screenshotName=sdf.format(date);

File src=((TakesScreenshot) screenshot).getScreenshotAs(OutputType.FILE);
String des=System.getProperty("user.dir")+"\\ScreenShots\\"+screenshotName+".png";
File destination=new File(des);
FileUtils.copyFile(src,destination);	// .appache.common method"
System.out.println("Sceenshot is Captured");




/*
	}catch(Exception e)
	{
		System.out.println("Exception while taking screenshot");
		return e.getMessage();
	}
*/
return des;

}




}
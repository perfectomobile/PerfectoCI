package com.perfectomobile.test;

import java.io.File;
import java.util.Random;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.perfectomobile.dataDrivers.excelDriver.ExcelDriver;
import com.perfectomobile.utils.PerfectoUtils;
//import com.perfectomobile.selenium.util.EclipseConnector;
import com.perfectomobile.walmartPOM.SearchItemsPageView;
import com.perfectomobile.walmartPOM.WalmartBaseView;

public class WalmartItemTest extends BasicTest2 {
	
	@Factory(dataProvider="factoryData")
	public WalmartItemTest(DesiredCapabilities caps){
		super(caps);
	}
	@Test (dataProvider = "searchItemsData")
	public void searchItemsTest(String itemSerial, String itemDescription, String itemPrice) throws Exception {
		boolean testFail = false;
	
		// Get Excel file path
		 
		  ClassLoader classLoader = PerfectoUtils.class.getClassLoader();
		  File inputWorkbook = new File(classLoader.getResource("testResults.xlsx").getFile());
		  String absolutePath = inputWorkbook.getAbsolutePath();
		  
		  // Open workbook
		  ExcelDriver ed = new ExcelDriver();
		  ed.setWorkbook(absolutePath);
	 	  ed.setSheet(this.deviceDesc, true);
	 	  ed.setTestCycle(this.testCycle, true);
	 	  Reporter.log(String.valueOf(Thread.currentThread().getId()));
	 	  try{
    	
	    	//this.driver.get("http://google.com");
	    	SearchItemsPageView view = new WalmartBaseView(this.driver).init().searchItem(itemSerial);
	    	 
	        String actualPrice = view.getItemPriceByIndex(1);
	        String actualDescription = view.getItemNameByIndex(1);
	        
	        if(!actualDescription.equals(itemDescription)){
	        	testFail = true;
	        	Reporter.log("Value is: " + actualDescription + ", Should be: " + itemDescription);
	        	String errorFile = PerfectoUtils.takeScreenshot(driver);
	    		Reporter.log("Error screenshot saved in file: " + errorFile);
	        }
	        if(!actualPrice.equals(itemPrice)){
	        	testFail = true;
	        	Reporter.log("Value is: " + actualPrice + ", Should be: " + itemPrice);
	        	String errorFile = PerfectoUtils.takeScreenshot(driver);
	        	Reporter.log("Error screenshot saved in file: " + errorFile);
	        }
	    }
	    catch(Exception e){
	    	ed.setResultByTestCycle(false, this.testName, itemSerial, itemDescription, itemPrice);
	    	Assert.fail("See Reporter log for details");
	    }
	    
	    if(testFail){
	    	ed.setResultByTestCycle(false, this.testName, itemSerial, itemDescription, itemPrice);
	    	Assert.fail("See reporter log for details");
	    }
	    else{
	    	ed.setResultByTestCycle(true, this.testName, itemSerial, itemDescription, itemPrice);
	    }
	    //System.out.println(price);
	    //Reporter.log(price);
	      
	}
 
	@DataProvider (name = "searchItemsData")
	public Object[][] searchItemsData() throws Exception{
		
		  ClassLoader classLoader = PerfectoUtils.class.getClassLoader();
		  File inputWorkbook = new File(classLoader.getResource("testData.xlsx").getFile());
		  String absolutePath = inputWorkbook.getAbsolutePath();
		  // Open workbook
		  ExcelDriver ed = new ExcelDriver();
		  ed.setWorkbook(absolutePath);
		  
		  // Open sheet
		  ed.setSheet("items", false);
		  
		  // Read the sheet into 2 dim (String)Object array.
		  // "3" is the number of columns to read.
		  Object[][] s = ed.getData(3);

		  return s;
	}
	
/*	
	@Test(dataProvider="itemsDP2")
	public void secondTest(String testParam) throws InterruptedException{
//		Reporter.log("Mock Test: " + this.testName + " - " + testParam);
		Reporter.log(String.valueOf(Thread.currentThread().getId()));
		Random rand = new Random();
		int  n = rand.nextInt(3000) + 1;
		Thread.sleep(n);
	}
	 
	 @DataProvider (name = "itemsDP2")
	 public Object[][] itemsDP2() {
		 
		 return new Object[][]{{"0"}, {"1"}, {"2"}, {"3"}};
		 
	 }
*/

}
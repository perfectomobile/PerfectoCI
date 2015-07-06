package com.perfectomobile.yelp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.SendKeysAction;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;

import com.perfectomobile.utils.BaseObject;
import com.perfectomobile.utils.PerfectoUtils;


public class YelpBaseView extends BaseObject{
	
	String url = "http://www.walmart.com";
	
	// Page elements
	//com.yelp.android:id/left_unfocused_view
	private By yelpLogo	= By.xpath("//*[@resource-id='com.yelp.android:id/left_unfocused_view']");
	
	/**********************************************************************
	 * 		Constructor
	 **********************************************************************/
	public YelpBaseView(RemoteWebDriver driver){
		super(driver);
		
	}
	
	/**********************************************************************
	 * 		init: initializes the driver.
	 * @throws IOException 
	 **********************************************************************/
	public YelpBaseView init() throws IOException{
		//set timeouts
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		//validate page opened	
		try{
			fluentWait(yelpLogo, 20);
		}
		catch(Exception e){
			String errorFile = PerfectoUtils.takeScreenshot(driver);
			Reporter.log("Error screenshot saved in file: " + errorFile);
			throw new IllegalStateException();
		}	
		return this;
	}
	
	
	/**********************************************************************
	 * 	clickHomePage	
	 * 		This method clicks on the Walmart image link to navigate site to its 
	 * 		home page
	 * 		
	 * 		@return	new instance of the search stores page
	 **********************************************************************/
	public YelpBaseView clickHomePage(){
		try {
			//click on locate stores button
			click(yelpLogo);
			return new YelpBaseView(this.driver);
			
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
		
	}
	
}

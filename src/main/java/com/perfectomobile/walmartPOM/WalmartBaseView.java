package com.perfectomobile.walmartPOM;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.SendKeysAction;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;

import com.perfectomobile.utils.BaseObject;
import com.perfectomobile.utils.PerfectoUtils;


public class WalmartBaseView extends BaseObject{
	
	String url = "http://www.walmart.com";
	
	// Page elements
	private By walmartLogo	= By.xpath("//a[@class='logo js-logo display-block-l' or @class='walmart-header']");
	private By searchArea	= By.xpath("(//input)[1]");
	private By searchButton = By.xpath("//*[starts-with(@class,'search-go button') or starts-with(@class,'searchbar-submit')]"); 
	private By storeLocatorButton = By.xpath("//*[text()='Store Finder' or text()='Store Locator']");
	
	//additional elements on the page (yet to write scenarios for them)
	private By cartIcon	= By.xpath("//*[contains(@class,'show-cart') or contains(@class,'wmicon-cart')]");
	private By signInLink	= By.xpath("//*[@href='/signin']");
	private By logoutButton = By.xpath("//span[@class='js-account-logged-in active']");
	
	/**********************************************************************
	 * 		Constructor
	 **********************************************************************/
	public WalmartBaseView(RemoteWebDriver driver){
		super(driver);
		
	}
	
	/**********************************************************************
	 * 		init: initializes the driver.
	 * @throws IOException 
	 **********************************************************************/
	public WalmartBaseView init() throws IOException{
		//set timeouts
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//open url
		driver.get(url);
		//validate page opened	
		try{
			fluentWait(walmartLogo, 20);
		}
		catch(Exception e){
			String errorFile = PerfectoUtils.takeScreenshot(driver);
			Reporter.log("Error screenshot saved in file: " + errorFile);
			throw new IllegalStateException();
		}	
		return this;
	}
	
	/**********************************************************************
	 * 	SearchItemsPageView	
	 * 		This method searches a given product in the walmart site.
	 * 		
	 * 		@param item	the prduct to search for
	 * 		@return	new instance of the view to the searched items page
	 * @throws IOException 
	 **********************************************************************/
	public SearchItemsPageView searchItem(String item) throws IOException{
		
		try {
			//search item and click search button:
			sendKeys(searchArea, item);
			click(searchButton);
			
			return new SearchItemsPageView(this.driver);
			
		} catch (Exception e) {
			
			String errorFile = PerfectoUtils.takeScreenshot(driver);
			Reporter.log("Error screenshot saved in file: " + errorFile);
			return null;
		}
		
	}
	
	/**********************************************************************
	 * 	SearchStoresPageView	
	 * 		This method clicks on the "stores" link to navigate site to the 
	 * 		stores page
	 * 		
	 * 		@return	new instance of the search stores page
	 * @throws IOException 
	 **********************************************************************/
	public SearchStoresPageView clickLocateStores() throws IllegalStateException {
		
		try {
			//click on locate stores button
			click(storeLocatorButton);
			return new SearchStoresPageView(this.driver);
			
		} catch (Exception e) {
			
			PerfectoUtils.getScreenshotOnError(driver);
			throw new IllegalStateException();
		}
		
	}
	
	/**********************************************************************
	 * 	clickHomePage	
	 * 		This method clicks on the Walmart image link to navigate site to its 
	 * 		home page
	 * 		
	 * 		@return	new instance of the search stores page
	 **********************************************************************/
	public WalmartBaseView clickHomePage(){
		try {
			//click on locate stores button
			click(walmartLogo);
			return new WalmartBaseView(this.driver);
			
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
		
	}
	
}

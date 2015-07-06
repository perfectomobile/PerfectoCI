package com.perfectomobile.walmartPOM;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;

import com.perfectomobile.utils.*;

public class SearchStoresPageView extends WalmartBaseView {
	//default page elements
	private By pageText = By.xpath("//*[contains(text(),'Enter a location to find') or contains(text(),'Use the search bar') or text()='Filters']");
	//private By goButton = By.cssSelector("html > body.min-height > div:nth-of-type(1) > div.main-content > div:nth-of-type(2) > div.location-list > form.location-search-bar > div.search-bar > div.search-active-buttons.button-container.choice-group > button.button.secondary-button.primary-button");
	private By goButton = By.xpath("(//*[@class='button secondary-button primary-button' or @id='find-new-location-input'])[1]");
	private By storeLocatorSearchArea = By.xpath("//input[@placeholder='City, State or ZIP' or @id='find-new-location-input']");
	
	//results elements:
	private By resultsPage = By.xpath("(//*[span>0 or text()='Directions'])[1]");
	private String distanceText	 = "(//*[@class='distance'] | //*[@class='store-list-item'])";
	private String addressText = "(//div[starts-with(@class,'store-finder-info-modal') or @class='location-contact']/div[@class='address'])";
	
	
	/**********************************************************************
	 * 		Constructor
	 * @throws Exception 
	 **********************************************************************/
	public SearchStoresPageView(RemoteWebDriver driver) throws Exception{
        super(driver);
        
        //wait for page to load:
        try {
        	fluentWait(pageText, 20);
        	Thread.sleep(4000);
		} catch (Exception e) {
			String errorFile = PerfectoUtils.takeScreenshot(driver);
			Reporter.log("Error screenshot saved in file: " + errorFile);
			throw e;
		}
       
	}
	
	
	/**********************************************************************
	 * 	searchAddress	
	 * 		This method searches a given address in the stores page.
	 * 		
	 * 		@param address	the address to search for
	 * 		@return	new instance of the view to the searched stores page
	 * @throws IOException 
	 **********************************************************************/
	public SearchStoresPageView searchAddress(String address) throws IOException{
		
		//search address 
		try {
			
			sendKeys(storeLocatorSearchArea,address);
			click(goButton);
			pressKey(Keys.ENTER);
			
			try {
				fluentWait(resultsPage, 20);
			} catch (Exception e) {
				System.out.println("No stores were found");
				throw e;
			}
			
			return this;
		} catch (Exception e) {
			//System.out.println(e.toString());
			
			PerfectoUtils.getScreenshotOnError(driver);
			throw e;
			//throw new IllegalStateException();
		}
					
	}
	
	
	/**********************************************************************
	 * 	getDistanceByStoreIndex	
	 * 		This method gets the distance of store#index in the results page.
	 * 		
	 * 		@param storeIndex	the index of the store
	 * 		@return	the distance of the store 
	 **********************************************************************/
	public String getDistanceByStoreIndex(int storeIndex){
		
		String distance = null;
		
		//build xpath:

		By xpath = By.xpath(distanceText + "["+ storeIndex + "]");
		
		try {
			//get the store element:
			WebElement element = find(xpath);
			
			//find the stores distance
			distance = element.getText();
			
			if (distance.contains("\n")){
				String lines[] = distance.split("\\r?\\n");
				distance= lines[2];
			}
			return distance;
			
		} catch (Exception e) {
			System.out.println("Store#" + storeIndex + " wasn't found");
			return null;
		}
		
	}
	
	/**********************************************************************
	 * 	getAddressByStoreIndex	
	 * 		This method gets the address of store#index in the results page.
	 * 		
	 * 		@param storeIndex	the index of the store
	 * 		@return	the address of the store 
	 **********************************************************************/
	public String getAddressByStoreIndex(int storeIndex){
		
		String address = null;
		
		//build xpath:
		By xpath = By.xpath(addressText + "["+ storeIndex + "]");
		
		try {
			//get the store element:
			WebElement element = find(xpath);
			
			//get the address
			address = element.getText();
			
			if (address.isEmpty()){
				address = element.getAttribute("innerHTML");
				
			}
			//remove commas to align address between mobile and web
			address = address.replace(",","");
			
			return address.trim();
			
		} catch (Exception e) {
			System.out.println("Store#" + storeIndex + " wasn't found");
			return null;
		}	
	}
}
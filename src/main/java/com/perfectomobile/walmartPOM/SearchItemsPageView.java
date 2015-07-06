package com.perfectomobile.walmartPOM;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;

import com.perfectomobile.utils.*;

public class SearchItemsPageView extends WalmartBaseView {
	
	// Page elements
	private String itemPrice = "(//*[@class='price' or @class='price price-display' or @class='item-price'])";
	private String itemName	= "(//*[@class='name' or @class='js-product-title'] | //div[@class='item-page-header ']/h2)";
	private By sortButton = By.xpath("//*[contains(text(),'Sort')]");
		
	/**********************************************************************
	 * 		Constructor
	 * @throws IOException 
	 **********************************************************************/
	public SearchItemsPageView(RemoteWebDriver driver) throws IOException{
        super(driver);
        
        //validate page loaded successfully before proceeding
        try{
        	fluentWait(sortButton, 30);
        }
        catch(Exception e){
        	String errorFile = PerfectoUtils.takeScreenshot(driver);
			Reporter.log("Error screenshot saved in file: " + errorFile);
        	throw new IllegalStateException();
        }
    }
	
	/**********************************************************************
	 * 	getItemNameByIndex	
	 * 		This method gets an index of item in the item results page 
	 * 		and returns the name of the item.	  	
	 * 		
	 * 		@param itemNumber the index of the item on the page
	 * 		@return	the name of item 
	 **********************************************************************/
	public String getItemNameByIndex(int itemNumber) {
		
		//build xpath:
		By xpath = By.xpath(itemName + "["+ itemNumber + "]");
		
		//find item numbr "index" on the page
		try {
			return getText(xpath);
		} catch (Exception e) {
			
			return "item#" + itemNumber + "was not found";
		}
		
	}
	
	/**********************************************************************
	 * 	getItemPriceByIndex	
	 * 		This method gets an index of item in the item results page 
	 * 		and returns the price of the item.	  	
	 * 		
	 * 		@param itemNumber the index of the item on the page
	 * 		@return	the price of item 
	 **********************************************************************/
	public String getItemPriceByIndex(int itemNumber){
		
		//build xpath
        By xpath = By.xpath(itemPrice + "["+ itemNumber + "]");
        
        try {
        	 //find the price of the item
        	 String price = getText(xpath).replace("\n", "");
             return price;
             
		} catch (Exception e) {
			
			return "item#" + itemNumber + "was not found";
		}
       
  }

}
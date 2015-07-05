package com.perfectomobile.yelp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.utils.BaseObject;

public class FilterPage extends BaseObject {
	
		
		private By bestMatchRBLocator = By.xpath("//radiobutton[text()='Best Match']");
		private By distanceRBLocator = By.xpath("//radiobutton[text()='Distance']");
		private By ratingRBLocator = By.xpath("//radiobutton[text()='Rating']");
		private By twoblocksRBLocator = By.xpath("//radiobutton[text()='2 blocks']");
		private By sixblocksRBLocator = By.xpath("//radiobutton[text()='6 blocks']");
		private By onemileRBLocator = By.xpath("//radiobutton[text()='1 mile']");
		private By fivemilesRBLocator = By.xpath("//radiobutton[text()='5 miles']");
		private By $TBLocator = By.xpath("//switch[@resourceid='com.yelp.android:id/dlg_filters_btnPrice1']");
		private By $$TBLocator = By.xpath("//switch[@resourceid='com.yelp.android:id/dlg_filters_btnPrice2']");
		private By $$$TBLocator = By.xpath("//switch[@resourceid='com.yelp.android:id/dlg_filters_btnPrice3']");
		private By $$$$TBLocator = By.xpath("//switch[@resourceid='com.yelp.android:id/dlg_filters_btnPrice4']");
		private By openNowTBLocator = By.xpath("//switch[text()='Open Now']");
		private By cancelBTLocator = By.xpath("//button[text()='Cancel']");
		private By okBTLocator = By.xpath("//button[text()='OK']");
		
		private By loginValidateByOCR = By.linkText("Search");
		
		public FilterPage(RemoteWebDriver driver) {
			super(driver);
		}

		
		public void sortRadioButton(String button){
			
			switch (button) {
	            case "Best Match": 	click(bestMatchRBLocator);
	            					break;
	            case "Distance":	click(distanceRBLocator);
                     				break;
	            case "Rating":  	click(ratingRBLocator);
	            					break;
			}
            	
			
		}
		
		public void distanceRadioButton(String button){
			
			switch (button) {
	            case "2 blocks": 	click(twoblocksRBLocator);
	            					break;
	            case "6 blocks":	click(sixblocksRBLocator);
                     				break;
	            case "1 mile":  	click(onemileRBLocator);
	            					break;
	            case "5 mile":  	click(fivemilesRBLocator);
									break;
	            					
			}
		}
				
	public void $buttonPrice(Boolean turnOn){
			if (!isChecked ($TBLocator)) 
	        		click($TBLocator);
			}
	public void $$buttonPrice(Boolean turnOn){		
	        if	(!isChecked ($$TBLocator)) 
	        		click($$TBLocator);
	        }
	public void $$$buttonPrice(Boolean turnOn){        
		    if	(!isChecked ($$$TBLocator)) 
				    click($$$TBLocator); 
			}
	public void $$$$buttonPrice(Boolean turnOn){	  
		if		(!isChecked ($$$$TBLocator)) 
					click($$$$TBLocator);   	  
		}
		
		
		
		
		public void openNowSwitch(Boolean turnOn) {
			
			if (!isChecked(openNowTBLocator))
				click(openNowTBLocator);
		}
		

		public void  cancel(){
			click(cancelBTLocator);
		
		}
		public void  ok(){
			click(okBTLocator);
			
		}
		

		public Boolean validatevalidateFirstPage() {

			return waitForIsDisplayed(loginValidateByOCR, 40);

		}
	}

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

		
//		sets best match filter
		public BaseObject  bestMatch(){
			click(bestMatchRBLocator);
			return new BaseObject(getDriver());
		}
//		sets Distance filter
		public BaseObject  distance(){
			click(distanceRBLocator);
			return new BaseObject(getDriver());
		}
//		sets rating filter
		public BaseObject  rating(){
			click(ratingRBLocator);
			return new BaseObject(getDriver());
		}
//		sets Distance twoblocks filter
		public BaseObject  twoblocks(){
			click(twoblocksRBLocator);
			return new BaseObject(getDriver());
		}
//		sets Distance sixblocks filter
		public BaseObject  sixblocks(){
			click(sixblocksRBLocator);
			return new BaseObject(getDriver());
		}
//		sets onemile filter
		public BaseObject  onemile(){
			click(onemileRBLocator);
			return new BaseObject(getDriver());
		}
//		sets fivemiles filter
		public BaseObject  fivemiles(){
			click(fivemilesRBLocator);
			return new BaseObject(getDriver());
		}
//		sets onemile filter
		public BaseObject  $TB(){
			click($TBLocator);
			return new BaseObject(getDriver());
		}
		public BaseObject  $$TB(){
			click($$TBLocator);
			return new BaseObject(getDriver());
		}
		public BaseObject  $$$TB(){
			click($$$TBLocator);
			return new BaseObject(getDriver());
		}
		public BaseObject  $$$$TB(){
			click($$$$TBLocator);
			return new BaseObject(getDriver());
		}
		public BaseObject  openNow(){
			click(openNowTBLocator);
			return new BaseObject(getDriver());
		}
		public BaseObject  cancel(){
			click(cancelBTLocator);
			return new NearByPage(driver);
		}
		public BaseObject  ok(){
			click(okBTLocator);
			return new NearByPage(driver);
		}
		

		public Boolean validatevalidateFirstPage() {

			return waitForIsDisplayed(loginValidateByOCR, 40);

		}
	}

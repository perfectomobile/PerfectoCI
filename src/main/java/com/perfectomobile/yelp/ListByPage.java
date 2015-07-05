package com.perfectomobile.yelp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.utils.BaseObject;

public class ListByPage extends BaseObject {
	
		private By filterBTLocator = By.xpath("//button[text()='Filter']");
		private By mapBTLocator = By.xpath("//button[text()='Map']");
		private By searchTFLocator = By.xpath("//textfield[@resourceid='com.yelp.android:id/searchbar']");
	
		
		private By loginValidateByOCR = By.linkText("Map");
		
		public ListByPage(RemoteWebDriver driver) {
			super(driver);
		}


		
//		clicks on filter
		public BaseObject  filter(){
			click(filterBTLocator);
			return new FilterPage(getDriver());
		}
//		opens map	
		public BaseObject  map(){
			click(mapBTLocator);
			return new BaseObject(getDriver());
		}

		

		public Boolean validatevalidateFirstPage() {

			return waitForIsDisplayed(loginValidateByOCR, 40);

		}
	}

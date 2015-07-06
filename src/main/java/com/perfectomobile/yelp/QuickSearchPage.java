package com.perfectomobile.yelp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.utils.BaseObject;

public class QuickSearchPage extends YelpBaseView {
	
		
		//private By searchTFLocator = By.xpath("//textfield[@resourceid='com.yelp.android:id/searchbar']");
		private By searchBTLocator = By.xpath("//button[text()='Search']");
		private By nearLocator = By.xpath("//textfield[text()='Current Location']");
		private By findLocator = By.xpath("//*[@resourceid='com.yelp.android:id/searchbar']");
		
		private By loginValidateByOCR = By.linkText("Find");
		
		public QuickSearchPage(RemoteWebDriver driver) {
			super(driver);
		}

		
//		new search using the textfield
		public BaseObject search(String Near, String Find) {
			//click(searchTFLocator);
			type (Near, nearLocator);
			type(Find, findLocator);
			click(searchBTLocator);
			return new ListByPage(driver);
		}

		
		

		public Boolean validatevalidateFirstPage() {

			return waitForIsDisplayed(loginValidateByOCR, 40);

		}
	}

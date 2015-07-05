package com.perfectomobile.yelp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.utils.BaseObject;

public class MapPage extends BaseObject {
	
		private By filterBTLocator = By.xpath("//button[text()='Filter']");
		private By ListBTLocator = By.xpath("//button[text()='List']");
		
		private By loginValidateByOCR = By.linkText("List");
		
		public MapPage(RemoteWebDriver driver) {
			super(driver);
		}

		

//		clicks on filter
		public BaseObject  filter(){
			click(filterBTLocator);
			return new BaseObject(getDriver());
		}
//		opens map	
		public BaseObject  list(){
			click(ListBTLocator);
			return new ListByPage(driver);
		}

		
		
		

		public Boolean validatevalidateFirstPage() {

			return waitForIsDisplayed(loginValidateByOCR, 40);

		}
	}

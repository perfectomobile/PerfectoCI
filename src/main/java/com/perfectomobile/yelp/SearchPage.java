package com.perfectomobile.yelp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.utils.BaseObject;

public class SearchPage extends BaseObject {
	
		private By filterBTLocator = By.xpath("//button[text()='Filter']");
		private By mapBTLocator = By.xpath("//button[text()='Map']");
//		private By BTLocator = By.xpath("//button[@class='android.widget.ImageButton']");
		
		private By loginValidateByOCR = By.linkText("Map");
		
		public SearchPage(RemoteWebDriver driver) {
			super(driver);
		}

		

//		clicks on filter
		public BaseObject  filter(){
			click(filterBTLocator);
			return new BaseObject(getDriver());
		}
//		opens map	
		public BaseObject  map(){
			click(mapBTLocator);
			return new BaseObject(getDriver());
		}
//		opens a small bottom menu
//		public BaseObject  bt(){
//			click(BTLocator);
//			return new BaseObject(getDriver());
//		}

		public BaseObject clickMenuItem(String menuText){
			
			By locator = By.xpath("//list/group/text[text()='" + menuText + "']");
			click(locator);
			return new ListByPage(driver);
		
		}
		
		

		public Boolean validatevalidateFirstPage() {

			return waitForIsDisplayed(loginValidateByOCR, 40);

		}
	}

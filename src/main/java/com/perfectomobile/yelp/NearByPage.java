package com.perfectomobile.yelp;

import java.awt.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.utils.BaseObject;
import com.thoughtworks.selenium.webdriven.commands.GetElementIndex;

public class NearByPage extends YelpBaseView {
	
		
		private By loginValidateByOCR = By.linkText("Nearby");
		private By moreCategories = By.linkText("more Categories");
		
		public NearByPage(RemoteWebDriver driver) {
			super(driver);
		}

		
		//insert a list select mechanisim manager
		public BaseObject clickMenuItem(String menuText){
			
			By locator = By.xpath("//list/group/text[text()='" + menuText + "']");
			click(locator);
			return new ListByPage(driver);
		
		}

		public Boolean validatevalidateFirstPage() {

			return waitForIsDisplayed(loginValidateByOCR, 40);

		}
	}

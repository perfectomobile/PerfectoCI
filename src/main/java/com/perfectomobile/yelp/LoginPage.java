package com.perfectomobile.yelp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.utils.BaseObject;

public class LoginPage extends BaseObject {
	
		
		private By NoBTLocator = By.xpath("//button[text()='No']");
		private By YesBTLocator = By.xpath("//button[text()='Yes, Log In']");
		private By BTLocator = By.xpath("//button[@class='android.widget.ImageButton']");
		
		
		private By loginValidateByOCR = By.linkText("yelp.com");
		
		public LoginPage(RemoteWebDriver driver) {
			super(driver);
		}

		
//		
		public BaseObject  noBT(){
			click(NoBTLocator);
			return new SignUpPage(driver);
		}
//		opens the checkins page		
		public BaseObject  yesBT(){
			click(YesBTLocator);
			return new BaseObject(getDriver());
		}
//		opens the aboutme page
		public BaseObject  bt(){
			click(BTLocator);
			return new MainPage(driver);
		}
		
		

		public Boolean validatevalidateFirstPage() {

			return waitForIsDisplayed(loginValidateByOCR, 40);

		}
	}

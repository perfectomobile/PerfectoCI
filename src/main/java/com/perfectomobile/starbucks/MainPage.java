package com.perfectomobile.starbucks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.utils.BaseObject;

public class MainPage extends BaseObject {
	
		

	private By settingsBTLocator = By.xpath("//button[text()='Account & Settings']");

	// different page - after login Whats new page

	public MainPage(RemoteWebDriver driver) {
		super(driver);
	}

	public Settings goToSettingPage() {

		click(settingsBTLocator);
		Settings settings = new Settings(super.getDriver());
		
		return settings;
		//waitForIsDisplayed(loginValidateByOCR, 40);
		
	}
	
	
	public Boolean validatevalidateFirstPage() {

		//return waitForIsDisplayed(loginValidateByOCR, 40);
		return false;
	}


}

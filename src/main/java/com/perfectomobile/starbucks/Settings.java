package com.perfectomobile.starbucks;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.utils.BaseObject;

public class Settings extends BaseObject {
	
	private By selectSignoutLocator = By.xpath("//button[text()='SIGN OUT']");
	private By signoutLocator = By.xpath("//button[text()='Sign Out']");
	private By backLocator = By.xpath("//button[text()='Back']");

	private By SettingsValidate = By.xpath("//*[@label='SETTINGS']");

	public Settings(RemoteWebDriver driver) {
		super(driver);
	}

	
	public Login signout() {
		
		swipeAndSearch(selectSignoutLocator, "50%,70%", "50%,10%", 5);
		click(selectSignoutLocator);
		click(signoutLocator);
		click(backLocator);
		return new Login(getDriver());
		
	}

	

}

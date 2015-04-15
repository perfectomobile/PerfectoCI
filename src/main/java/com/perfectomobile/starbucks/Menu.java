package com.perfectomobile.starbucks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.utils.BaseObject;

public class Menu extends BaseObject {
	
	private By payLocator = By.xpath("//button[text()='PAY']");
	private By storeLocator = By.xpath("//button[text()='STORES']");
	private By giftLocator = By.xpath("//button[text()='GIFT']");
	private By menuLocator = By.xpath("//button[text()='MANU']");

	// different page - after login Whats new page

	private By loginValidateByOCR = By.linkText("PAY");

	public Menu(RemoteWebDriver driver) {
		super(driver);
	}

	
	public BaseObject pay() {
		
		click(payLocator);
		return new BaseObject(getDriver());
		
	}

	public BaseObject store() {
		
		click(storeLocator);
		return new BaseObject(getDriver());
	}

	

	public Boolean validatevalidateFirstPage() {

		return waitForIsDisplayed(loginValidateByOCR, 40);

	}
}

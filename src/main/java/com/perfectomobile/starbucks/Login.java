package com.perfectomobile.starbucks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.perfectomobile.utils.BaseObject;

public class Login extends BaseObject {
	
	private By signInVBT = By.xpath("//*[text()='SIGN IN']");
	private By userIdLocator = By.xpath("//textfield[contains(text(),'Username')]");
	private By passwordLocator = By.xpath("(//secure | //textfield[contains(text(),'password')])");
	private By loginSubmitLocator = By.xpath("//button[text()='SIGN IN']");

	// different page - after login Whats new page

	private By loginValidateByOCR = By.linkText("PAY");

	public Login(RemoteWebDriver driver) {
		super(driver);
	}

	public MainPage with(String username, String password, String accountName) {

		MainPage mainPage = null;
		
		click(signInVBT);
		type(username, userIdLocator);
		type(password, passwordLocator);
		click(loginSubmitLocator);
		
		waitForIsDisplayed(loginValidateByOCR, 40);

		Assert.assertTrue(validateUserNameOCR(accountName), "Couldnt find username");
		mainPage =  new MainPage(super.getDriver());
		
		return mainPage;

	}
	
	
	public Boolean validateUserNameOCR(String username) {
		
		switchToVisual();
		
		try {
			find(By.linkText(username));
		
		} catch (ElementNotFoundException e){
			
			return false;
			
		} finally {
		
			switchToNative();
		}

		return true;
		
	}

	public Boolean validatevalidateFirstPage() {

		return waitForIsDisplayed(loginValidateByOCR, 40);
		
		

	}
}

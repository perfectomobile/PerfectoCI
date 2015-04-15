package com.perfectomobile.P2E;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.perfectomobile.utils.BaseObject;

public class Login extends BaseObject {
	private By userIdLocator = By.id("username");
	private By passwordLocator = By.id("password");
	private By loginSubmitLocator = By.id("loginSubmit");

	// different page - after login Whats new page

	private By mainManunavigator = By.xpath("//*[@class='k-input']");

	public Login(WebDriver driver) {
		super(driver);
		getDriver().get("https://p2afetest.p2energysolutions.com/P2AFE");
		Assert.assertTrue(isDisplayed(loginSubmitLocator),
				"The login form is not presented");
	}

	public void with(String username, String password) {

		type(username, userIdLocator);
		type(password, passwordLocator);
		submit(loginSubmitLocator);

		waitForIsDisplayed(mainManunavigator, 40);

	}

}

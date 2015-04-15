package com.perfectomobile.starbucks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.perfectomobile.utils.BaseObject;

public class Login extends BaseObject {
	private By signInVBT = By.xpath("//*[text()='SIGN IN']");
	private By userIdLocator = By
			.xpath("//textfield[contains(text(),'Username')]");
	private By passwordLocator = By
			.xpath("(//secure | //textfield[contains(text(),'password')])");
	private By loginSubmitLocator = By.xpath("//button[text()='SIGN IN']");

	// different page - after login Whats new page

	private By loginValidateByOCR = By.linkText("PAY");

	public Login(WebDriver driver) {
		super(driver);
	}

	public void with(String username, String password) {

		click(signInVBT);
		type(username, userIdLocator);
		type(password, passwordLocator);
		submit(loginSubmitLocator);

		// switchToContext(_driver, "NATIVE_APP");
		// switchToContext(_driver, "VISUAL");
		// waitForIsDisplayed(loginValidateByOCR, 40);

	}

	public Boolean validatevalidateFirstPage() {

		return waitForIsDisplayed(loginValidateByOCR, 40);

	}
}

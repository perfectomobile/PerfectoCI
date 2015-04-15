package com.perfectomobile.PayPal;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.perfectomobile.utils.BaseObject;

public class LoginPageLinks extends BaseObject {
	private By loginValidatePage = By.id("besthelp_popular_header_inner");

	public LoginPageLinks(RemoteWebDriver driver) {
		super(driver);
		getDriver().get(
				"https://www.stage2d0022.stage.paypal.com/selfhelp/help/home");
		Assert.assertTrue(isDisplayed(loginValidatePage),
				"The login form is not presented");

	}

	public List<WebElement> getCommonQestions() {

		List<WebElement> commonQ = getDriver().findElements(
				By.xpath(".//*[@id='besthelp_popular_body']/A"));
		return commonQ;
	}
}

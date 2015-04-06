package P2E;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import utils.BaseObject;


public class login extends BaseObject 
{

	RemoteWebDriver _driver;
	By userIdLocator = By.id("username");
	By passwordLocator = By.id("password");
	By loginSubmitLocator = By.id("loginSubmit");

	// different page - after login Whats new page
 
	By mainManunavigator = By.xpath("//*[@class='k-input']") ;
 


	public login(WebDriver driver) {
		super(driver);
		_driver = (RemoteWebDriver) driver;
		_driver.get("https://p2afetest.p2energysolutions.com/P2AFE");
		Assert.assertTrue(isDisplayed(loginSubmitLocator) , "The login form is not presented");

	}


	public void with(String username, String password) {

		type(username, userIdLocator);
		type(password, passwordLocator);
		submit(loginSubmitLocator);

		waitForIsDisplayed(mainManunavigator, 40);

	 


	}

}



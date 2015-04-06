package starbucks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import utils.BaseObject;


public class login extends BaseObject 
{
	 
	RemoteWebDriver _driver;
	By signInVBT = By.xpath("//*[text()='SIGN IN']");
	By userIdLocator = By.xpath("//textfield[contains(text(),'Username')]");
	By passwordLocator =By.xpath("(//secure | //textfield[contains(text(),'password')])");
	By loginSubmitLocator = By.xpath("//button[text()='SIGN IN']");

	// different page - after login Whats new page
 
	By loginValidateByOCR = By.linkText("PAY") ;
 


	public login(WebDriver driver) {
		super(driver);
		
	}


	public void with(String username, String password) {

		click(signInVBT);
		type(username, userIdLocator);
		type(password, passwordLocator);
		submit(loginSubmitLocator);

		
		//switchToContext(_driver, "NATIVE_APP");
		// switchToContext(_driver, "VISUAL");
		// waitForIsDisplayed(loginValidateByOCR, 40);

	  
	}

	public Boolean validatevalidateFirstPage() {
 
		  return waitForIsDisplayed(loginValidateByOCR, 40);

	}
}



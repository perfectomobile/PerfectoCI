package PayPal;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.server.handler.WebElementHandler;
import org.testng.Assert;

import utils.BaseObject;


public class loginPageLinks extends BaseObject 
{
	 
	RemoteWebDriver _driver;
	 
 
	By loginValidatePage = By.id("besthelp_popular_header_inner") ;
 


	public loginPageLinks(WebDriver driver) {
		super(driver);
 		_driver = (RemoteWebDriver) driver;
		_driver.get("https://www.stage2d0022.stage.paypal.com/selfhelp/help/home");
		Assert.assertTrue(isDisplayed(loginValidatePage) , "The login form is not presented");
		
	}

 
	public List<WebElement>  getCommonQestions() {
 
		List<WebElement> commonQ =  _driver.findElements(By.xpath(".//*[@id='besthelp_popular_body']/A"));
		return commonQ;
	}
}



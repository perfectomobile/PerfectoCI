package P2E;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import utils.BaseObject;


public class settingMenu extends BaseObject 
{

	RemoteWebDriver _driver;
	By menuButton = By.xpath(".//*[@src='/P2AFE/Content/Images/Settings.png']");
	By logoutOpt = By.xpath("//li[@id='logOut']/a");
	By aboutOPT = By.id("aboutItem");

	public settingMenu(WebDriver driver) {
		super(driver);
		_driver = (RemoteWebDriver) driver;
	}


	public void logout() {
		click(menuButton);
		click(logoutOpt);
  
	}
	public void about() {
		click(menuButton);
		click(aboutOPT);
  
	}

}



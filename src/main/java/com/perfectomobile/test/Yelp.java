package com.perfectomobile.test;
import java.lang.reflect.Method;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.perfectomobile.yelp.MainPage;
import com.perfectomobile.utils.BaseObject;
import com.perfectomobile.utils.PerfectoUtils;
import com.perfectomobile.yelp.FilterPage;
import com.perfectomobile.yelp.ListByPage;
import com.perfectomobile.yelp.LoginPage;
import com.perfectomobile.yelp.MapPage;
import com.perfectomobile.yelp.QuickSearchPage;
import com.perfectomobile.yelp.SearchPage;
import com.perfectomobile.yelp.SignUpPage;

public class Yelp extends BasicTest {

	@Test(dataProvider = "Capabilities")
	public void search(DesiredCapabilities caps, Method method) {

		RemoteWebDriver driver = null;
		
		try {
			driver = beforeTest(caps);
			
			
			//MainPage mainpage= new MainPage(driver);
			//if first time or validation on yelp.com
			//login-press noBT
			//signUp press noBT
			//else not first time mainpage goto quick search
			LoginPage login = new LoginPage(driver);
			login.noBT();
			SignUpPage signUp = new SignUpPage(driver);
			signUp.noBT();
			MainPage mp = new MainPage(driver);
			mp.searchTF();
			QuickSearchPage quickSearch = new QuickSearchPage(driver);
			quickSearch.search("Burlington, MA", "Nordstrom");
			ListByPage listBy = new ListByPage(driver);
			listBy.map();
			PerfectoUtils.getScreenShot(driver, "Nordstrom Burlington,MA map");
			MapPage map = new MapPage(driver);
			map.list();
			FilterPage filter =new FilterPage(driver);
			filter.$$$TB();
			SearchPage search = new SearchPage(driver);
			search.clickMenuItem("75 Middlesex Tpke");
			By parkingLocator= By.xpath("//text[text()='Parking:']");
			search.swipeAndSearch(parkingLocator, "50%,20%","50%,70%",5);
			WebElement parking = driver.findElementByXPath("//text[text()='Parking:']");
			WebElement textElement = parking.findElement(By.xpath("//.row/text[2]"));
			 Assert.assertEquals("Text found!", textElement);
			

			
		} catch (Exception e) {
			System.out.println("Yelp ended with Error");

		} finally {
			endTest(driver);
		}

	}
	

	@Override
	public RemoteWebDriver beforeTest(DesiredCapabilities caps) throws Exception {
		System.out.println("Yelp description: " + caps.getCapability("description"));
		// add the Perfecto native tree capability
		caps.setCapability("automationName", "PerfectoMobile");
		
		RemoteWebDriver driver = PerfectoUtils.getDriver(caps);
		// open app
		switchToContext(driver, "NATIVE_APP");
		
		Object Platform =driver.getCapabilities().getCapability("platformName");
		if (Platform.equals("Android")){
			PerfectoUtils.installApp("http://nxc.co.il/rwd/Yelp_2.5.0.apk" , driver);
		}
		PerfectoUtils.startApp("Yelp", driver);
		return driver;

	}

	@Override
	public void endTest(RemoteWebDriver driver) {
		PerfectoUtils.uninstallApp("Yelp", driver);
		PerfectoUtils.closeTest(driver);
	}

}

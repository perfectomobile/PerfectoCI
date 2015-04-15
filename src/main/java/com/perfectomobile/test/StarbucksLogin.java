package com.perfectomobile.test;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import com.perfectomobile.starbucks.Login;
import com.perfectomobile.utils.PerfectoUtils;

public class StarbucksLogin extends BasicTest {

	// Test (dataProvider="Capabilities",groups = { "STARBUCKS", "P1", "ALL" })
	@Test(dataProvider = "Capabilities")
	public void login(DesiredCapabilities caps, Method method) {

		System.out.println("START WITH CAP " + caps.toString());
		RemoteWebDriver driver = null;
		
		try {
			driver = beforeTest(caps);
			
			
			Login login = new Login(driver);
			login.with("uzi.eilon@gmail.com", "Perfecto1");
			// PerfectoUtils.getScreenShot(_driver,
			// _caps.getCapability("description").toString()+"_"+_method.getName());
			// Change to OCR
			switchToContext(driver, "VISUAL");
			login.validatevalidateFirstPage();
			switchToContext(driver, "NATIVE_APP");
			
		} catch (Exception e) {
			System.out.println("StarbucksLogin ended with Error");

		} finally {
			endTest(driver);
		}

	}
	
	
	// Test (dataProvider="Capabilities",groups = { "STARBUCKS", "P1", "ALL" })
	@Test(dataProvider = "Capabilities")
	public void login2(DesiredCapabilities caps, Method method) {

		System.out.println("START WITH CAP " + caps.toString());
		RemoteWebDriver driver = null;
		
		try {
			driver = beforeTest(caps);
			
			
			Login login = new Login(driver);
			login.with("uzi.eilon@gmail.com", "Perfecto1");
			// PerfectoUtils.getScreenShot(_driver,
			// _caps.getCapability("description").toString()+"_"+_method.getName());
			// Change to OCR
			switchToContext(driver, "VISUAL");
			login.validatevalidateFirstPage();
			switchToContext(driver, "NATIVE_APP");
			
		} catch (Exception e) {
			System.out.println("StarbucksLogin ended with Error");

		} finally {
			endTest(driver);
		}

	}

	

	@Override
	public RemoteWebDriver beforeTest(DesiredCapabilities caps) throws Exception {
		System.out.println("StarbucksLogin" + caps.getCapability("description"));
		// add the Perfecto native tree capability
		caps.setCapability("automationName", "PerfectoMobile");
		RemoteWebDriver driver = PerfectoUtils.getDriver(caps);
		// open app
		switchToContext(driver, "NATIVE_APP");
		PerfectoUtils.startApp("Starbucks", driver);
		
		return driver;

	}

	@Override
	public void endTest(RemoteWebDriver driver) {
		PerfectoUtils.closeTest(driver);
	}

}

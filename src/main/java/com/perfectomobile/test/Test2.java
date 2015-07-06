package com.perfectomobile.test;

import java.lang.reflect.Method;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.perfectomobile.utils.PerfectoUtils;

public class Test2 extends BasicTest{

 	@Test (dataProvider="Capabilities" )	
	public void f2_1(DesiredCapabilities caps, Method method) {
 		System.out.println("Test2 in Test " + caps.toString());
		RemoteWebDriver driver = null;
		
		try {
			driver = beforeTest(caps);
			
			System.out.println("Test2" + caps.getCapability("description"));
			// open app
			
		} catch (Exception e) {
			System.out.println("Test2 in Test ended with Error");

		} finally {
			endTest(driver);
		}

 	}
 	
	@Test (dataProvider="Capabilities" )	
	public void f2_2(DesiredCapabilities caps, Method method) {
		
 		System.out.println("Test2 in Test " + caps.toString());
		RemoteWebDriver driver = null;
		
		try {
			driver = beforeTest(caps);
			
			System.out.println("Test2" + caps.getCapability("description"));
			
			// open app
			
			
			
		} catch (Exception e) {
			System.out.println("Test2 in Test ended with Error");

		} finally {
			endTest(driver);
		}

 	}
	
	
	@Override
	public RemoteWebDriver beforeTest(DesiredCapabilities caps) throws Exception {
		System.out.println("Test2" + caps.getCapability("description"));
		// add the Perfecto native tree capability
		caps.setCapability("automationName", "PerfectoMobile");
		RemoteWebDriver driver = PerfectoUtils.getDriver(caps, 10, 20);
		// open app
		
		return driver;

	}
	

	@Override
	public void endTest(RemoteWebDriver driver) {
		PerfectoUtils.closeTest(driver);
	}

 
}

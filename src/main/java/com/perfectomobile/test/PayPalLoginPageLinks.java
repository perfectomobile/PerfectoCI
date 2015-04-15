package com.perfectomobile.test;

import java.lang.reflect.Method;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import com.perfectomobile.PayPal.*;
import com.perfectomobile.utils.PerfectoUtils;

public abstract class PayPalLoginPageLinks extends BasicTest{

 	private Method method;
 	
	@Test (dataProvider="Capabilities",groups = { "PAYPAL", "P1", "ALL" })  
	public void login(DesiredCapabilities caps, Method method) {
 		this.method = method;
		
 		RemoteWebDriver driver = null;
		try {
			driver = beforeTest(caps);
			
			// execTest
			LoginPageLinks lp = new LoginPageLinks(driver);
			List<WebElement> cq = lp.getCommonQestions();
			// loop over all the questions
			for (WebElement question : cq) {
				System.out.println(question.getText());
			}
			
			 
		} catch (Exception e) {
			System.out.println("PayPal ended with Error");

		}finally
		{
			 endTest(driver);
		}
		
	}

	@Override
	public RemoteWebDriver beforeTest(DesiredCapabilities caps) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("PayPalLoginPageLinks" + caps.getCapability("description"));
		// add the Perfecto native tree capability
		caps.setCapability("automationName", "PerfectoMobile");
		RemoteWebDriver driver = PerfectoUtils.getDriver(caps);
		// open app
		
		return driver;
		
	}



	@Override
	public void endTest(RemoteWebDriver driver)  {
		PerfectoUtils.closeTest(driver);		
	}
	 

 
}

package test;

import java.lang.reflect.Method;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import utils.PerfectoUtils;

public class StarbucksLogin extends basicTest{
 
	 // Test (dataProvider="Capabilities",groups = { "STARBUCKS", "P1", "ALL" })
	@Test (dataProvider="Capabilities" )	
	public void login(DesiredCapabilities caps, Method method) {
	
		System.out.println("START WITH CAP "+  caps.toString());
  
		try {
			beforeTest(caps);
			execTest();
		} catch (Exception e) {
			System.out.println("StarbucksLogin ended with Error");

		}finally
		{
			endTest();
		}


	}
	

	@Override
	public void beforeTest(DesiredCapabilities caps) throws Exception {
		 System.out.println("StarbucksLogin"+caps.getCapability("description"));
			// add the Perfecto native tree capability   
	 		caps.setCapability("automationName", "PerfectoMobile");
			setUpDriver(PerfectoUtils.getDriver(caps));
			// open app
			switchToContext(_driver, "NATIVE_APP");
			PerfectoUtils.startApp("Starbucks",_driver);
		
	}


	 

	@Override
	public void execTest() throws Exception {


		try {
			starbucks.login login = new starbucks.login(_driver);
 
			login.with("uzi.eilon@gmail.com", "Perfecto1");
		// 	PerfectoUtils.getScreenShot(_driver, _caps.getCapability("description").toString()+"_"+_method.getName());
			//Change to OCR
			switchToContext(_driver, "VISUAL");
			login.validatevalidateFirstPage();
 			switchToContext(_driver, "NATIVE_APP");


		} catch (Exception e) {
		 //	PerfectoUtils.getScreenShot(_driver, _caps.getCapability("description").toString()+"_"+_method.getName());

		}finally
		{
			endTest();
		}

		endTest();

	}

	@Override
	public void endTest()  {
		PerfectoUtils.closeTest(_driver);		
	}

	 



}

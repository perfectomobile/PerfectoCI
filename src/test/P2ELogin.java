package test;

import java.lang.reflect.Method;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import utils.PerfectoUtils;

public abstract class P2ELogin extends basicTest{

	DesiredCapabilities _caps;
	Method _method;
	@Test (dataProvider="Capabilities",groups = { "P2E", "P1", "ALL" })  
	public void login(DesiredCapabilities caps, Method method) {
		_caps=caps;
		_method = method;
		
		try {
			 beforeTest(_caps);
			 execTest();
		} catch (Exception e) {
			System.out.println("P2ELogin ended with Error");

		}finally
		{
			 endTest();
		}
		
	}

	@Override
	public void beforeTest(DesiredCapabilities caps) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execTest() throws Exception {
		System.out.println("P2e"+_caps.getCapability("description"));
		setUpDriver(PerfectoUtils.getDriver(_caps));

		try {
			P2E.login login = new P2E.login(_driver);
			login.with("bcl1127", "test");
			PerfectoUtils.getScreenShot(_driver, _caps.getCapability("description").toString()+"_"+_method.getName());
			
			P2E.settingMenu setting = new P2E.settingMenu(_driver);
			setting.logout();
			
		} catch (Exception e) {
			PerfectoUtils.getScreenShot(_driver, _caps.getCapability("description").toString()+"_"+_method.getName());
			
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

package test;

import java.lang.reflect.Method;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import utils.PerfectoUtils;

public class P2ELogin extends basicTest{

	@Test (dataProvider="Capabilities",groups = { "P2E", "P1", "ALL" })  

	public void login(DesiredCapabilities caps, Method method) {
		System.out.println("P2e"+caps.getCapability("description"));
		setUpDriver(PerfectoUtils.getDriver(caps));

		try {
			P2E.login login = new P2E.login(_driver);
			login.with("bcl1127", "test");
			PerfectoUtils.getScreenShot(_driver, caps.getCapability("description").toString()+"_"+method.getName());
			
			P2E.settingMenu setting = new P2E.settingMenu(_driver);
			setting.logout();
			
		} catch (Exception e) {
			PerfectoUtils.getScreenShot(_driver, caps.getCapability("description").toString()+"_"+method.getName());
			
		}finally
		{
			PerfectoUtils.closeTest(_driver);
		}

		PerfectoUtils.closeTest(_driver);
	}
	 

 
}

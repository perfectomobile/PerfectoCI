package com.perfectomobile.test;

import java.lang.reflect.Method;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.perfectomobile.utils.PerfectoUtils;
import com.perfectomobile.P2E.*;

public abstract class P2ELogin extends BasicTest {

	private DesiredCapabilities caps;
	private Method method;

	@Test(dataProvider = "Capabilities", groups = { "P2E", "P1", "ALL" })
	public void login(DesiredCapabilities caps, Method method) {
		this.caps = caps;
		this.method = method;

		System.out.println("START WITH CAP " + caps.toString());
		RemoteWebDriver driver = null;
		
		try {
			driver = beforeTest(caps);

			Login login = new Login(driver);
			login.with("bcl1127", "test");
			PerfectoUtils.getScreenShot(
					driver,
					caps.getCapability("description").toString() + "_"
							+ method.getName());

			SettingMenu setting = new SettingMenu(driver);
			setting.logout();
			
		} catch (Exception e) {
			System.out.println("P2ELogin ended with Error");

		} finally {
			endTest(driver);
		}

	}

	@Override
	public RemoteWebDriver beforeTest(DesiredCapabilities caps) throws Exception {
		
		// TODO Auto-generated method stub
		System.out.println("P2ELogin" + caps.getCapability("description"));
		// add the Perfecto native tree capability
		caps.setCapability("automationName", "PerfectoMobile");
		RemoteWebDriver driver = PerfectoUtils.getDriver(caps);
		// open app

		return driver;
	}


	@Override
	public void endTest(RemoteWebDriver driver) {
		PerfectoUtils.closeTest(driver);
	}

}

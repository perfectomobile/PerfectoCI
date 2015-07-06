package com.perfectomobile.test;

import java.lang.reflect.Method;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import com.perfectomobile.utils.PerfectoUtils;

public class Test1 extends BasicTest {

	@Test(dataProvider = "Capabilities")
	public void f12(DesiredCapabilities caps, Method method) {
		System.out.println("Test1 in Test" + caps.getCapability("platformName"));
		RemoteWebDriver driver = PerfectoUtils.getDriver(caps, 10, 20);
		PerfectoUtils.getScreenShot(driver, caps.getCapability("platformName")
				.toString() + "_" + method.getName());
		PerfectoUtils.closeTest(driver);

	}

	@Override
	public RemoteWebDriver beforeTest(DesiredCapabilities caps) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void endTest(RemoteWebDriver driver) {
		// TODO Auto-generated method stub

	}
}

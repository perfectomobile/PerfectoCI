package test;

import java.lang.reflect.Method;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import utils.PerfectoUtils;

public class test2 extends basicTest{

 	@Test (dataProvider="Capabilities" )	
	public void f2_1(DesiredCapabilities caps, Method method) {
		System.out.println("Test2 in Test"+caps.getCapability("description"));
		setUpDriver(PerfectoUtils.getDriver(caps));
		PerfectoUtils.getScreenShot(_driver, caps.getCapability("description").toString()+"_"+method.getName());
		PerfectoUtils.closeTest(_driver);
		}
	
	@Test (dataProvider="Capabilities" )	
	public void f2_2(DesiredCapabilities caps, Method method) {
		System.out.println("Test2 in Test"+caps.getCapability("description"));
		setUpDriver(PerfectoUtils.getDriver(caps));
		PerfectoUtils.getScreenShot(_driver, caps.getCapability("description").toString()+"_"+method.getName());
		PerfectoUtils.closeTest(_driver);
		}

 
}

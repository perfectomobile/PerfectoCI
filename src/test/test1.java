package test;

import java.lang.reflect.Method;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import utils.PerfectoUtils;

public class test1 extends basicTest{

	@Test (dataProvider="Capabilities" )	
	public void f12(DesiredCapabilities caps, Method method) {
		System.out.println("Test1 in Test"+caps.getCapability("platformName"));
		setUpDriver(PerfectoUtils.getDriver(caps));
		PerfectoUtils.getScreenShot(_driver, caps.getCapability("platformName").toString()+"_"+method.getName());
		PerfectoUtils.closeTest(_driver);
		
		}

	@Override
	public void beforeTest(DesiredCapabilities caps) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execTest() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endTest() {
		// TODO Auto-generated method stub
		
	}
}

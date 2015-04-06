package utils;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
 
/*
 *
 *   Class Name  : PerfectoMobileBasicTest
 *	Author      : Uzi Eilon <uzie@perfectomobile.com>
 *	
 *	Description :
	Basic abstract perfecto mobile test - Each test need to extend this class and implement the actual test in the PerfectoMobileBasicTest
 *	This basic test handles the driver and the device
 */

public abstract  class PerfectoTest {


	String _DeviceId = null;
	RemoteWebDriver _driver;
	boolean _status = true; 


	public PerfectoTest (RemoteWebDriver driver)
	{
		_driver = driver;
	} 



	public Boolean getStatus() {
		return _status  ;
	}

	public void setDeviceID(String Device) {
		_DeviceId= Device;

	}

	public  void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	public void closeTest() {
		_driver.quit();

	}

	public abstract void beforeTest() throws Exception ;
	public abstract void execTest() throws Exception ;
	public abstract void endTest() throws Exception ;
	



 	 
}


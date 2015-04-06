package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class basicTest {

	static RemoteWebDriver _driver;
	public basicTest() {
	
	}

	public static void setUpDriver(RemoteWebDriver driver) 
	{
		_driver=driver;
	}
	
	@DataProvider(name = "Capabilities", parallel = true)
	public static Iterator<Object[]> getDesiredCapabilities() {

		List<DesiredCapabilities> capabilities = utils.PerfectoUtils.readFromExecl("C:\\aaa\\CustomersTest\\exmple1.xls");

		List<Object[]> objects = new ArrayList<>();
		if (!(capabilities.isEmpty())){
			for (DesiredCapabilities object : capabilities) {
				objects.add(new Object[] { object} );
			}
		}		return objects.iterator();
	}
	
	 
}

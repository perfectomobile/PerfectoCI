package test;

import java.lang.reflect.Method;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import PayPal.loginPageLinks;
import utils.PerfectoUtils;

public abstract class PayPalLoginPageLinks extends basicTest{

 	Method _method;
	@Test (dataProvider="Capabilities",groups = { "PAYPAL", "P1", "ALL" })  
	public void login(DesiredCapabilities caps, Method method) {
 		_method = method;
		
		try {
			 beforeTest(caps);
			 execTest();
		} catch (Exception e) {
			System.out.println("PayPal ended with Error");

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
		DesiredCapabilities caps = (DesiredCapabilities) _driver.getCapabilities();
		System.out.println("payPal"+caps.getCapability("description"));
		setUpDriver(PerfectoUtils.getDriver(caps));

		try {
		 
			PayPal.loginPageLinks lp = new loginPageLinks(_driver);
			List<WebElement> cq = lp.getCommonQestions();
			// loop over all the questions
			for (WebElement question : cq) {
				System.out.println(question.getText());
			}
	 
			
		} catch (Exception e) {
			e.printStackTrace();
			 
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

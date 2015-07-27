package com.perfectomobile.test;

import java.io.File;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.perfectomobile.starbucks.Login;
import com.perfectomobile.starbucks.MainPage;
import com.perfectomobile.starbucks.Settings;
import com.perfectomobile.utils.PerfectoUtils;

public class StarbucksLogin2 extends BasicTest2 {


	protected static String capabilitiesFilePath = "testData2.xlsx";


	@Factory(dataProvider="factoryData")
	public StarbucksLogin2(DesiredCapabilities caps){
		super(caps);
		automationName = PERFECTO_MOBILE_AUTOMATION_NAME; 
	}

	@DataProvider(name="factoryData", parallel=true)
	public static Object[][] factoryData() throws Exception {

		int ColumnsToRead = 11;

		ClassLoader classLoader = PerfectoUtils.class.getClassLoader();
		File inputWorkbook = new File(classLoader.getResource(capabilitiesFilePath).getFile());

		Object[][] s = generateArrayFromExcel(inputWorkbook, "devices", ColumnsToRead);		
		Object [][] k = getCapabilitiesArrary(s);
		return k;
	}



	// Test (dataProvider="Capabilities",groups = { "STARBUCKS", "P1", "ALL" })
	@Test
	public void login() {
		try {

			Login login = new Login(driver);
			MainPage mp = login.with("uzi.eilon@gmail.com", "Perfecto1", "Uzi");
			Settings settings = mp.goToSettingPage();
			settings.signout();

			// PerfectoUtils.getScreenShot(_driver,
			// _caps.getCapability("description").toString()+"_"+_method.getName());
			// Change to OCR
			//switchToContext(driver, "VISUAL");
			//login.validatevalidateFirstPage();
			//switchToContext(driver, "NATIVE_APP");

		} catch (Exception e) {
			System.out.println("**************StarbucksLogin ended with Error**************");
			e.printStackTrace();
		} 

	}

	@Parameters({"testCycle"})
	@Override
	@BeforeClass
	public void beforeClass(String testCycle) throws Exception {

		super.beforeClass(testCycle);

		// open app
		switchToContext(driver, "NATIVE_APP");

		String Platform =driver.getCapabilities().getCapability("platformName").toString();
		PerfectoUtils.startApp("Starbucks", driver);

	}

	@Override
	@AfterClass
	public void afterClass() {

		super.afterClass();

	}



}

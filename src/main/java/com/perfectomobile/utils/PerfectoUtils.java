package com.perfectomobile.utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.regexp.recompile;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.selenium.util.EclipseConnector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class PerfectoUtils {



	private static final String REPOSITORY = "PUBLIC:";

	public PerfectoUtils() {


	}

	public static RemoteWebDriver getDriver(DesiredCapabilities cap)
	{
		System.out.println("Current capabilities " + cap.toString());

		RemoteWebDriver driver;
		boolean waitForDevice = true;
		int index = 10;
		do {
			try{
				try {
					String host = System.getProperty("np.testHost", "qatestlab.perfectomobile.com");
					String username = System.getProperty("np.testUsername", "test_automation@gmail.com");
					String password = System.getProperty("np.testPassword", "Test_automation");

					cap.setCapability("user", username);
					cap.setCapability("password", password);
//				doesn't work	
//					EclipseConnector connector = new EclipseConnector(); 
//					String eclipseExecutionId = connector.getExecutionId();                  
//					cap.setCapability("eclipseExecutionId", cap); 
//					
					
					driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), cap);
					//driver = new RemoteWebDriver(cap);
					System.out.println("Run started");
					return driver;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				waitForDevice = false;
			}

			catch (WebDriverException e) {
				index--;
				System.out.println("device not found, index = " + index);
				System.out.println("Current capabilities " + cap.toString());
				sleep(30000);
				if (e.getMessage().contains("command browser open")) {
					waitForDevice = false;
				}
			}

		} while (waitForDevice && index > 0);
		return null;

	}
	
	public static List<DesiredCapabilities> readFromExecl(String inputFile)
	{
		ClassLoader classLoader = PerfectoUtils.class.getClassLoader();
		File inputWorkbook = new File(classLoader.getResource(inputFile).getFile());
		//File inputWorkbook = new File(inputFile);
		Workbook w;
		List<DesiredCapabilities> DCList = new ArrayList<>();
		//	capabilities.setCapability("platformName", "Android");

		try {
			String title = "";
			String cellVal = "";
			DesiredCapabilities capabilities = null;
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// start from 1 > don't want to run on title raw
			for (int rowNum = 1; rowNum < sheet.getRows(); rowNum++) {
				//create DC per line 
				capabilities = new DesiredCapabilities("", "", Platform.ANY);

				for (int col = 0; col < sheet.getColumns(); col++) { 
					Cell cell = sheet.getCell(col,rowNum);
					title = sheet.getCell(col, 0).getContents();
					cellVal = cell.getContents();
					if (!cellVal.equals(""))
					{
						//	System.out.println("Add " + title + " " + cellVal);
						capabilities.setCapability(title, cellVal);
					}
				}
				DCList.add(capabilities);
			}

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DCList;
	}

	public static void closeTest(RemoteWebDriver driver)
	{
		System.out.println("CloseTest");
		driver.quit();
	}


//	private void uploadMedia(String resource, String repositoryKey) throws URISyntaxException, IOException {
//		repositoryKey = REPOSITORY;
//		String FILENAME;
//		File file = new File(FILENAME);
//		
//		d.uploadMedia(repositoryKey, file);
//		File file = loadResource(resource);
//		_driver.uploadMedia(repositoryKey, file);
//	}

	
	
	
	public static void getScreenShot(RemoteWebDriver driver,String name )
	{
		driver   = (RemoteWebDriver) new Augmenter().augment( driver );
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File("c:\\test\\"+name+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void installApp(String appLocation,RemoteWebDriver d )
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("file", appLocation);
		d.executeScript("mobile:application:install", params);
	}
	
	
	public static void startApp(String appName,RemoteWebDriver d )
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("name", appName);
		d.executeScript("mobile:application:open", params);
	}
	public static void uninstallApp(String appName,RemoteWebDriver d )
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("name", appName);
		d.executeScript("mobile:application:uninstall", params);
	}
	
	public static void swipe(String start,String end,RemoteWebDriver d )
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("start", start);  //50%,50%
		params.put("end", end);  //50%,50%

		d.executeScript("mobile:touch:swipe", params);

	}
	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}

	}

	public static void switchToContext(RemoteWebDriver driver, String context) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		Map<String,String> params = new HashMap<String,String>();
		params.put("name", context);
		executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
	}


}
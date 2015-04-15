package com.perfectomobile.utils;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.regexp.recompile;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class PerfectoUtils {



	public PerfectoUtils() {


	}

	public static RemoteWebDriver getDriver(DesiredCapabilities cap)
	{

		RemoteWebDriver driver;
		boolean waitForDevice = true;
		int index = 10;
		do {
			try{
				System.out.println("Current capabilities " + cap.toString());

				System.out.println("Run started");
				try {
					String host = "demo.perfectomobile.com";
					String user = URLEncoder.encode("uzie@perfectomobile.com", "UTF-8");
					String password = URLEncoder.encode("Perfecto1", "UTF-8");
					//				
					driver = new RemoteWebDriver(new URL("https://" + user + ':' + password + '@' + host + "/nexperience/wd/hub"), cap);
					return driver;

				} catch (  MalformedURLException | UnsupportedEncodingException e) {
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

		} while (waitForDevice && index > 0); //Utils.continueTest()
		return null;

	}

	public static List<DesiredCapabilities>  readFromExecl(String inputFile)
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
				capabilities = new DesiredCapabilities("MobileOS", "", Platform.ANY);

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
	
	public static void startApp(String appName,RemoteWebDriver d )
	{
 		Map<String,String> params = new HashMap<String,String>();
		params.put("name", appName);
		d.executeScript("mobile:application:open", params);
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

}
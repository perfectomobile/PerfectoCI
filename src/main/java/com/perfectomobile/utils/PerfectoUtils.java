package com.perfectomobile.utils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.regexp.recompile;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Reporter;

import com.google.common.base.Function;
//import com.perfectomobile.selenium.util.EclipseConnector;



import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class PerfectoUtils {



	private static final String REPOSITORY = "PUBLIC:";

	public PerfectoUtils() {


	}

	public static RemoteWebDriver getDriver(DesiredCapabilities cap, int retries, int retryIntervalSeconds) {
		return getDriver(cap, retries, retryIntervalSeconds, "");
	}
	
	public static RemoteWebDriver getDriver(DesiredCapabilities cap, int retries, int retryIntervalSeconds, String automationName)
	{
		System.out.println("Current capabilities " + cap.toString());

		RemoteWebDriver driver;
		boolean waitForDevice = true;
		int index = retries;
		do {
			try {
				String host = System.getProperty("np.testHost", "qatestlab.perfectomobile.com");
				String username = System.getProperty("np.testUsername", "test_automation@gmail.com");
				String password = System.getProperty("np.testPassword", "Test_automation");
				
				cap.setCapability("user", username);
				cap.setCapability("password", password);

				cap.setCapability("automationName", automationName);
				
//				doesn't work	
//					EclipseConnector connector = new EclipseConnector(); 
//					String eclipseExecutionId = connector.getExecutionId();                  
//					cap.setCapability("eclipseExecutionId", cap); 
//					
				
				driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), cap);
				//driver = new RemoteWebDriver(cap);
				System.out.println("Driver Created");
				return driver;

			} catch (Exception e) {
				index--;
				System.out.println("device not found: " + cap.toString() +"\n Retries left: " + index);
				//System.out.println();
				sleep(retryIntervalSeconds * 1000);
				if (e.getMessage().contains("command browser open")) {
					waitForDevice = false;
				}
			}
				//waitForDevice = false;
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

	public static void downloadReport(RemoteWebDriver driver, String type, String fileName) throws IOException {
		try { 
			String command = "mobile:report:download"; 
			Map<String, Object> params = new HashMap<>(); 
			params.put("type", type); 
			String report = (String)driver.executeScript(command, params); 
			File reportFile = new File(fileName + "." + type); 
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(reportFile)); 
			byte[] reportBytes = OutputType.BYTES.convertFromBase64Png(report); 
			output.write(reportBytes); output.close(); 
		} catch (Exception ex) { 
			System.out.println("Got exception " + ex); }
	}
	
	public static void downloadAttachment(RemoteWebDriver driver, String type, String fileName, String suffix) throws IOException {
		try {
			String command = "mobile:report:attachment";
			boolean done = false;
			int index = 0;

			while (!done) {
				Map<String, Object> params = new HashMap<>();	

				params.put("type", type);
				params.put("index", Integer.toString(index));

				String attachment = (String)driver.executeScript(command, params);
				
				if (attachment == null) { 
					done = true; 
				}
				else { 
					File file = new File(fileName + index + "." + suffix); 
					BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file)); 
					byte[] bytes = OutputType.BYTES.convertFromBase64Png(attachment);	
					output.write(bytes); 
					output.close(); 
					index++; }
			}
		} catch (Exception ex) { 
			System.out.println("Got exception " + ex); 
		}
	}


	public static String getCurrentContextHandle(RemoteWebDriver driver) {		  
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		String context =  (String) executeMethod.execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
		return context;
	}

	public static List<String> getContextHandles(RemoteWebDriver driver) {		  
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		List<String> contexts =  (List<String>) executeMethod.execute(DriverCommand.GET_CONTEXT_HANDLES, null);
		return contexts;
	}
	public static boolean checkVisual(RemoteWebDriver driver, String needle){
		String previousContext = getCurrentContextHandle(driver);
		// Switch to visual driver, to perform text checkpoint
		switchToContext(driver, "VISUAL");
		
		// Perform the checkpoint
		try{
			driver.findElement(By.linkText(needle));
		}
		catch(Exception e){
			switchToContext(driver, previousContext);
			return false;
		}
		
		// Switch back to webview context
		switchToContext(driver, previousContext);
		return true;
		
	}
	/* Wait until the objects loads until the timeout */
	  public static WebElement fluentWait(final By locator, RemoteWebDriver driver, long timeout) {
		 
		try {
			 FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				        .withTimeout(timeout, TimeUnit.SECONDS)
				        .pollingEvery(250, TimeUnit.MILLISECONDS)
				        .ignoring(Exception.class);
				        //.ignoring(NoSuchElementException.class);
				       
					  WebElement webelement = wait.until(new Function<WebDriver, WebElement>() {
						  public WebElement apply(WebDriver driver) {
				            return driver.findElement(locator);
						  }
					  });
					    return  webelement;
		} catch (Exception e) {
			return null;
		}
		 
		
	  }
	  public static String getDateAndTime(int offset){
		  Calendar c = Calendar.getInstance();
		  c.setTime(new Date());
		  c.add(Calendar.DATE, offset);
		  DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss z");
		  return dateFormat.format(c.getTime());
	  }
	  
	  
	  public static void getScreenshotOnError(RemoteWebDriver driver) {
		  
		String errorFile = takeScreenshot(driver);
		Reporter.log("Error screenshot saved in file: " + errorFile);
		  
		  
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
	  
	  public static String takeScreenshot(RemoteWebDriver driver) {
		  
		  String filePath = new File("").getAbsolutePath();
		  filePath += "\\test-output\\screenshots";
		  File theDir = new File(filePath);

		  // if the directory does not exist, create it
		  if (!theDir.exists()) {
			  //System.out.println("creating directory: " + directoryName);

			  try{
				  theDir.mkdir();
			  } 
			  catch(SecurityException se) {
				  
				  return null;
			  }        
		  }
		  filePath+= "\\";		  
		  File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		  String filename = filePath + getDateAndTime(0) + ".png";
		  System.out.println(filename);
			try {
				FileUtils.copyFile(scrFile, new File(filename+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		  return filename;
	  }
	  
	  

	  public static DesiredCapabilities getCapabilites(String deviceName, String platformName, String platformVersion, String manufacturer,
			  String deviceModel, String deviceResolution, String deviceNetwork, String deviceLocation, String deviceDescription, String browserName, String automationName) throws Exception{
		  
		  DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
		  		  
		  if(!deviceName.equals("")){
			  capabilities.setCapability("deviceName", deviceName);
		  }
		  if(!platformName.equals("")){
			  capabilities.setCapability("platformName", platformName);
		  }
		  if(!platformVersion.equals("")){
			  capabilities.setCapability("platformVersion", platformVersion);
		  }
		  if(!manufacturer.equals("")){
			  capabilities.setCapability("manufacturer", manufacturer);
		  }
		  if(!deviceModel.equals("")){
			  capabilities.setCapability("model", deviceModel);
		  }
		  if(!deviceResolution.equals("")){
			  capabilities.setCapability("resolution", deviceResolution);
		  }
		  if(!deviceNetwork.equals("")){
			  capabilities.setCapability("network", deviceNetwork);
		  }
		  if(!deviceLocation.equals("")){
			  capabilities.setCapability("location", deviceLocation);
		  }
		  if(!deviceDescription.equals("")){
			  capabilities.setCapability("description", deviceDescription);
		  }
		  if(!automationName.equals("")){
			  capabilities.setCapability("automationName", automationName);
		  }
		  		 
		  return capabilities;
	  }
}
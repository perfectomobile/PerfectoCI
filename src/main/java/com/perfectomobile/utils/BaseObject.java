package com.perfectomobile.utils;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class BaseObject {

	protected RemoteWebDriver driver;

	public BaseObject(RemoteWebDriver driver) {
		this.driver = driver;
	}

	public void visit(String url) {
		driver.get(url);
	}

	public WebElement find(By locator) {
		return driver.findElement(locator);
	}

	public void click(By locator) {
		find(locator).click();
	}

	public Boolean swipeAndSearch(By locator, String start, String end, Integer attempts) {
		boolean isFound = false;
		while (!isFound && attempts > 0)
		{
			String result = find(locator).getAttribute("isvisible");
			isFound = Boolean.valueOf(result);
			if (!isFound) {
				String commandStartApp = "mobile:touch:swipe";
				Map<String, Object> paramsSwipe = new HashMap<>();
				paramsSwipe.put("start",start);
				paramsSwipe.put("end",end);
				driver.executeScript(commandStartApp, paramsSwipe);
				attempts--;
			}
		} 

		return isFound;
	}

	public void type(String inputText, By locator) {
		find(locator).sendKeys(inputText);
	}

	public void submit(By locator) {
		find(locator).submit();
	}

	public Boolean isDisplayed(By locator) {

		try {

			return find(locator).isDisplayed();

		} catch (org.openqa.selenium.NoSuchElementException exception) {
			return false;

		}
	}

	public Boolean waitForIsDisplayed(By locator, Integer... timeout) {
		try {
			waitFor(ExpectedConditions.visibilityOfElementLocated(locator),
					(timeout.length > 0 ? timeout[0] : null));
		} catch (org.openqa.selenium.TimeoutException exception) {
			return false;
		}

		return true;
	}

	private void waitFor(ExpectedCondition<WebElement> condition,
			Integer timeout) {
		timeout = timeout != null ? timeout : 5;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(condition);
	}

	public void textIsOnPage(WebElement textToFind)		 {
	 textToFind = driver.findElement(By.xpath("//*[contains(.,'textToFind')]")); 
	 Assert.assertEquals("Text found!", textToFind);
	}
	
	
	public RemoteWebDriver getDriver() {
		return driver;
	}

	public void switchToVisual() {

		PerfectoUtils.switchToContext(driver, "VISUAL");
	}

	public void switchToNative() {

		PerfectoUtils.switchToContext(driver, "NATIVE_APP");
	}

}
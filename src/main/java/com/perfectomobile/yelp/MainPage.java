package com.perfectomobile.yelp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.utils.BaseObject;

public class MainPage extends YelpBaseView {
	
		private By searchTFLocator = By.xpath("//textfield[@resourceid='com.yelp.android:id/searchbar']");
		private By nearByLocator = By.xpath("//button[text()='Nearby']");
		private By checkInsLocator = By.xpath("//button[text()='Check-Ins']");
		private By aboutMeLocator = By.xpath("//button[text()='About Me']");
		private By bookmarksLocator = By.xpath("//button[text()='Bookmarks']");
		private By monocleLocator = By.xpath("//button[text()='Monocle']");
		private By recentsLocator = By.xpath("//button[text()='Recents']");
		
		private By loginValidateByOCR = By.linkText("Search");
		
		public MainPage(RemoteWebDriver driver) {
			super(driver);
		}

		public QuickSearchPage  searchTF(){
			click(searchTFLocator);
			return new QuickSearchPage(driver);
		}

//		opens the nearby page
		public BaseObject  nearby(){
			click(nearByLocator);
			return new BaseObject(getDriver());
		}
//		opens the checkins page		
		public BaseObject  checkIns(){
			click(checkInsLocator);
			return new BaseObject(getDriver());
		}
//		opens the aboutme page
		public BaseObject  aboutMe(){
			click(aboutMeLocator);
			return new BaseObject(getDriver());
		}
//		opens the bookmarks page		
		public BaseObject  bookmarks(){
			click(bookmarksLocator);
			return new BaseObject(getDriver());
		}
//		opens the monocle page		
		public BaseObject  monocle(){
			click(monocleLocator);
			return new BaseObject(getDriver());
		}
//		opens the recents page		
		public BaseObject  recents(){
			click(recentsLocator);
			return new BaseObject(getDriver());
		}
		
		
		

		public Boolean validatevalidateFirstPage() {

			return waitForIsDisplayed(loginValidateByOCR, 40);

		}
	}

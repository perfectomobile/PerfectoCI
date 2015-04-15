package com.perfectomobile.P2E;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.utils.BaseObject;

public class SettingMenu extends BaseObject {
	private By menuButton = By
			.xpath(".//*[@src='/P2AFE/Content/Images/Settings.png']");
	private By logoutOpt = By.xpath("//li[@id='logOut']/a");
	private By aboutOPT = By.id("aboutItem");

	public SettingMenu(RemoteWebDriver driver) {
		super(driver);
	}

	public void logout() {
		click(menuButton);
		click(logoutOpt);

	}

	public void about() {
		click(menuButton);
		click(aboutOPT);

	}

}

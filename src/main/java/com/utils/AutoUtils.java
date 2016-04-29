package com.utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AutoUtils {
	
	private final WebDriver webDriver;
	
	public AutoUtils() {
		webDriver = new FirefoxDriver();
	}
	
	public static void main(String[] args) {
		new AutoUtils()
		.fetchPage("https://twitter.com/");
	}
	
	public AutoUtils fetchPage(final String url) {		
		webDriver.get(url);
		return this;
	}
	
	public WebElement locateElementById(final String id) {
		return webDriver.findElement(By.id(id));
	}
	
	public List<WebElement> findElementsByClass(final String className) {
		return webDriver.findElements(By.className(className));
	}
	
	public void quit() {
		webDriver.quit();
	}

}

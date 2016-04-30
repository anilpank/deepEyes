package com.naukri;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class NaukriLaunch {
	
	public static void main(String[] args) {
		new NaukriLaunch().launch();
	}
	
	public void launch() {
		System.setProperty("webdriver.chrome.driver","C:\\anil\\deploy\\Installables\\selenium-driver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();		
        driver.get("https://www.naukri.com");
        WebElement elem = driver.findElement(By.id("login_Layer"));
        System.out.println(elem.getText());
        elem.click();
        WebElement emailIdInput = driver.findElement(By.id("eLogin"));
        emailIdInput.sendKeys("ramjanakverma@gmail.com"); 
        WebElement passwordInput = driver.findElement(By.id("pLogin"));
        passwordInput.sendKeys("");
        driver.quit();
	}
}

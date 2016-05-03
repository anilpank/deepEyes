package com.naukri;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.properties.PropReader;

public class NaukriLaunch {
	
	private static final Logger log = LogManager.getLogger(NaukriLaunch.class);

	public static void main(String[] args) {
		new NaukriLaunch().launch();
	}

	public void launch() {
		try {
			System.setProperty("webdriver.chrome.driver","C:\\anil\\deploy\\Installables\\selenium-driver\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();	
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			log.trace("Applying for 5 jobs in Naukri");
			driver.get("https://www.naukri.com");
			WebElement elem = driver.findElement(By.id("login_Layer"));
			System.out.println(elem.getText());
			elem.click();
			WebElement emailIdInput = driver.findElement(By.id("eLogin"));
			PropReader propReader = new PropReader();
			emailIdInput.sendKeys(propReader.get("naukri_email")); 
			WebElement passwordInput = driver.findElement(By.id("pLogin"));
			passwordInput.sendKeys(propReader.get("naukri_password"));
			driver.findElement(By.xpath("//*[@id=\"lgnFrm\"]/div[7]/button")).click();

			driver.findElement(By.linkText("(View all jobs)")).click();
			String mainWindowHandle = driver.getWindowHandle();
			log.trace("mainWindowHandle is " + mainWindowHandle);

			List<WebElement> jobElems =  driver.findElements(By.cssSelector("div[type='tuple']"));
			int maxSize=5;
			int count=0;
			for (WebElement jobElem : jobElems) {
				jobElem.findElement(By.tagName("a")).click();
				count++;
				if (count>maxSize) {
					break;
				}
			}
			
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			for (String tab : tabs) {
				applyForJob(tab, driver, mainWindowHandle);				
			}
			//Switch to main/parent window
			driver.switchTo().window(mainWindowHandle);

			driver.quit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private JobAppliedStatus applyForJob(String tab, WebDriver driver, String mainWindowHandle) {
		JobAppliedStatus status = new JobAppliedStatus(false, "Applying for " + tab);
		try {
			if (tab.equalsIgnoreCase(mainWindowHandle)) {
				return status;
			}
			driver.switchTo().window(tab);
			WebElement trig1Elem = driver.findElement(By.id("trig1"));
			if (trig1Elem != null) {
				trig1Elem.click();
				WebElement closeBtn = driver.findElement(By.id("closeLB1"));
				if (closeBtn != null) {
					closeBtn.click();			
				}
				WebElement confMsgElem = driver.findElement(By.id("cjaConfMsg"));
				if (confMsgElem != null) {
					status.setMessage(confMsgElem.findElement(By.tagName("div")).getText());
					status.setSuccess(true);
					log.trace(status.getMessage());
				}
			}				
			
		} catch (Exception e) {
			log.error("Unable to apply for job window " + tab, e);
			status.setMessage("Unable to apply for job window " + tab);
		}
		
		finally {
			driver.close();
		}
		
		return status;
		
	}
}

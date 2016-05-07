package com.naukri;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

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
			log.trace("Applying for n jobs in Naukri");
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
			int maxApplyCount = Integer.parseInt(propReader.get("naukri_maxApplyCount"));			
			int count=0;
			for (WebElement jobElem : jobElems) {
				jobElem.findElement(By.tagName("a")).click();
				count++;
				if (count>maxApplyCount) {
					break;
				}
			}

			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			List<JobAppliedStatus>jobStatuses = new ArrayList<JobAppliedStatus>();
			for (String tab : tabs) {
				jobStatuses.add(applyForJob(tab, driver, mainWindowHandle));							
			}
			write(jobStatuses);

			//Switch to main/parent window
			driver.switchTo().window(mainWindowHandle);

			driver.quit();
		} catch (Exception e) {
			log.error("Unable to launch Naukri ", e);
			e.printStackTrace();
		}
	}

	public void write(List<JobAppliedStatus> jobStatuses) {
		try {

			String fileTimeStamp = new SimpleDateFormat("yyyy_MMM_dd_hh_mm_ss'.txt'").format(new Date());
			File file = new File(new PropReader().get("directory")+"result"+fileTimeStamp);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			long successCount = jobStatuses.stream().filter(status -> status.isSuccess()).count();
			bw.write("Successfully applied for " + successCount + " jobs");
			for (JobAppliedStatus jobAppliedStatus : jobStatuses) {
				bw.write(jobAppliedStatus.toString());
			}		
			bw.close();
		}
		catch (IOException e) {
			log.error(e);
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
			if (!tab.equalsIgnoreCase(mainWindowHandle)) {
				driver.close();
			}			
		}		
		return status;		
	}
}

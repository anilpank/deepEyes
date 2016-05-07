package com.naukri;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import com.properties.PropReader;
import com.util.MailUtil;
import com.naukri.NaukriLaunch.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class NaukriLaunchTest {
	
	@Test
	public void test_Write() {
		List<JobAppliedStatus> jobStatuses = new ArrayList<JobAppliedStatus>();
		jobStatuses.add(new JobAppliedStatus(true, "File is processed"));
		assertTrue(new NaukriLaunch().write(jobStatuses));		
	}

	
}

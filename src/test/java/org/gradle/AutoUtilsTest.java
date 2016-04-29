package org.gradle;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.utils.AutoUtils;

public class AutoUtilsTest {
	public static AutoUtils autoUtils;
	
	@BeforeClass
	public static void init() {
		autoUtils = new AutoUtils();
		autoUtils.fetchPage("https://www.google.co.in");
	}

	
	@Test
	public void test() {		
		String txt = 
				autoUtils				
				.locateElementById("cst")
				.getText();
		System.out.println(txt);
		
	}
	
	@Test
	public void testClass() {		
		int size = autoUtils				
				.findElementsByClass("ctr-p")
				.size();
		assertTrue(size>0);		
	}
	
	@AfterClass
	public static void quitAll() {
		System.out.println("Quitting the driver");
		autoUtils.quit();
	}

}

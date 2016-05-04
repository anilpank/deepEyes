package com.properties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropReader {
	private static final Logger logger = LogManager.getLogger(PropReader.class);
	private final Map<String, String> propMap = new HashMap<String, String>();

	public PropReader() {
		init();
	}

	private void init() {		
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = PropReader.class.getClassLoader().getResourceAsStream("deepEyes.properties");
			// load a properties file
			prop.load(input);
			Enumeration em = prop.keys();
			while(em.hasMoreElements()){
				String key = (String)em.nextElement();
				String value = prop.getProperty(key);				
				propMap.put(key, value);
			}

		} catch (IOException ex) {
			logger.error("Unable to init ", ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
	}

	public String get(final String key) {
		return propMap.get(key);
	}
	
	public static void main(String[] args) {
		logger.info("just tracing this");
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = PropReader.class.getClassLoader().getResourceAsStream("deepEyes.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			System.out.println(prop.getProperty("database"));
			System.out.println(prop.getProperty("dbuser"));
			System.out.println(prop.getProperty("dbpassword"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
package com.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropReader {
	private static final Logger logger = LogManager.getLogger(PropReader.class);

	public String get(final String key) {
		logger.trace("just tracing this");
		String value = null;
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = PropReader.class.getClassLoader().getResourceAsStream("deepEyes.properties");
			// load a properties file
			prop.load(input);
			value = prop.getProperty(key);
		} catch (IOException ex) {
			logger.error("Unable to get property for " + key, ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
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
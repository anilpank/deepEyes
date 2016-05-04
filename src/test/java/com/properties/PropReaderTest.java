package com.properties;

import com.properties.PropReader.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

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
public class PropReaderTest {

	@Test
	public void test_get() throws Exception {
		PropReader target = new PropReader();
		assertTrue(target.get("database").equalsIgnoreCase("dure"));
	}

	
}

package com.selenium.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandler {

	private static Properties prop = new Properties();

	static {
		try {
			InputStream input = new FileInputStream("src/test/resources/config.properties");
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return prop.getProperty(key);
	}

	public static int getInt(String key, int defaultValue) {
		try {
			return Integer.parseInt(prop.getProperty(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
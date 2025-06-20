package com.selenium.utils;

import java.io.InputStream; //read config.properties as stream from filepath
import java.util.Properties; //built-in java class to handle .properties

public class PropertiesHandler {

    private static Properties properties = new Properties();
	
	//Loads config.properties 
	static {
		try {
			InputStream input = PropertiesHandler.class
					.getClassLoader()
					.getResourceAsStream("config.properties"); //converts to input stream to be read
			
			if (input==null) { //if file not found
				throw new RuntimeException("config.properties file not found in classpath");
			}
			
			properties.load(input);	//loads key values + ensures tests not carried out if config not loaded
		} catch (Exception e) {
			throw new RuntimeException("Failed to load config.properties: ", e);
		}
	}
	
	//Get the value via keys
	public static String get(String key) {
		return properties.getProperty(key);
	}
	
	//Prevents crashes from missing properties
	public static int getInt(String key, int defaultValue) {
		try {
			return Integer.parseInt(properties.getProperty(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}
}

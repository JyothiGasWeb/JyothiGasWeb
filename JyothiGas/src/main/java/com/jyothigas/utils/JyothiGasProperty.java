package com.jyothigas.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JyothiGasProperty {
	
	private static JyothiGasProperty instance = new JyothiGasProperty();
	private Properties properties;
	
	private JyothiGasProperty()
	{
		InputStream is = this.getClass().getResourceAsStream("Configuration.properties");
		properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static JyothiGasProperty getInstance()
	{
		return instance;
	}
	
	public String getProperties(String key)
	{
		return properties.getProperty(key);
	}

}

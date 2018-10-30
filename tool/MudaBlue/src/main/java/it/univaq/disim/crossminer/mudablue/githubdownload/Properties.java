package it.univaq.disim.crossminer.mudablue.githubdownload;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Properties {

	public static String getProperties(String key) {
		java.util.Properties prop = new java.util.Properties();
		String propFileName = "resources/properties.properties";
		InputStream inputStream = Properties.class.getClassLoader().getResourceAsStream(propFileName);
		try {
			if (inputStream != null) prop.load(inputStream);
			else throw new FileNotFoundException("property file '"
						+ propFileName + "' not found in the classpath");
			String user = prop.getProperty("gitLocation");
			return user;
		} catch (IOException e) {
				return "C:/repos";
		}
	}
}
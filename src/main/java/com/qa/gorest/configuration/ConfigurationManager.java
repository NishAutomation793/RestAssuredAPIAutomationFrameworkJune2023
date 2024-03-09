package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.gorest.exceptions.APIFrameworkException;

public class ConfigurationManager {

	private Properties prop;
	private FileInputStream fp;

	public Properties initProp() {

		prop = new Properties();

		// Maven command for running the test cases :
		// mvn clean install -Denv="qa". System.getProperty("variable_Name") will pick
		// the key from mvn command

		String envName = System.getProperty("env");

		try {
			if (envName == null) {
				System.out.println("No Env Name is entered. Hence running test cases on default env");
				fp = new FileInputStream(".\\src\\test\\resources\\config\\config.properties");

			}

			else {

				switch (envName.trim().toLowerCase()) {
				case "qa":
					System.out.println("Running test cases on "+envName +" Environment ");
					fp = new FileInputStream(".\\src\\test\\resources\\config\\config.properties");
					
					break;
				case "dev":
					System.out.println("Running test cases on "+envName +" Environment ");
					fp = new FileInputStream(".\\src\\test\\resources\\config\\dev.config.properties");
					
						
					break;
				case "uat":

					System.out.println("Running test cases on "+envName +" Environment ");
					fp = new FileInputStream(".\\src\\test\\resources\\config\\uat.config.properties");
					
					
					break;
				case "prod":

					System.out.println("Running test cases on "+envName +" Environment ");
					fp = new FileInputStream(".\\src\\test\\resources\\config\\prod.config.properties");
					
					
					break;

				default:
					System.out.println("Wrong Env Name is entered");
					throw new APIFrameworkException("WRONG ENV NAME...");
				}
			}

			try {
				prop.load(fp);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return prop;
	}

}

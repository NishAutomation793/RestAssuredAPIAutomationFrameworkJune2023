package com.qa.gorest.utils;

public class StringUtil {
	
	public static String getRandomEmailId()
	{
		
		return "api"+System.currentTimeMillis()+"@mail.com";
	}

}

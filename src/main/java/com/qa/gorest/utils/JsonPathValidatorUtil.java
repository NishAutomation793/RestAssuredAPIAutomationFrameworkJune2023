package com.qa.gorest.utils;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;

public class JsonPathValidatorUtil {

	private String getJsonResponseAsString(Response response)
	{
		return response.getBody().asString();
		
	}
	
	public <T> T read(Response response, String jsonPath)
	{
		String jsonResponse=getJsonResponseAsString(response);
		
		return JsonPath.read(jsonResponse, jsonPath);
		
	}
	
	
	public <T> List<T> readList(Response response, String jsonPath)
	{
		String jsonResponse=getJsonResponseAsString(response);
		
		return JsonPath.read(jsonResponse, jsonPath);
		
	}
	
	public <T> List<Map<String,T>> readListOfMaps(Response response, String jsonPath)
	{
		String jsonResponse=getJsonResponseAsString(response);
		
		return JsonPath.read(jsonResponse, jsonPath);
		
	}
	
	
	
}

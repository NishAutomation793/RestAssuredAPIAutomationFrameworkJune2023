package com.qa.gorest.utils;

import java.util.List;
import java.util.Map;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class XMLPathValidatorUtil {

	private XmlPath getXmlPath(Response response)
	{
		String xmlResponse=response.getBody().asString();
		return new XmlPath(xmlResponse);
	}
	
	
	public <T> T read(Response response, String xmlPathExpression)
	{
		XmlPath xmlPathResponse=getXmlPath(response);
		return xmlPathResponse.get(xmlPathExpression);
		
	}
	
	public <T> List<T> readList(Response response, String xmlPathExpression)
	{
		XmlPath xmlPathResponse=getXmlPath(response);
		return xmlPathResponse.getList(xmlPathExpression);
		
	}
	
	public <T> List<Map<String,T>> readListofMaps(Response response, String xmlPathExpression)
	{
		XmlPath xmlPathResponse=getXmlPath(response);
		return xmlPathResponse.getList(xmlPathExpression);
		
	}
	
}

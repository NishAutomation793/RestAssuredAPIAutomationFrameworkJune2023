package com.qa.gorest.tests;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

public class GetUserTest extends BaseTest{
	
	@BeforeMethod
	public void getUserSetup()
	{
		restClient =new RestClient(prop, baseURI);
		
	}
	
	@Test
	public void getAllUsers() {
		
		restClient.getMethod("/public/v2/users",true, true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
		
	}
	
	@Test
	  public void getSingleUsers() {
	  
	  restClient.getMethod("/public/v2/users/6762901",true, true) .then().log().all()
	  .assertThat().statusCode(APIHttpStatus.OK_200.getCode()) .and().body("name",equalTo("Nishant"));
	  
	  }
	  
	@Test
	  public void getUsers_with_Query_Params() {
	  
	  Map<String, String> queryParams=new HashMap<String, String>();
	  queryParams.put("name","Drona"); queryParams.put("status","active");
	  queryParams.put("id","2322049");
	  
	  restClient.getMethod("/public/v2/users",null, queryParams, true, true)
	  .then().log().all() .assertThat().statusCode(APIHttpStatus.OK_200.getCode())
	  .and().body("name",contains("Drona Saini"));
	  
	  }
	 
}

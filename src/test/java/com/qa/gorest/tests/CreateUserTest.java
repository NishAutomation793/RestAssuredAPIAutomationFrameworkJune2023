package com.qa.gorest.tests;

import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.UserPojo;
import com.qa.gorest.utils.ExcelUtil;
import com.qa.gorest.utils.StringUtil;

public class CreateUserTest extends BaseTest {

	//Here we are creating multiple objects of RestClient so that different calls should get their separate objects
	
	@BeforeMethod
	public void creatUserSetup()
	{
		restClient =new RestClient(prop, baseURI);
		
	}
	
	
	@DataProvider
	public Object [][] getSheetData(){
		
		Object obj[][]=ExcelUtil.getSheetData(APIConstants.USER_SHEET_NAME);
		
		return obj;
	}
	
	@Test(dataProvider ="getSheetData")
	public void createUser(String name, String gender, String status)
	{
		
		UserPojo userPojo=new UserPojo(name,status,gender,StringUtil.getRandomEmailId());
		
		int userId=
				restClient.postMethod("/public/v2/users", "json", userPojo, true, true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode()).and().extract().path("id");
	
		System.out.println("User Id after POST call is: "+userId);
		
	// Fetching the User with help of UserId
		RestClient getRestClient=new RestClient(prop, baseURI);
		
		getRestClient.getMethod("/public/v2/users/"+userId,true, true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
		
	}
	
}

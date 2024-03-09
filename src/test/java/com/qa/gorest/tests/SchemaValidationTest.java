package com.qa.gorest.tests;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.UserPojo;
import com.qa.gorest.utils.StringUtil;

import io.qameta.allure.Description;



public class SchemaValidationTest extends BaseTest{


	@BeforeMethod
	public void creatUserSetup()
	{
		restClient =new RestClient(prop, baseURI);
		
	}
	
	
	@Description("For Checking User Schema Validation Test")
	@Test
	public void createUserSchemaValidation() {

		UserPojo userPojo=new UserPojo("Nishant","inactive","Male",StringUtil.getRandomEmailId());

		// Post Call to successfully Create a User


		restClient.postMethod("/public/v2/users", "json", userPojo, true, true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
		.and()
		.body(matchesJsonSchemaInClasspath("createUserSchema.json"));

}
	
	@Description("For Checking User Schema Validation Test in GET Call")
	@Test
	public void getUserSchemaValidation()
	{
		// Get Call to successfully check if user is created

		restClient.getMethod("/public/v2/users", "json", true, true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.OK_200.getCode())
		.and()
		.body(matchesJsonSchemaInClasspath("getUserSchema.json"));
	
	}
}

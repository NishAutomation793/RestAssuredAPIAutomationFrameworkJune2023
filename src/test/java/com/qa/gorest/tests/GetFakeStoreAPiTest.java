package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

import io.qameta.allure.Description;

public class GetFakeStoreAPiTest extends BaseTest{

	@BeforeMethod
	public void getFakeStroeAPiSetup()
	{
		restClient =new RestClient(prop, baseURI);
		
	}
	
	@Description("This is for fetching all the Products info from Fake Store API Test")
	@Test
	public void getAllProductsFakeStoreAPiTest() {
		
		restClient.getMethodNoAuth("/products",true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
		
	}
	
	@Description("This is for Fetching Single Product from Fake Store APi Test")
	@Test
	public void getSingleProductFakeStoreAPiTest() {
		
		restClient.getMethodNoAuth("/products/"+1,true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
		
	}

	
}

package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

public class GetFakeStoreAPiTest extends BaseTest{

	@BeforeMethod
	public void getFakeStroeAPiSetup()
	{
		restClient =new RestClient(prop, baseURI);
		
	}
	
	@Test
	public void getAllProductsFakeStoreAPiTest() {
		
		restClient.getMethodNoAuth("/products",true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
		
	}
	
	@Test
	public void getSingleProductFakeStoreAPiTest() {
		
		restClient.getMethodNoAuth("/products/"+1,true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
		
	}

	
}

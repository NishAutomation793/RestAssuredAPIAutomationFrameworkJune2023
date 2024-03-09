package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.ProductFakeStorePojo;

import io.qameta.allure.Description;
import io.restassured.response.Response;

public class CreateNewProductFakeStoreTest extends BaseTest {

	
	@BeforeMethod
	public void setUp()
	{
		
		restClient =new RestClient(prop,baseURI);
	}
	
	@DataProvider
	public Object[][] getData() {
		
		return new Object[][]
				{
				{"Test1", 2.77,"Description1","Image1","Category1"},
				{"Test2", 4.77,"Description2","Image2","Category2"},
				{"Test3", 8.77,"Description3","Image3","Category3"}
				};
	}
	
	@Description("This is the Creation of New Product In Product APi")
	@Test(dataProvider="getData")
	public void createNewProduct(String title,double price, String description, String image, String category)
	{
		ProductFakeStorePojo dp=new ProductFakeStorePojo(title,price,description,image,category );
		
		int productId=
				restClient.postMethod("/products", "json", dp, false, true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.OK_200.getCode()).and().extract().path("id");
	
		System.out.println("Product Id after POST call is: "+productId);
		
		// Fetching the Product with help of productId
				RestClient getRestClient=new RestClient(prop, baseURI);
				
			getRestClient.getMethod("/products/"+productId, "json", false, true)
				.then().log().all()
				.assertThat().statusCode(APIHttpStatus.OK_200.getCode());	

			
	}
	
}

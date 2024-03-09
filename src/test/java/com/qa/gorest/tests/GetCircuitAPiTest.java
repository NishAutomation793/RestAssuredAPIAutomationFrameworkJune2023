package com.qa.gorest.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.utils.JsonPathValidatorUtil;
import com.qa.gorest.utils.XMLPathValidatorUtil;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import io.qameta.allure.Description;
import io.restassured.response.Response;

public class GetCircuitAPiTest extends BaseTest {

	@BeforeMethod
	public void getCircuitAPiSetup() {
		restClient = new RestClient(prop, baseURI);

	}

	@Description("This is for Getting All Circuits Infor Test")
	@Test
	public void getAllCircuitsTest() {

		Response response = restClient.getMethodNoAuth("/api/f1/2017/circuits.json", true);

		int statusCode = response.statusCode();
		Assert.assertEquals(statusCode, APIHttpStatus.OK_200.getCode());

		JsonPathValidatorUtil js = new JsonPathValidatorUtil();
		List<String> circuitsName = js.readList(response, "$.MRData.CircuitTable.Circuits[*].circuitName");

		System.out.println("List of Circuits are: " + circuitsName);

		Assert.assertTrue(circuitsName.contains("Albert Park Grand Prix Circuit"));

	}

	@Description("This is for Getting all Circuits Infor Test with XML Response")
	@Test
	public void getAllCircuitsTestXmlExtraction() {

		Response response = restClient.getMethodNoAuth("/api/f1/2017/circuits.xml", true);

		int statusCode = response.statusCode();
		Assert.assertEquals(statusCode, APIHttpStatus.OK_200.getCode());

		XMLPathValidatorUtil xl = new XMLPathValidatorUtil();

		// Fetching the locality where circuitId ="America"

		String locality = xl.read(response, "**.findAll {it.@circuitId=='americas'}.Location.Locality");
		System.out.println("Locality Name is: " + locality);

		Assert.assertTrue(locality.contains("Austin"));

		// Fetching List of All circuitIds:

		List<String> circuitIds = xl.readList(response, "MRData.CircuitTable.Circuit.@circuitId");
		System.out.println("List of All Circuit Ids are: " + circuitIds);
		Assert.assertTrue(circuitIds.contains("bahrain"));

	}

}

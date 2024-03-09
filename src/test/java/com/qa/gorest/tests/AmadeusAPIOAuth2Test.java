package com.qa.gorest.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AmadeusAPIOAuth2Test extends BaseTest {

	private String accessToken;

	@Parameters({ "grantType", "clientId", "clientSecret" })
	@BeforeMethod
	public void accessTokenSetup(String grantType, String clientId, String clientSecret) {
		restClient = new RestClient(prop, baseURI);
		accessToken = restClient.generateAccessTokenOauth2("/v1/security/oauth2/token", grantType, clientId,
				clientSecret);
		System.out.println("Access Token for Oauth 2.0 is :" + accessToken);
	}

	@Test
	public void getFlightDetails() {
		// Here we have to generate separate
		// restClient object because the obje generated in Before Method will be used
		// for different API call ( generate Access Token) and this one will be used for
		// getting all flight details call

		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Authorization", "Bearer " + accessToken);

		Map<String, String> queryParams = new HashMap<String, String>();

		queryParams.put("origin", "PAR");
		queryParams.put("maxPrice", "200");

		RestClient restClientFlight = new RestClient(prop, baseURI);

		Response flightResponse = restClientFlight.getMethod("/v1/shopping/flight-destinations", headersMap, queryParams, false,true).then().log()
				.all().assertThat().statusCode(APIHttpStatus.OK_200.getCode()).and().extract().response();

		JsonPath js = flightResponse.jsonPath(); // Printing entire Json Response

		List<Object> list = js.getList("data");
		System.out.println("List of Entire Response Data is -----------------" + "\n" + list);

		// Printing list of Origins

		List<String> originList = js.getList("data.origin");
		System.out.println("Origin List is ---> " + "\n" + originList);

	}

}

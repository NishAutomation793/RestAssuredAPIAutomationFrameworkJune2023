package com.qa.gorest.client;

import static io.restassured.RestAssured.*;
import java.util.Properties;
import java.util.Map;
import java.util.Properties;

import com.qa.gorest.exceptions.APIFrameworkException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {

//	private static final String BEARER_TOKEN = "4d14c7128346af6d94bd14f954ca47330b930da69b80f2a8359b7d0cf15828fd";
//	private static final String BASE_URI = "https://gorest.co.in";

	private static RequestSpecBuilder specBuilder;
	
	private Properties prop;
	private String baseURI;
	
	private boolean isAuthHeaderAdded=false;

//	/**
//	 * Static Block to initialize the specBuilder ref_variable
//	 */
//	static {
//		specBuilder = new RequestSpecBuilder();
//	}

	public RestClient(Properties prop, String baseURI)
	{
		specBuilder = new RequestSpecBuilder();
		
		this.prop=prop;
		this.baseURI=baseURI;
	}
	
	
	public void addAuthorizationHeader() {

		if(!isAuthHeaderAdded)
		{		
		specBuilder.addHeader("Authorization", "Bearer " +prop.getProperty("tokenId"));
		isAuthHeaderAdded=true;
		}
		
	}

	private static void setContentType(String contentType) {

		switch (contentType.toLowerCase()) {
		case "json":
			specBuilder.setContentType(ContentType.JSON);

			break;
		case "xml":
			specBuilder.setContentType(ContentType.XML);

			break;
		case "html":
			specBuilder.setContentType(ContentType.HTML);

			break;
		case "text":
			specBuilder.setContentType(ContentType.TEXT);

			break;
		case "multipart":
			specBuilder.setContentType(ContentType.MULTIPART);

			break;

		default:
			System.out.println("Please Provide Correct Content Type");
			throw new APIFrameworkException("WRONG CONTENT TYPE");
		}

	}

	private RequestSpecification createRequestSpecNoAuth() {

		specBuilder.setBaseUri(baseURI);
		return specBuilder.build();

	}
	
	/**
	 * Just a basic request spec method with only base uri and authorization header
	 * 
	 * @return
	 */
	private RequestSpecification createRequestSpec(boolean includeAuth) {

		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
			addAuthorizationHeader();

		}
		return specBuilder.build();

	}

	/**
	 * Request Spec with Authorization Header and Content Type
	 * 
	 * @param contentType
	 * @return
	 */
	private RequestSpecification createRequestSpec(String contentType, boolean includeAuth) {

		specBuilder.setBaseUri(baseURI);
		
		if(includeAuth)
		{
			addAuthorizationHeader();

		}

		setContentType(contentType);
		return specBuilder.build();

	}

	/**
	 * Request Spec with Authorization Header and HasMap of extra Headers if any
	 * 
	 * @param contentType
	 * @return
	 */
	private RequestSpecification createRequestSpec(Map<String, String> headersMap, boolean includeAuth) {

		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorizationHeader();
		}
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build();

	}

	/**
	 * Request Spec with Authorization Header and HasMap of extra Headers if any and
	 * HashMap of Query Parameters if any
	 * 
	 * @param contentType
	 * @return
	 */
	private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, String> queryParams, boolean includeAuth) {

		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorizationHeader();
		}		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		if (queryParams != null) {
			specBuilder.addQueryParams(queryParams);
		}
		return specBuilder.build();

	}

	/**
	 * Request Spec with Authorization Header, Content Type and HasMap of extra
	 * Headers if any and HashMap of Query Parameters if any
	 * 
	 * @param contentType
	 * @return
	 */
	private RequestSpecification createRequestSpec(String contentType, Map<String, String> headersMap,
			Map<String, String> queryParams, boolean includeAuth) {

		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorizationHeader();
		}		specBuilder.setContentType(contentType);
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		if (queryParams != null) {
			specBuilder.addQueryParams(queryParams);
		}
		return specBuilder.build();

	}

// Creating Request Specifications for POST Call	

	private RequestSpecification createRequestSpec(Object requestBody, String contentType, boolean includeAuth) {

		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorizationHeader();
		}
		setContentType(contentType);
		if (requestBody != null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build();

	}

	private RequestSpecification createRequestSpec(Object requestBody, Map<String, String> headersMap, boolean includeAuth) {

		specBuilder.setBaseUri(baseURI);
		if(includeAuth)
		{
		addAuthorizationHeader();
		}		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		if (requestBody != null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build();

	}
	
// Creating method for OAUTH 2.0 access Token
	
	public String generateAccessTokenOauth2(String serviceUrl, String grantType, String clientId, String clientSecret)
	{
		RestAssured.baseURI="https://test.api.amadeus.com";
		String accessToken=
				given().log().all()
		.header("Content-Type","application/x-www-form-urlencoded")
		.formParam("grant_type",grantType)
		.formParam("client_id", clientId)
		.formParam("client_secret", clientSecret)
		.when()
		.post(serviceUrl)
		.then()
		.extract()
		.path("access_token");
		
	return accessToken;
		
	}


// HTTP Methods :	

	// 1. Methods for GET call

	public Response getMethodNoAuth(String serviceUrl, boolean log)
	{
		if (log) {
			return given().log().all().spec(createRequestSpecNoAuth()).when().get(serviceUrl);

		}

		return given().spec(createRequestSpecNoAuth()).when().get(serviceUrl);

	}
	
	/**
	 * Method with basic service URL only
	 * 
	 * @param serviceUrl
	 * @param log
	 * @return
	 */
	public Response getMethod(String serviceUrl,boolean includeAuth, boolean log) {
		if (log) {
			return given().log().all().spec(createRequestSpec(includeAuth)).when().get(serviceUrl);

		}

		return given().spec(createRequestSpec(includeAuth)).when().get(serviceUrl);
	}

	/**
	 * Method with basic service URL and content type
	 * 
	 * @param serviceUrl
	 * @param contentType
	 * @param log
	 * @return
	 */
	public Response getMethod(String serviceUrl, String contentType,boolean includeAuth, boolean log) {
		if (log) {
			return given().log().all().spec(createRequestSpec(contentType,includeAuth)).when().get(serviceUrl);

		}

		return given().spec(createRequestSpec(contentType, includeAuth)).when().get(serviceUrl);
	}

	/**
	 * Method with multiple or single header can also include content type as header
	 * and basic service Url
	 * 
	 * @param serviceUrl
	 * @param headersMap
	 * @param log
	 * @return
	 */
	public Response getMethod(String serviceUrl, Map<String, String> headersMap,boolean includeAuth, boolean log) {
		if (log) {
			return given().log().all().spec(createRequestSpec(headersMap, includeAuth)).when().get(serviceUrl);

		}

		return given().spec(createRequestSpec(headersMap, includeAuth)).when().get(serviceUrl);
	}

	/**
	 * 
	 * @param serviceUrl
	 * @param headersMap
	 * @param queryParams
	 * @param log
	 * @return
	 */
	public Response getMethod(String serviceUrl, Map<String, String> headersMap, Map<String, String> queryParams,
			boolean includeAuth, boolean log) {
		if (log) {
			return given().log().all().spec(createRequestSpec(headersMap, queryParams, includeAuth)).when().get(serviceUrl);

		}

		return given().spec(createRequestSpec(headersMap, queryParams, includeAuth)).when().get(serviceUrl);
	}

	// 2. Methods for POST call

	public Response postMethod(String serviceUrl, String contentType, Object body,boolean includeAuth, boolean log) {
		if (log) {
			return given().log().all().spec(createRequestSpec(body, contentType, includeAuth)).body(body).when().post(serviceUrl);

		}

		return given().spec(createRequestSpec(body, contentType, includeAuth)).body(body).when().post(serviceUrl);
	}

	public Response postMethod(String serviceUrl, Object body, Map<String, String> headersMap, boolean includeAuth,boolean log) {
		if (log) {
			return given().log().all().spec(createRequestSpec(body, headersMap, includeAuth)).body(body).when().post(serviceUrl);

		}

		return given().spec(createRequestSpec(body, headersMap, includeAuth)).body(body).when().post(serviceUrl);
	}

	// 3. Methods for PUT call

	public Response putMethod(String serviceUrl, String contentType, Object body,boolean includeAuth, boolean log) {
		if (log) {
			return given().log().all().spec(createRequestSpec(body, contentType, includeAuth)).body(body).when().post(serviceUrl);

		}

		return given().spec(createRequestSpec(body, contentType, includeAuth)).body(body).when().post(serviceUrl);
	}

	public Response putMethod(String serviceUrl, Object body, Map<String, String> headersMap,boolean includeAuth, boolean log) {
		if (log) {
			return given().log().all().spec(createRequestSpec(body, headersMap, includeAuth)).body(body).when().post(serviceUrl);

		}

		return given().spec(createRequestSpec(body, headersMap, includeAuth)).body(body).when().post(serviceUrl);
	}

	// 4. Methods for PATCH call

	public Response patchMethod(String serviceUrl, String contentType, Object body,boolean includeAuth, boolean log) {
		if (log) {
			return given().log().all().spec(createRequestSpec(body, contentType, includeAuth)).body(body).when().post(serviceUrl);

		}

		return given().spec(createRequestSpec(body, contentType, includeAuth)).body(body).when().post(serviceUrl);
	}

	public Response patchMethod(String serviceUrl, Object body, Map<String, String> headersMap,boolean includeAuth, boolean log) {
		if (log) {
			return given().log().all().spec(createRequestSpec(body, headersMap, includeAuth)).body(body).when().post(serviceUrl);

		}

		return given().spec(createRequestSpec(body, headersMap, includeAuth)).body(body).when().post(serviceUrl);
	}

	// 5. Methods for DELETE call

	public Response deleteMethod(String serviceUrl, String contentType,boolean includeAuth, boolean log) {

		if (log) {
			return given().log().all().spec(createRequestSpec(contentType, includeAuth)).when().delete(serviceUrl);

		}

		return given().spec(createRequestSpec(contentType, includeAuth)).when().delete(serviceUrl);

	}

}

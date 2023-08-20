package restJsonTests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.Test;

import apiEngine.model.AuthorizationRequest;

public class GenerateToken {
	
	@Test
	public void generateToken() {
		AuthorizationRequest authRequest = new AuthorizationRequest("TestUser3", "Test@1431" ) ;
		
		RestAssured.baseURI = "https://demoqa.com" ;
		RequestSpecification http_Request = RestAssured.given() ;
		
		http_Request.contentType(io.restassured.http.ContentType.JSON) ; 
		http_Request.body(authRequest);
		System.out.println("Http_Request: " + http_Request ) ;
		Response response = http_Request.post("/Account/v1/GenerateToken") ;
		
		
		String jsonString = response.asString() ;
		String token = JsonPath.from(jsonString).get( "token" ) ;
		System.out.println("Response code:" + response.getStatusCode() + "-- token is : " + token ) ;
		//9781449365035
	}

}

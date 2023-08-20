package restTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.json.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AuthorizedTest {
	
	@Test
	public void registrationSuccessful() {
		
		RestAssured.baseURI = "https://demoqa.com" ;
		RequestSpecification http_Request = RestAssured.given() ;
		
		JSONObject requestParams = new JSONObject() ;
		requestParams.put("userName", "TestUser3") ;
		requestParams.put("password", "Test@1431") ;
		
		http_Request.contentType(io.restassured.http.ContentType.JSON) ; 
		System.out.println("Request Body : " + requestParams.toString() ) ;
		Response response = http_Request.body(requestParams.toString()).post("/Account/v1/Authorized") ;
		
		System.out.println("Response : " + response.getBody().toString() ) ;
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
}

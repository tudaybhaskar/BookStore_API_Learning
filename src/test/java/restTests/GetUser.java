package restTests;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetUser {
	
	@Test
	public void getListOfBooks_Get() {
		
		RestAssured.baseURI = "https://demoqa.com" ;
		RequestSpecification http_Request =  RestAssured.given() ;
		
		http_Request.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6IlRlc3RVc2VyMyIsInBhc3N3b3JkIjoiVGVzdEAxNDMxIiwiaWF0IjoxNjkyMzI4MDk5fQ.w189ef3VshqOsq39gdOoPqf4K5mSyL7TyBYn6gh8IdM")
		.contentType(io.restassured.http.ContentType.JSON);
		
		String userID = "8e56b746-7261-4dfb-a7bc-d28c99514a16" ;
		
		Response response = http_Request.get("/Account/v1/User/" + userID + "") ;
		System.out.println("Status code : " + response.getStatusCode()) ;
		System.out.println("Response code : " + response.getStatusLine() ) ;
		System.out.println("Response code : " + response.getBody().asPrettyString() ) ;
	}

}

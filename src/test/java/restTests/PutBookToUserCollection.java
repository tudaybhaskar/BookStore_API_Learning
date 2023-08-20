package restTests;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PutBookToUserCollection {
	
	@Test
	public void Update_UserCollection_PUT() {
		
		RestAssured.baseURI = "https://demoqa.com" ;
		RequestSpecification http_Request = RestAssured.given() ;
		
		http_Request.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6IlRlc3RVc2VyMyIsInBhc3N3b3JkIjoiVGVzdEAxNDMxIiwiaWF0IjoxNjkxMzM4NzgzfQ.NHQujRGVBPVT5Od3ketf6L4GWcJ5yc2XAoTNBZKLRJM")
		.contentType(io.restassured.http.ContentType.JSON);
		String isbn = "9781491950296" ;
		String userID = "8e56b746-7261-4dfb-a7bc-d28c99514a16" ; 
		String requestBody = "{\n"
				+ "  \"userId\": \"" + userID + "\",\n"
				+ "  \"isbn\": \"" + isbn + "\"\n"
				+ "}" ;
		
		System.out.println("Request body as JSON String : " + requestBody) ;
		
		Response response = http_Request.body(requestBody)
				.put("/BookStore/v1/Books/9781449331818");
		
		System.out.println("Response code : " + response.getStatusCode() ) ;
		System.out.println("Response code : " + response.getStatusLine() ) ;
		
	}

}

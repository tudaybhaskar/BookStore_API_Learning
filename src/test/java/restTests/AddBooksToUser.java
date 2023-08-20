package restTests;

import org.testng.annotations.Test;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddBooksToUser {
	
	@Test
	public void sendPostRequestToAddBooks() {
		
		System.out.println("Testing API : ") ;
		
		RestAssured.baseURI = "https://demoqa.com" ;
		RequestSpecification httpRequest = RestAssured.given()
				.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6IlRlc3RVc2VyMyIsInBhc3"
						+ "N3b3JkIjoiVGVzdEAxNDMxIiwiaWF0IjoxNjkxOTg4NDQ3fQ.k07M0IOfHLfpheSgOoaJImSsk81n_OD_bN3ErFQi-_Y")
				.contentType(io.restassured.http.ContentType.JSON);
		
		String requestBody = "{\n"
				+ "  \"userId\": \"8e56b746-7261-4dfb-a7bc-d28c99514a16\",\n"
				+ "  \"collectionOfIsbns\": [\n"
				+ "    {\n"
				+ "      \"isbn\": \"9781449337711\"\n"
				+ "    }\n"
				+ "  ]\n"
				+ "}" ;
		
		/*//Creating a JSON object which has key value pairs
		JSONObject requestParams = new JSONObject() ;
		requestParams.put("userId", "8e56b746-7261-4dfb-a7bc-d28c99514a16") ;
		requestParams.put("isbn", "9781593277574") ;
		
		
		//header stating the request body is a json
		httpRequest.header("Content-Type", "application/json") ;
		httpRequest.body(requestParams.toString()) ;
		
		System.out.println("Header request : " + httpRequest.toString()) ;*/
		
		Response response = httpRequest.body(requestBody).post("/BookStore/v1/Books");
		System.out.println("The status received: " + response.getStatusCode());	
	}
	
}

package restJsonTests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;

import apiEngine.model.*;

public class AddBooksToUserCollection {
	
	private static String userId = "8e56b746-7261-4dfb-a7bc-d28c99514a16" ;
	private static final String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6IlRlc3RVc2VyMyIsInBhc3N3b3JkIjoiVGVzdEAxNDMxIiwiaWF0IjoxNjkyMDExMzk5fQ.6pHiE9FTopipUe7CLiCUk9Wj-LupovSru1oT6Iq4GL8" ;
	@Test
	public void addBookToUser() {
		
		ISBN isbn = new ISBN("9781449365035") ;
		AddBooksRequest addBooks = new AddBooksRequest(userId, isbn) ;
		
		RestAssured.baseURI = "https://demoqa.com" ;
		RequestSpecification httpRequest = RestAssured.given() ;
		
		httpRequest.header("Authorization", token) ;
		httpRequest.contentType(io.restassured.http.ContentType.JSON) ;
		
		Response response = httpRequest.body(addBooks).post("/BookStore/v1/Books") ;
		Assert.assertEquals(response.getStatusCode(), 201, "Book is not added to user collection");
		
		List<Map<String,String>> books = JsonPath.from(response.asString()).get("books") ;
		System.out.println("isbn value : " + books.get(0).get("isbn") );
	}

}

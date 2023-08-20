package stepDefinitions;

import org.json.JSONObject;
import org.testng.*;
import java.util.List;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BookStoreSteps {
	
	private static final String USER_ID = "8e56b746-7261-4dfb-a7bc-d28c99514a16" ;
	private static final String USERNAME = "TestUser3" ;
	private static final String PASSWORD = "Test@1431" ;
	private static final String BASE_URL = "https://demoqa.com" ;
	
	private static String token ;
	private static String isbn ;
	
	@Given("I am an Authorized user")
	public void i_am_an_authorized_user() {
		RestAssured.baseURI = BASE_URL ;
		RequestSpecification http_Request = RestAssured.given() ;
		
		http_Request.contentType(io.restassured.http.ContentType.JSON) ;
		JSONObject request_Params = new JSONObject() ;
		request_Params.put("userName", USERNAME ) ;
		request_Params.put("password", PASSWORD ) ;
		
		Response response = http_Request.body(request_Params.toString()).post("/Account/v1/GenerateToken") ;
		Assert.assertEquals( response.getStatusCode(), 200, "Token is not generated") ;
		JsonPath jsonPathEvaluator = response.getBody().jsonPath() ;
		token = jsonPathEvaluator.getString( "token" ) ;
		System.out.println("Toke is : " + token) ;
	}
	
	@Given("A list of books are available")
	public void a_list_of_books_are_available() {
	    // Write code here that turns the phrase above into concrete actions
		RestAssured.baseURI = BASE_URL ;
		RequestSpecification http_Request = RestAssured.given() ;
		
		Response response = http_Request.get( "/BookStore/v1/Books" ) ;
		Assert.assertEquals(response.getStatusCode(), 200, "Could not get the list of books") ;
		
		JsonPath jsonPath = response.jsonPath() ;
		List<String> books_isbn = jsonPath.getList("books.isbn") ;
		
		isbn = books_isbn.get(2);
		System.out.println("ISBN Value is : " + isbn ) ;	
	}

	@When("I add a book to my reading list")
	public void i_add_a_book_to_my_reading_list() {
	    // Write code here that turns the phrase above into concrete actions
		RestAssured.baseURI = BASE_URL ;
		RequestSpecification http_Request = RestAssured.given() ;
		
		http_Request.header("Authorization" , "Bearer " + token )
		.contentType(io.restassured.http.ContentType.JSON) ;
		
		String requestBody = "{\n"
				+ "  \"userId\": \""+USER_ID+"\",\n"
				+ "  \"collectionOfIsbns\": [\n"
				+ "    {\n"
				+ "      \"isbn\": \""+isbn+"\"\n"
				+ "    }\n"
				+ "  ]\n"
				+ "}" ;
		
		System.out.println("JSONString : " + requestBody ) ;
		
		Response response = http_Request.body(requestBody).post("/BookStore/v1/Books") ;
		Assert.assertEquals(response.getStatusCode(), 201, "Book with isbn: " + isbn + " is not added to user list") ;
	}

	@Then("the book is added")
	public void the_book_is_added() {
	    // Write code here that turns the phrase above into concrete actions
		RestAssured.baseURI = "https://demoqa.com" ;
		RequestSpecification http_Request =  RestAssured.given() ;
		
		http_Request.header("Authorization" , "Bearer " + token ) ;
		Response response = http_Request.get("/Account/v1/User/"+USER_ID ) ;
		
		Assert.assertEquals(response.getStatusCode(), 200, "Book with isbn: " +isbn+ "is not added to user list") ;
		
	}

	@When("I remove a book from my reading list")
	public void i_remove_a_book_from_my_reading_list() {
	    // Write code here that turns the phrase above into concrete actions
		RestAssured.baseURI = "https://demoqa.com" ;
		RequestSpecification http_Request =  RestAssured.given() ;
		
		http_Request.header("Authorization" , "Bearer " + token )
		.contentType(io.restassured.http.ContentType.JSON);
		JSONObject request_Params = new JSONObject() ;
		request_Params.put("isbn", isbn ) ;
		request_Params.put("userId", USER_ID ) ;
		
		Response response = http_Request.body(request_Params.toString()).delete( "/BookStore/v1/Book" ) ;
		Assert.assertEquals(response.getStatusCode(), 204, "Book with isbn: " +isbn+ "is not deleted from the user list") ;
	}

	@Then("the book is removed")
	public void the_book_is_removed() {
	    // Write code here that turns the phrase above into concrete actions
		RestAssured.baseURI = "https://demoqa.com" ;
		RequestSpecification http_Request =  RestAssured.given() ;
		
		http_Request.header("Authorization" , "Bearer " + token ) ;
		Response response = http_Request.get("/Account/v1/User/"+USER_ID ) ;
		
		Assert.assertEquals(response.getStatusCode(), 200, "Book with isbn: " +isbn+ "is still present in te user list") ;
		
	}

}

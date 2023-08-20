package restTests;

import java.util.List;

import org.testng.annotations.Test;

import apiEngine.model.Book;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetBooksList {
	
	@Test
	public void getListOfBooks_GET() {
		
		RestAssured.baseURI = "https://demoqa.com" ;
		RequestSpecification http_Request = RestAssured.given() ;
		
		Response response = http_Request.get("/BookStore/v1/Books") ;
		
		JsonPath jsonPath = new JsonPath( response.asString() ) ;
		System.out.println("Status Code : " + response.getStatusCode()) ;
		System.out.println("Response Body as String: " + response.body().asPrettyString() ) ;
		
		System.out.println("Response Jsonpath to find publisher: " +  jsonPath.get("books.publisher") ) ;
		
		JsonPath jsonPathEvaluator =  response.jsonPath() ;
		
		List<String> allBooks = jsonPathEvaluator.getList("books.title") ;
		
		for( String book: allBooks ) {
			System.out.println(" Book : " + book ) ;
		}
		
	}
	
	@Test(enabled=false)
	public void getListOfBooks_Get_Books() {
		
		RestAssured.baseURI = "https://demoqa.com" ;
		RequestSpecification http_Request = RestAssured.given() ;
		
		Response response = http_Request.get("/BookStore/v1/Books") ;
		System.out.println(" Getting List of Books of Book Type") ;
		System.out.println("Status Code : " + response.getStatusCode()) ;
		
		JsonPath jsonPathEvaluator =  response.jsonPath() ;
		
		List<Book> allBooks = jsonPathEvaluator.getList("books", Book.class) ;
		
		for( Book book: allBooks ) {
			System.out.println(" Book : " + book.title ) ;
		}
	}

}

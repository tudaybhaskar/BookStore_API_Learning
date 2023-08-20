package stepDefinitions;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import apiEngine.Endpoints;
import apiEngine.model.AddBooksRequest;
import apiEngine.model.AuthorizationRequest;
import apiEngine.model.Book;
import apiEngine.model.Books;
import apiEngine.model.DeleteBookRequest;
import apiEngine.model.ISBN;
import apiEngine.model.Token;
import apiEngine.model.UserAccount;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BookStore_Framework_E2E {
	
	private static final String USER_ID = "8e56b746-7261-4dfb-a7bc-d28c99514a16" ;
	private static final String BASE_URL = "https://demoqa.com" ;
	private static String token ;
	private static Book add_Book_ID;
	private static ISBN isbn_book ;
	private static String isbn_add;
	
	private static Response response;
	private static Token tokenResponse;
	private static Book book;
	private static Books List_books;
	private static UserAccount userAccount;
	
	@Given("TestUser is Authorized")
	public void testuser_is_authorized() {
	    // Write code here that turns the phrase above into concrete actions
		AuthorizationRequest authRequest = new AuthorizationRequest("TestUser3", "Test@1431" ) ;
		
		response = Endpoints.authenticateUser(authRequest) ;
		Assert.assertEquals(response.getStatusCode(), 200, "Token is not generated " ) ;
		//String jsonString = response.asString() ;
		//token = JsonPath.from(jsonString).get( "token" ) ;
		token = response.getBody().as(Token.class).token ;
		
		response = Endpoints.authorizeUser( authRequest ) ;
		Assert.assertEquals(response.getStatusCode(), 200, "Not Authorized " ) ;
	}

	@Given("A collection of Testbooks are available")
	public void a_collection_of_testbooks_are_available() {
	    // Write code here that turns the phrase above into concrete actions
		response = Endpoints.getBooks() ;
		
		Assert.assertEquals(response.getStatusCode(), 200, "List of Books is not retrieved " ) ;
		
		List_books = response.getBody().as(Books.class) ;
		add_Book_ID = List_books.books.get(4) ;
		isbn_add = add_Book_ID.isbn ;
	}

	@When("I add a book to my TestUser collection")
	public void i_add_a_book_to_my_test_user_collection() {
	    // Write code here that turns the phrase above into concrete actions  
		AddBooksRequest addBookToUserList = new AddBooksRequest(USER_ID, new ISBN( isbn_add ) ) ;
		
		response = Endpoints.addBooks( addBookToUserList, token ) ;
		Assert.assertEquals(response.getStatusCode(), 201, "Book with isbn : " + isbn_add + " is not added" ) ;
		
	}

	@Then("the book is added to TestUser collection")
	public void the_book_is_added_to_test_user_collection() {
	    // Write code here that turns the phrase above into concrete actions
		response = Endpoints.getUserAccount( USER_ID, token ) ;
		Assert.assertEquals(response.getStatusCode(), 200, "Unable to get user Books List" ) ;
		
		//Verifying using POJO classes
		userAccount = response.getBody().as(UserAccount.class) ;
		
		//verify for Books isbn
		List<Book> li = userAccount.books ;
		boolean book_added = false ;
		for( Book map_book :  li ) {
			if(map_book.isbn.contains( isbn_add )) {
				book_added = true ;
				break ;
			}
		}
		Assert.assertEquals(book_added, true, " Book with isbn " + isbn_add + " is not added" ) ;
		//verify for USER_ID
		Assert.assertEquals(USER_ID, userAccount.userId, "User_ID did not match ");
		
		// Verifying without using POJO classes
		List<Map<String,Integer>> books = JsonPath.from(response.asString()).get( "books" ) ;

		boolean b_isbn = false ;
		if(books.size() != 0 ) {
			
			for( Map<String,Integer> map : books ) {
				
				for( Map.Entry<String, Integer> entry : map.entrySet() ) {
					//String isbn = entry.getKey() ;
					String isbn_value = String.valueOf( entry.getValue() ) ;
					if(isbn_value.contains(isbn_add) ) {
						b_isbn = true ;
						break;
					}
				}
			}
		}
		Assert.assertEquals(b_isbn, true, " Book with isbn " + isbn_add + " is not added" ) ;
	}


	@When("I remove a book from my TestUser collection")
	public void i_remove_a_book_from_my_test_user_collection() {
	    // Write code here that turns the phrase above into concrete actions
	    DeleteBookRequest del_BookRequest = new DeleteBookRequest(isbn_add, USER_ID) ;
	    
	    response = Endpoints.removeBook( del_BookRequest, token ) ;
		Assert.assertEquals(response.getStatusCode(), 204, "Book is not deleted " ) ;
	}

	@Then("the book is removed from my TestUser collection")
	public void the_book_is_removed_from_my_test_user_collection() {
	    // Write code here that turns the phrase above into concrete actions
		response = Endpoints.getUserAccount( USER_ID, token ) ;
		Assert.assertEquals(response.getStatusCode(), 200, "Unable to get user Books List" ) ;
		
		//Verifying using POJO classes
		userAccount = response.getBody().as(UserAccount.class) ;
				
		//verify for Books isbn
		List<Book> li = userAccount.books ;
		boolean book_added = false ;				
		for( Book map_book :  li ) {
			if(map_book.isbn.contains( isbn_add )) {
				book_added = true ;
				break ;
			}
		}
		Assert.assertEquals(book_added, false, " Book with isbn " + isbn_add + " is not added" ) ;
		//verify for USER_ID
		Assert.assertEquals(USER_ID, userAccount.userId, "User_ID did not match ");
				
		// Verifying without using POJO classes
		List<Map<String,Integer>> books = JsonPath.from(response.asString()).get( "books" ) ;

		boolean b_isbn = false ;
		if(books.size() != 0 ) {
					
			for( Map<String,Integer> map : books ) {
						
				for( Map.Entry<String, Integer> entry : map.entrySet() ) {
					//String isbn = entry.getKey() ;
					String isbn_value = String.valueOf( entry.getValue() ) ;
					if(isbn_value.contains(isbn_add) ) {
						b_isbn = true ;
						break;
					}
				}
			}
		}
		Assert.assertEquals(b_isbn, false, " Book with isbn " + isbn_add + " is present in user collection" ) ;
		
	}


}

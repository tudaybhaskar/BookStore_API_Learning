package stepDefinitions;

import apiEngine.model.AuthorizationRequest;
import apiEngine.model.Book;
import apiEngine.model.DeleteBookRequest;
import apiEngine.model.AddBooksRequest;
import apiEngine.model.Books;
import apiEngine.model.ISBN;
import apiEngine.model.UserAccount;

import java.util.List;

import org.testng.Assert;

import apiEngine.EndPoints_InstanceMethods;
import apiEngine.IRestResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class BookStore_InstanceMethods_E2E {
	
	private static final String BASE_URL = "https://demoqa.com" ;
	private static final String USER_ID = "8e56b746-7261-4dfb-a7bc-d28c99514a16" ;
	private EndPoints_InstanceMethods endpoints ;
	private static IRestResponse<Books> books_Response; 
	private static String book_Isbn ;
	private static UserAccount userAccount ;
	
	
	@Given("user gives username and password and generates a token")
	public void user_gives_username_and_password_and_generates_a_token() {
	
		endpoints = new EndPoints_InstanceMethods(BASE_URL) ;
		AuthorizationRequest authRequest = new AuthorizationRequest( "TestUser3", "Test@1431" ) ;
		endpoints.authenticateUser(authRequest);
		endpoints.authorizeUser(authRequest);
	}

	@Given("A collectionList of books")
	public void a_collection_list_of_books() {
	   
		books_Response = endpoints.getBooks() ;
		book_Isbn = books_Response.getBody().books.get(4).isbn ;
	}

	@When("authUser adds a book")
	public void auth_user_adds_a_book() {
	    
		AddBooksRequest add_BookRequest = new AddBooksRequest( USER_ID, new ISBN(book_Isbn));
		IRestResponse<Books> userBooks = endpoints.addBooks(add_BookRequest) ;
	}

	@Then("the book is added book Library")
	public void the_book_is_added_book_library() {
	    // Write code here that turns the phrase above into concrete actions
	    IRestResponse<UserAccount> acc_Response = endpoints.getUserAccount(USER_ID) ;
	    
	  Assert.assertEquals(acc_Response.getStatusCode(), 201, " Book is not added");
	    
	    userAccount = acc_Response.getBody();
		
		//verify for Books isbn
		List<Book> li = userAccount.books ;
		boolean book_added = false ;
		for( Book map_book :  li ) {
			if(map_book.isbn.contains( book_Isbn )) {
				book_added = true ;
				break ;
			}
		}
		
		Assert.assertEquals(book_added, true, " Book with isbn " + book_Isbn + " is not added" ) ;
	}

	@When("the auth user removes the book")
	public void the_auth_user_removes_the_book() {
	  
		DeleteBookRequest delBook_Request = new DeleteBookRequest(book_Isbn, USER_ID) ;
		endpoints.removeBook(delBook_Request) ;
	}

	@Then("the book is removed from library")
	public void the_book_is_removed_from_library() {
	    
		IRestResponse<UserAccount> acc_Response = endpoints.getUserAccount(USER_ID) ;
	    
	    userAccount = acc_Response.getBody();
		
		//verify for Books isbn
		List<Book> li = userAccount.books ;
		boolean book_added = false ;
		if(li.size() != 0) {
			for( Book map_book :  li ) {
				if(map_book.isbn.contains( book_Isbn )) {
					book_added = true ;
					break ;
				}
			}
		}
		
		Assert.assertEquals(book_added, false, " Book with isbn " + book_Isbn + " is still present in user collection " ) ;
	}
}

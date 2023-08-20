package stepDefinitions;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import apiEngine.EndPoints_Generics;
import apiEngine.Endpoints;
import apiEngine.IRestResponse;
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
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class BookStore_Generics_E2E {
	
	private static final String USER_ID = "8e56b746-7261-4dfb-a7bc-d28c99514a16" ;
	private static String token ;
	private static Book add_Book_ID;
	private static ISBN isbn_book ;
	private static String isbn_add;
	
	private static Response response;
	private static IRestResponse<Token> tokenResponse;
	private static Book book;
	private static Books List_books;
	private static UserAccount userAccount;
	
	@Given("User is Generic and is Authorized")
	public void user_is_generic_and_is_authorized() {
	    
		AuthorizationRequest authRequest = new AuthorizationRequest("TestUser3", "Test@1431" ) ;
		tokenResponse = EndPoints_Generics.authenticateUser(authRequest) ;
		response = EndPoints_Generics.authorizeUser( authRequest ) ;
	}

	@Given("A list of books are available for GenericUser")
	public void a_list_of_books_are_available_for_generic_user() {
	    
		IRestResponse<Books> books_response = EndPoints_Generics.getBooks() ;
		isbn_add = books_response.getBody().books.get(5).isbn ;
		
	}

	@When("GenericUser adds a book to his collection")
	public void generic_user_adds_a_book_to_his_collection() {
	     
		AddBooksRequest addBookToUserList = new AddBooksRequest(USER_ID, new ISBN( isbn_add ) ) ;
		IRestResponse<Books> books_response = EndPoints_Generics.addBooks( addBookToUserList, tokenResponse.getBody().token ) ;
	}

	@Then("the book is added to GenericUser collection of books")
	public void the_book_is_added_to_generic_user_collection_of_books() {
		
		IRestResponse<UserAccount> acc_response = EndPoints_Generics.getUserAccount(USER_ID, tokenResponse.getBody().token) ;
		
		//Verifying using POJO classes
		userAccount = acc_response.getBody() ;
		
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
	}


	@When("GenericUser removes a book from his collection")
	public void generic_user_removes_a_book_from_his_collection() {
	    // Write code here that turns the phrase above into concrete actions
	    DeleteBookRequest del_BookRequest = new DeleteBookRequest(isbn_add, USER_ID) ;
	    response = EndPoints_Generics.removeBook(del_BookRequest, tokenResponse.getBody().token )  ;
	}

	@Then("the book is removed from his collection")
	public void the_book_is_removed_from_his_collection() {
	    // Write code here that turns the phrase above into concrete actions
		IRestResponse<UserAccount> acc_response = EndPoints_Generics.getUserAccount(USER_ID, tokenResponse.getBody().token+1 ) ;
		boolean book_added = false ;
		if( acc_response.getStatusCode()== 401 ) {
			System.out.println("User is not authorized to perform Get Request - Implemented Generics ") ;
		}else if(acc_response.getStatusCode() == 204 ) {
		
		//Verifying using POJO classes
			userAccount = acc_response.getBody() ;
					
			//verify for Books isbn
			List<Book> li = userAccount.books ;
							
			for( Book map_book :  li ) {
				if(map_book.isbn.contains( isbn_add )) {
					book_added = true ;
					break ;
				}
			}
		}
		Assert.assertEquals(book_added, false, " Book with isbn " + isbn_add + " is still not deleted from user list " ) ;
		
	}


}

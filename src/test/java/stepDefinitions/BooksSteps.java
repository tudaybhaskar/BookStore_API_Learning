package stepDefinitions;

import org.testng.Assert;

import apiEngine.IRestResponse;
import apiEngine.model.Book;
import apiEngine.model.ISBN;
import apiEngine.model.Books;
import apiEngine.model.AddBooksRequest;
import apiEngine.model.DeleteBookRequest;
import apiEngine.model.UserAccount;
import cucumberHelper.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class BooksSteps extends BaseStep {

	public BooksSteps(TestContext testContext) {
		super(testContext);
		// TODO Auto-generated constructor stub
	}
	
	private final String USER_ID = "8e56b746-7261-4dfb-a7bc-d28c99514a16" ;
	private Response response;
	private IRestResponse<UserAccount> userAccountResponse;
	private IRestResponse<Books> add_booksResponse;
	private Book book;
	
	@Given("A list of books are available")
	public void listOfBooksAreAvailable() {
		IRestResponse<Books> booksResponse = getEndPoints().getBooks() ;
		book = booksResponse.getBody().books.get(4);
	}
	
	@When("I add a book to my collection")
	public void addBookInList() {
		ISBN isbn = new ISBN(book.isbn);
		AddBooksRequest addBook_request = new AddBooksRequest(USER_ID, isbn) ;
		add_booksResponse = getEndPoints().addBooks(addBook_request) ;
	}
	
	@Then("the book is added to collection")
	public void bookIsAddedtoUserList() {
		Assert.assertEquals(201, add_booksResponse.getStatusCode() );
		
		userAccountResponse = getEndPoints().getUserAccount(USER_ID) ;
	
		Assert.assertTrue(userAccountResponse.isSuccessful());
		Assert.assertEquals(USER_ID, userAccountResponse.getBody().userId );
		Assert.assertEquals(book.isbn, userAccountResponse.getBody().books.get(0).isbn);
	}
	
	@When("I remove a book from my collection")
	public void removeBookFromList() {
		DeleteBookRequest delBookRequest = new DeleteBookRequest(book.isbn, USER_ID) ;
		response = getEndPoints().removeBook(delBookRequest) ;
	}
	
	@Then("the book is removed from my collection")
	public void bookIsRemoved() {
		
		Assert.assertEquals(204, response.getStatusCode()) ;
		
		userAccountResponse = getEndPoints().getUserAccount(USER_ID) ;
		Assert.assertEquals( 200, userAccountResponse.getStatusCode() );
		
		Assert.assertEquals(0, userAccountResponse.getBody().books.size() );
	}

}

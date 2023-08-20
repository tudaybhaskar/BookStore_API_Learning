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
import enums.Context;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class BooksSteps extends BaseStep {

	public BooksSteps(TestContext testContext) {
		super(testContext);
		// TODO Auto-generated constructor stub
	}
	
	private Response response;
	private IRestResponse<UserAccount> userAccountResponse;
	private IRestResponse<Books> add_booksResponse;
	
	
	@Given("A list of books are available")
	public void listOfBooksAreAvailable() {
		IRestResponse<Books> booksResponse = getEndPoints().getBooks() ;
		Book book = booksResponse.getBody().books.get(4);
		getScenarioContext().setContext(Context.BOOK, book);
	}
	
	@When("I add a book to my collection")
	public void addBookInList() {
		Book book = (Book) getScenarioContext().getContext(Context.BOOK) ;
		String USER_ID = (String) getScenarioContext().getContext(Context.USER_ID) ;
		
		ISBN isbn = new ISBN(book.isbn);
		AddBooksRequest addBook_request = new AddBooksRequest(USER_ID, isbn) ;
		
		add_booksResponse = getEndPoints().addBooks(addBook_request) ;
		getScenarioContext().setContext(Context.ADD_BOOK_RESPONSE, add_booksResponse);
	}
	
	@When("I remove a book from my collection")
	public void removeBookFromList() {
		Book book = (Book) getScenarioContext().getContext(Context.BOOK) ;
		String USER_ID = (String) getScenarioContext().getContext(Context.USER_ID) ;
		
		DeleteBookRequest delBookRequest = new DeleteBookRequest(book.isbn, USER_ID) ;
		response = getEndPoints().removeBook(delBookRequest) ;
		getScenarioContext().setContext(Context.BOOK_REMOVE_RESPONSE, response);
	}
	

}

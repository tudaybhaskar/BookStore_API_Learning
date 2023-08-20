package stepDefinitions;

import org.testng.Assert;

import apiEngine.model.Book;
import cucumberHelper.TestContext;
import enums.Context;
import io.cucumber.java.en.Then;
import apiEngine.IRestResponse;
import apiEngine.model.UserAccount;
import io.restassured.response.Response;

public class VerificationSteps extends BaseStep {
	
	private IRestResponse<UserAccount> userAccountResponse;
	
	public VerificationSteps(TestContext testContext) {
		super(testContext);
		// TODO Auto-generated constructor stub
	}
	
	@Then("the book is added to collection")
	public void bookIsAddedtoUserList() {
		String USER_ID = (String) getScenarioContext().getContext(Context.USER_ID) ;
		Book book = (Book) getScenarioContext().getContext(Context.BOOK) ;
		
		userAccountResponse = getEndPoints().getUserAccount(USER_ID) ;
		getScenarioContext().setContext(Context.USER_ACCOUNT_RESPONSE, userAccountResponse);
	
		Assert.assertTrue( userAccountResponse.isSuccessful() );
		Assert.assertEquals( 200, userAccountResponse.getStatusCode() );
		Assert.assertEquals( USER_ID, userAccountResponse.getBody().userId );
		Assert.assertEquals( book.isbn, userAccountResponse.getBody().books.get(0).isbn );
	}
	
	@Then("the book is removed from my collection")
	public void bookIsRemoved() {
		String USER_ID = (String) getScenarioContext().getContext(Context.USER_ID) ;
		
		Response response = (Response) getScenarioContext().getContext( Context.BOOK_REMOVE_RESPONSE ) ;
		
		Assert.assertEquals(204, response.getStatusCode()) ;
		
		userAccountResponse = getEndPoints().getUserAccount(USER_ID) ;
		
		Assert.assertEquals( 200, userAccountResponse.getStatusCode() );
		Assert.assertEquals(0, userAccountResponse.getBody().books.size() );
	}

}

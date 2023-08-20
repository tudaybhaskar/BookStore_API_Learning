package stepDefinitions;

import apiEngine.model.AuthorizationRequest;
import cucumberHelper.TestContext;
import io.cucumber.java.en.Given;

public class AccountSteps extends BaseStep {

	public AccountSteps(TestContext testContext) {
		super(testContext);
		// TODO Auto-generated constructor stub
	}
	
	@Given("I am an User and is Authorized")
	public void iAmAnAuthorizedUser(){
		
		AuthorizationRequest authRequest = new AuthorizationRequest("TestUser3", "Test@1431" ) ;
		getEndPoints().authenticateUser(authRequest);
		
	}
	
}

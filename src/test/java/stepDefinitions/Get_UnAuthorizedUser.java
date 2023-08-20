package stepDefinitions;

import apiEngine.model.AuthorizationRequest;

import org.testng.Assert;

import apiEngine.EndPoints_Generics;
import apiEngine.IRestResponse;
import apiEngine.model.Token;
import apiEngine.model.UserAccount;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Get_UnAuthorizedUser {
	
	private static final String USER_ID = "8e56b746-7261-4dfb-a7bc-d28c99514a16" ;
	private static IRestResponse<Token> tokenResponse;
	private static IRestResponse<UserAccount> acc_Response ;
	private static String token;
	
	@Given("user provides username and password and generates a token")
	public void user_provides_username_and_password_and_generates_a_token() {
	    // Write code here that turns the phrase above into concrete actions
		AuthorizationRequest authRequest = new AuthorizationRequest( "TestUser3", "Test@1431" ) ;
		tokenResponse = EndPoints_Generics.authenticateUser(authRequest) ;
		token = tokenResponse.getBody().token ;
	}

	@When("user tried to access his account")
	public void user_tried_to_access_his_account() {
	    // Write code here that turns the phrase above into concrete actions
		acc_Response = EndPoints_Generics.getUserAccount(USER_ID, token) ;
	}

	@Then("user will not get success response")
	public void user_will_not_get_success_response() {
	    // Write code here that turns the phrase above into concrete actions
	    	Assert.assertEquals(acc_Response.getStatusCode(), 401, "Not expected Response");
	    
	}

}

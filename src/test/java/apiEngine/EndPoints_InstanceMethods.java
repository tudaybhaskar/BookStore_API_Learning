package apiEngine;

import org.apache.http.HttpStatus;

import apiEngine.model.AddBooksRequest;
import apiEngine.model.AuthorizationRequest;
import apiEngine.model.Books;
import apiEngine.model.DeleteBookRequest;
import apiEngine.model.Route;
import apiEngine.model.Token;
import apiEngine.model.UserAccount;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndPoints_InstanceMethods {
	
	private final RequestSpecification request ;
	private String token;
	
	public EndPoints_InstanceMethods( String baseUrl ) {
		
		RestAssured.baseURI = baseUrl ;
		request = RestAssured.given() ;
		request.contentType( io.restassured.http.ContentType.JSON ) ;
	}
	
	public void authenticateUser(AuthorizationRequest authRequest) {
		
		Response response = request.auth().none().body(authRequest).post( Route.generateToken() ) ;
		if ( response.getStatusCode() != HttpStatus.SC_OK ) {
			throw new RuntimeException(" Authentication Failed. Content of Failed Response: " + 
					response.toString() + " Status Code : " + response.getStatusCode() ) ;
		}
		
		Token tokenResponse = response.getBody().jsonPath().getObject("$", Token.class) ;
		token = tokenResponse.token ;
		System.out.println("token: " + tokenResponse.token);
		request.header("Authorization", "Bearer " + tokenResponse.token ) ;
		System.out.println("Request set : " + request.auth().toString() ) ;
	}

	public void authorizeUser(AuthorizationRequest authRequest) {
		
		Response response = request.auth().none().post( Route.authorize() ) ;
		if(response.getStatusCode() != HttpStatus.SC_OK ) {
			throw new RuntimeException( "Authorization is failed. Content of failed response: " +
					response.toString() + " status code: " + response.getStatusCode() ) ;
		}
	}

	public IRestResponse<Books> getBooks() {
		
		Response response = request.auth().none().get( Route.books() ) ;
		return new RestResponse<Books>( Books.class , response );

	}
	
	public IRestResponse<Books> addBooks( AddBooksRequest addBookRequest ) {
		
		Response response = request.header("Authorization", "Bearer " + token ).body(addBookRequest).post( Route.books() ) ;
		return new RestResponse<Books>(Books.class , response );
	}
	
	public Response removeBook( DeleteBookRequest del_BookRequest ) {
		
		Response response = request.body(del_BookRequest).delete( Route.book() ) ;
		return response;	
	}
	
	public IRestResponse<UserAccount> getUserAccount( String userId ) {
		
		Response response = request.header("Authorization", "Bearer " + token ).get( Route.userAccount(userId) ) ;
		return new RestResponse<UserAccount>( UserAccount.class, response ) ;
	}
	

}

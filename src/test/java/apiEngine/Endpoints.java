package apiEngine;

import apiEngine.model.AddBooksRequest;
import apiEngine.model.AuthorizationRequest;
import apiEngine.model.DeleteBookRequest;
import apiEngine.model.Route;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Endpoints {
	
	private static final String BASE_URL = "https://demoqa.com" ;
	
	public static Response authenticateUser(AuthorizationRequest authRequest) {
		
		RestAssured.baseURI = BASE_URL ;
		RequestSpecification http_Request = RestAssured.given() ;
		
		http_Request.contentType(io.restassured.http.ContentType.JSON) ;
		
		Response response = http_Request.body(authRequest).post( Route.generateToken() ) ;
		return response;
	}
	
	public static Response authorizeUser(AuthorizationRequest authRequest) {
		
		RestAssured.baseURI = BASE_URL ;
		RequestSpecification http_Request = RestAssured.given() ;
		
		http_Request.contentType(io.restassured.http.ContentType.JSON) ;
		http_Request.body( authRequest ) ;
		
		Response response = http_Request.post( Route.authorize() ) ;
		return response;	
	}
	
	public static Response getBooks() {
		
		RestAssured.baseURI = BASE_URL ;
		RequestSpecification http_Request = RestAssured.given() ;
		
		Response response = http_Request.get( "/BookStore/v1/Books" ) ;
		return response;
	}
	
	public static Response addBooks(AddBooksRequest addBookRequest, String token) {
		RestAssured.baseURI = BASE_URL ;
		RequestSpecification http_Request = RestAssured.given() ;
		
		http_Request.contentType(io.restassured.http.ContentType.JSON) ; 
		http_Request.header("Authorization", "Bearer " + token ) ;
		http_Request.body(addBookRequest) ;
		
		Response response = http_Request.post( Route.books() ) ;
		return response;
	}
	
	public static Response removeBook(DeleteBookRequest del_BookRequest, String token) {
		
		RestAssured.baseURI = BASE_URL ;
		RequestSpecification http_Request = RestAssured.given() ;
		
		http_Request.contentType(io.restassured.http.ContentType.JSON) ; 
		http_Request.header("Authorization", "Bearer " + token ) ;
		http_Request.body(del_BookRequest) ;
		
		Response response = http_Request.delete( Route.book() ) ;
		return response;	
	}
	
	public static Response getUserAccount(String userId, String token) {
		
		RestAssured.baseURI = "https://demoqa.com" ;
		RequestSpecification http_Request = RestAssured.given() ;
		
		http_Request.header("Authorization", "Bearer " + token ) ;
		
		Response response = http_Request.get( Route.userAccount( userId ) ) ;
		return response ;
	}

}

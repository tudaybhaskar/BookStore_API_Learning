Feature: Test using the instance methods

Background: Generate token for a test user
	Given user gives username and password and generates a token
	
Scenario: The authorized user adding books and deleting from his collection
	Given A collectionList of books
    When authUser adds a book
    Then the book is added book Library
    When the auth user removes the book
    Then the book is removed from library


Feature: Cucumber E2E BookStore API functionality
	Background: User generates token for Authorisation
	Given User is Generic and is Authorized
  @NotRun
  Scenario: An Authorized GenericUser can Add and Remove to his collection
    Given A list of books are available for GenericUser
    When GenericUser adds a book to his collection
    Then the book is added to GenericUser collection of books
    When GenericUser removes a book from his collection
    Then the book is removed from his collection
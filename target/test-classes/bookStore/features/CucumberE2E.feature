
Feature: Cucumber E2E BookStore API functionality
	Background: User generates token for Authorisation
	Given User is Authorized
  @NotRun
  Scenario: An Authorized user can Add and Remove to his collection
    Given A collection of books are available
    When I add a book to my collection
    Then the book is added to collection
    When I remove a book from my collection
    Then the book is removed from my collection
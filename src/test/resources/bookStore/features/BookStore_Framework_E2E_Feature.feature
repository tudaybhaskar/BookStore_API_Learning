Feature: Cucumber Framework E2E BookStore API functionality
	Background: TestUser generates token for Authorisation
	Given TestUser is Autorized
  @NotRun
  Scenario: An Authorized Testuser can Add and Remove to his collection
    Given A collection of Testbooks are available
    When I add a book to my TestUser collection
    Then the book is added to TestUser collection
    When I remove a book from my TestUser collection
    Then the book is removed from my TestUser collection
Feature: An Authorized user cannot access get operation.
@NotRun
Scenario: An Authorized user cannot access his own account
	Given user provides username and password and generates a token
	When user tried to access his account
	Then user will not get success response
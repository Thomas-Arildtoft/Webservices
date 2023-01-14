Feature: Register customer success scenario

  Background:
    Given : [RegisterCustomer] Customer "Astrid" "Olesen" with cpr "090643-0088" with an account balance of 100 exists

  Scenario: Register customer successfully
    Given Customer's account id
    When the customer requests for registration
    Then the registration finishes successfully and registered customer is returned
Feature: Register customer scenarios

  Background:
    Given : [RegisterCustomer] Customer "Astrid" "Olesen" with cpr "090643-0088" with an account balance of 100 exists

  Scenario: Register customer successfully
    Given Customer's account id
    When the customer requests for registration
    Then the registration finishes successfully and registered customer is returned

  Scenario: Register already registered customer
    Given Already registered customer
    When the customer requests for registration
    Then customer registration finishes with failure and the following message is returned "Account already used"
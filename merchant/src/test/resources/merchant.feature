Feature: Merchant management

  Scenario: Merchant registration
    Given : Merchant has bank account id 123456789
    When : Register merchant at DTU Pay
    Then : User with role Merchant and bank account id 123456789 is returned
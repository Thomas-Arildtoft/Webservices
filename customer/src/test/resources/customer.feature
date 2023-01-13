Feature: Customer

  Scenario: Customer registration
    Given : Customer has bank account id 123456789
    When : Register merchant at DTU Pay
    Then : User with role Customer and bank account id 123456789 is returned

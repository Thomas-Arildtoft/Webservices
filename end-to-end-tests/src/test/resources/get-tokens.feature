Feature: Get tokens scenario

  Background:
    Given : [GetToken] Customer "Signe" "Østergaard" with cpr "050767-4062" with an account balance of 100 exists

  Scenario: Get tokens successfully
    Given : Registered customer at DTUPay
    When : Customer requests for 5 tokens
    Then : Customer receive 5 tokens
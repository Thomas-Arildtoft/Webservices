Feature: Get tokens scenario

  Background:
    Given : [GetToken] Customer "Signe" "Østergaard" with cpr "050767-4062" with an account balance of 100 exists

  Scenario: Get tokens successfully
    Given : Registered customer at DTUPay
    When : Customer requests for 5 tokens
    Then : Customer receive 5 tokens

  Scenario: Get tokens failure
    Given : Registered customer at DTUPay
    When : Customer requests for 9 tokens
    Then : Receive failure message that starts with "Incorrect number of tokens request"

  Scenario: Get tokens failure
    Given : Registered customer at DTUPay
    When : Customer requests for -1 tokens
    Then : Receive failure message that starts with "Incorrect number of tokens request"
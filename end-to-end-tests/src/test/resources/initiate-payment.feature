Feature: Initiate payment scenario

  Background:
    Given : [InitiatePayment] Customer "Signe" "Østergaard" with cpr "050767-4062" with an account balance of 100 exists
    And : [InitiatePayment] Merchant "Mimir" "Rasmussen" with cpr "230356-1531" with an account balance of 100 exists

  Scenario: Initiate payment successfully
    Given : Customer and merchant are registered
    When : Payment of 1 kr is initiated
    Then : 1 kr where successfully transferred

  Scenario: Initiate payment failed
    Given : Customer and merchant are registered
    When : Payment with wrong token of 1 kr is initiated
    Then : Message "Invalid token" is returned

  Scenario: Initiate payment failed
    Given : Customer is registered
    When : Payment of 1 kr is initiated
    Then : Message "No merchant registered" is returned
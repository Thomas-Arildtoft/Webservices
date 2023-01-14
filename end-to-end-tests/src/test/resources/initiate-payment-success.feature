Feature: Initiate payment scenario

  Background:
    Given : [InitiatePayment] Customer "Signe" "Østergaard" with cpr "050767-4062" with an account balance of 100 exists
    And : [InitiatePayment] Merchant "Mimir" "Rasmussen" with cpr "230356-1531" with an account balance of 100 exists

  Scenario: Get tokens successfully
    Given : Customer and merchant are registered
    When : Payment is of 1 kr is initiated
    Then : 1 kr where successfully transferred
Feature: Register merchant success scenario

  Background:
    Given : [RegisterMerchant] Merchant "Mimir" "Rasmussen" with cpr "230356-1531" with an account balance of 100 exists

  Scenario: Register merchant successfully
    Given Merchant's account id
    When the merchant requests for registration
    Then the registration finishes successfully and registered merchant is returned
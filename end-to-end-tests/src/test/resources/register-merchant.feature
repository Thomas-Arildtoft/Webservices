Feature: Register merchant scenarios

  Background:
    Given : [RegisterMerchant] Merchant "Mimir" "Rasmussen" with cpr "230356-1531" with an account balance of 100 exists

  Scenario: Register merchant successfully
    Given Merchant's account id
    When the merchant requests for registration
    Then the registration finishes successfully and registered merchant is returned

  Scenario: Register already registered merchant
    Given Already registered merchant
    When the merchant requests for registration
    Then merchant registration finishes with failure and the following message is returned "Account already used"

  Scenario: Register account does not exist
    Given Not existing merchant account Id
    When the merchant requests for registration
    Then merchant registration finishes with failure and the following message is returned "Account does not exist"
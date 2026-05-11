@Bootstap
Feature: Bootstrap Dynamic County Data

  Scenario: Load all states and counties
    Given User fetches state codes from database
    And User navigates to County Explorer page
    And User retrieves county and fips for all states
    Then Validation result should be recorded

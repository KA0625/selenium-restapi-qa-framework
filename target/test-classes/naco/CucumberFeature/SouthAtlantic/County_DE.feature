Feature: County Website Validation for DE


 

Scenario: Validate county Kent County, DE
 Given User fetches state codes from database
  And User navigates to County Explorer page
  And User retrieves county and fips for state "DE"
  When User validates website for county "Kent County, DE" with fips "10001"

Scenario: Validate county New Castle County, DE
  When User validates website for county "New Castle County, DE" with fips "10003"

Scenario: Validate county Sussex County, DE
  When User validates website for county "Sussex County, DE" with fips "10005"


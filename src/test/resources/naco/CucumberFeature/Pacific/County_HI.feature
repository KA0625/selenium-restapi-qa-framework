Feature: County Website Validation for HI


 

Scenario: Validate county Hawaii County, HI
 Given User fetches state codes from database
  And User navigates to County Explorer page
  And User retrieves county and fips for state "HI"
  When User validates website for county "Hawaii County, HI" with fips "15001"

Scenario: Validate county Honolulu City and County, HI
  When User validates website for county "Honolulu City and County, HI" with fips "15003"

Scenario: Validate county Kauai County, HI
  When User validates website for county "Kauai County, HI" with fips "15007"

Scenario: Validate county Maui County, HI
  When User validates website for county "Maui County, HI" with fips "15009"


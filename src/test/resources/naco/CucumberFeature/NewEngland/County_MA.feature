@api
Feature: County Website Validation for MA

Scenario: Validate county Barnstable County, MA
  Given User fetches state codes from database
  And User navigates to County Explorer page
  And User retrieves county and fips for state "MA"
  When User validates website for county "Barnstable County, MA" with fips "25001"

Scenario: Validate county Bristol County, MA
  When User validates website for county "Bristol County, MA" with fips "25005"

Scenario: Validate county Dukes County, MA
  When User validates website for county "Dukes County, MA" with fips "25007"

Scenario: Validate county Nantucket County, MA
  When User validates website for county "Nantucket County, MA" with fips "25019"

Scenario: Validate county Norfolk County, MA
  When User validates website for county "Norfolk County, MA" with fips "25021"

Scenario: Validate county Plymouth County, MA
  When User validates website for county "Plymouth County, MA" with fips "25023"

Scenario: Validate county Suffolk County, City of Boston, MA
  When User validates website for county "Suffolk County, City of Boston, MA" with fips "25025"


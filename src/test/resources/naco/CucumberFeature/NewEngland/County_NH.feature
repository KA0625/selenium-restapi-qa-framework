@api
Feature: County Website Validation for NH

Scenario: Validate county Belknap County, NH
 Given User fetches state codes from database
  And User navigates to County Explorer page
  And User retrieves county and fips for state "NH"
  When User validates website for county "Belknap County, NH" with fips "33001"

Scenario: Validate county Carroll County, NH
  When User validates website for county "Carroll County, NH" with fips "33003"

Scenario: Validate county Cheshire County, NH
  When User validates website for county "Cheshire County, NH" with fips "33005"

Scenario: Validate county Coos County, NH
  When User validates website for county "Coos County, NH" with fips "33007"

Scenario: Validate county Grafton County, NH
  When User validates website for county "Grafton County, NH" with fips "33009"

Scenario: Validate county Hillsborough County, NH
  When User validates website for county "Hillsborough County, NH" with fips "33011"

Scenario: Validate county Merrimack County, NH
  When User validates website for county "Merrimack County, NH" with fips "33013"

Scenario: Validate county Rockingham County, NH
  When User validates website for county "Rockingham County, NH" with fips "33015"

Scenario: Validate county Strafford County, NH
  When User validates website for county "Strafford County, NH" with fips "33017"

Scenario: Validate county Sullivan County, NH
  When User validates website for county "Sullivan County, NH" with fips "33019"


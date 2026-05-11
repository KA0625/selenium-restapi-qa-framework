@Mountain @West
Feature: County Website Validation for AZ


Scenario: Validate county Apache County, AZ
  Given User fetches state codes from database
  And User navigates to County Explorer page
  And User retrieves county and fips for state "AZ"
 When User validates website for county "Apache County, AZ" with fips "04001"

Scenario: Validate county Cochise County, AZ
  When User validates website for county "Cochise County, AZ" with fips "04003"

Scenario: Validate county Coconino County, AZ
  When User validates website for county "Coconino County, AZ" with fips "04005"

Scenario: Validate county Gila County, AZ
  When User validates website for county "Gila County, AZ" with fips "04007"

Scenario: Validate county Graham County, AZ
  When User validates website for county "Graham County, AZ" with fips "04009"

Scenario: Validate county Greenlee County, AZ
  When User validates website for county "Greenlee County, AZ" with fips "04011"

Scenario: Validate county La Paz County, AZ
  When User validates website for county "La Paz County, AZ" with fips "04012"

Scenario: Validate county Maricopa County, AZ
  When User validates website for county "Maricopa County, AZ" with fips "04013"

Scenario: Validate county Mohave County, AZ
  When User validates website for county "Mohave County, AZ" with fips "04015"

Scenario: Validate county Navajo County, AZ
  When User validates website for county "Navajo County, AZ" with fips "04017"

Scenario: Validate county Pima County, AZ
  When User validates website for county "Pima County, AZ" with fips "04019"

Scenario: Validate county Pinal County, AZ
  When User validates website for county "Pinal County, AZ" with fips "04021"

Scenario: Validate county Santa Cruz County, AZ
  When User validates website for county "Santa Cruz County, AZ" with fips "04023"

Scenario: Validate county Yavapai County, AZ
  When User validates website for county "Yavapai County, AZ" with fips "04025"

Scenario: Validate county Yuma County, AZ
  When User validates website for county "Yuma County, AZ" with fips "04027"


Feature: County Website Validation for VT


Scenario: Validate county Addison County, VT
 Given User fetches state codes from database
  And User navigates to County Explorer page
  And User retrieves county and fips for state "VT"
  When User validates website for county "Addison County, VT" with fips "50001"

Scenario: Validate county Bennington County, VT
  When User validates website for county "Bennington County, VT" with fips "50003"

Scenario: Validate county Caledonia County, VT
  When User validates website for county "Caledonia County, VT" with fips "50005"

Scenario: Validate county Chittenden County, VT
  When User validates website for county "Chittenden County, VT" with fips "50007"

Scenario: Validate county Essex County, VT
  When User validates website for county "Essex County, VT" with fips "50009"

Scenario: Validate county Franklin County, VT
  When User validates website for county "Franklin County, VT" with fips "50011"

Scenario: Validate county Grand Isle County, VT
  When User validates website for county "Grand Isle County, VT" with fips "50013"

Scenario: Validate county Lamoille County, VT
  When User validates website for county "Lamoille County, VT" with fips "50015"

Scenario: Validate county Orange County, VT
  When User validates website for county "Orange County, VT" with fips "50017"

Scenario: Validate county Orleans County, VT
  When User validates website for county "Orleans County, VT" with fips "50019"

Scenario: Validate county Rutland County, VT
  When User validates website for county "Rutland County, VT" with fips "50021"

Scenario: Validate county Washington County, VT
  When User validates website for county "Washington County, VT" with fips "50023"

Scenario: Validate county Windham County, VT
  When User validates website for county "Windham County, VT" with fips "50025"

Scenario: Validate county Windsor County, VT
  When User validates website for county "Windsor County, VT" with fips "50027"


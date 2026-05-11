@api
Feature: County Website Validation for ME

Scenario: Validate county Androscoggin County, ME
  Given User fetches state codes from database
  And User navigates to County Explorer page
  And User retrieves county and fips for state "ME"
  When User validates website for county "Androscoggin County, ME" with fips "23001"


Scenario: Validate county Aroostook County, ME
  When User validates website for county "Aroostook County, ME" with fips "23003"

Scenario: Validate county Cumberland County, ME
  When User validates website for county "Cumberland County, ME" with fips "23005"

Scenario: Validate county Franklin County, ME
  When User validates website for county "Franklin County, ME" with fips "23007"

Scenario: Validate county Hancock County, ME
  When User validates website for county "Hancock County, ME" with fips "23009"

Scenario: Validate county Kennebec County, ME
  When User validates website for county "Kennebec County, ME" with fips "23011"

Scenario: Validate county Knox County, ME
  When User validates website for county "Knox County, ME" with fips "23013"

Scenario: Validate county Lincoln County, ME
  When User validates website for county "Lincoln County, ME" with fips "23015"

Scenario: Validate county Oxford County, ME
  When User validates website for county "Oxford County, ME" with fips "23017"

Scenario: Validate county Penobscot County, ME
  When User validates website for county "Penobscot County, ME" with fips "23019"

Scenario: Validate county Piscataquis County, ME
  When User validates website for county "Piscataquis County, ME" with fips "23021"

Scenario: Validate county Sagadahoc County, ME
  When User validates website for county "Sagadahoc County, ME" with fips "23023"

Scenario: Validate county Somerset County, ME
  When User validates website for county "Somerset County, ME" with fips "34035"

Scenario: Validate county Waldo County, ME
  When User validates website for county "Waldo County, ME" with fips "23027"

Scenario: Validate county Washington County, ME
  When User validates website for county "Washington County, ME" with fips "23029"

Scenario: Validate county York County, ME
  When User validates website for county "York County, ME" with fips "23031"


Feature: County Website Validation for MD


  

Scenario: Validate county Allegany County, MD
Given User fetches state codes from database
  And User navigates to County Explorer page
  And User retrieves county and fips for state "MD"
  When User validates website for county "Allegany County, MD" with fips "24001"

Scenario: Validate county Anne Arundel County, MD
  When User validates website for county "Anne Arundel County, MD" with fips "24003"

Scenario: Validate county Baltimore County, MD
  When User validates website for county "Baltimore County, MD" with fips "24005"

Scenario: Validate county Calvert County, MD
  When User validates website for county "Calvert County, MD" with fips "24009"

Scenario: Validate county Caroline County, MD
  When User validates website for county "Caroline County, MD" with fips "24011"

Scenario: Validate county Carroll County, MD
  When User validates website for county "Carroll County, MD" with fips "24013"

Scenario: Validate county Cecil County, MD
  When User validates website for county "Cecil County, MD" with fips "24015"

Scenario: Validate county Charles County, MD
  When User validates website for county "Charles County, MD" with fips "24017"

Scenario: Validate county Dorchester County, MD
  When User validates website for county "Dorchester County, MD" with fips "24019"

Scenario: Validate county Frederick County, MD
  When User validates website for county "Frederick County, MD" with fips "24021"

Scenario: Validate county Garrett County, MD
  When User validates website for county "Garrett County, MD" with fips "24023"

Scenario: Validate county Harford County, MD
  When User validates website for county "Harford County, MD" with fips "24025"

Scenario: Validate county Howard County, MD
  When User validates website for county "Howard County, MD" with fips "24027"

Scenario: Validate county Kent County, MD
  When User validates website for county "Kent County, MD" with fips "24029"

Scenario: Validate county Montgomery County, MD
  When User validates website for county "Montgomery County, MD" with fips "24031"

Scenario: Validate county Prince George's County, MD
  When User validates website for county "Prince George's County, MD" with fips "24033"

Scenario: Validate county Queen Anne's County, MD
  When User validates website for county "Queen Anne's County, MD" with fips "24035"

Scenario: Validate county St. Mary's County, MD
  When User validates website for county "St. Mary's County, MD" with fips "24037"

Scenario: Validate county Somerset County, MD
  When User validates website for county "Somerset County, MD" with fips "24039"

Scenario: Validate county Talbot County, MD
  When User validates website for county "Talbot County, MD" with fips "24041"

Scenario: Validate county Washington County, MD
  When User validates website for county "Washington County, MD" with fips "24043"

Scenario: Validate county Wicomico County, MD
  When User validates website for county "Wicomico County, MD" with fips "24045"

Scenario: Validate county Worcester County, MD
  When User validates website for county "Worcester County, MD" with fips "24047"

Scenario: Validate county Baltimore City, MD
  When User validates website for county "Baltimore City, MD" with fips "24510"


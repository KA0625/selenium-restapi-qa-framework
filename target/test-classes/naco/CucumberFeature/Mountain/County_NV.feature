Feature: County Website Validation for NV


  

Scenario: Validate county Churchill County, NV
Given User fetches state codes from database
  And User navigates to County Explorer page
  And User retrieves county and fips for state "NV"
  When User validates website for county "Churchill County, NV" with fips "32001"

Scenario: Validate county Clark County, NV
  When User validates website for county "Clark County, NV" with fips "32003"

Scenario: Validate county Douglas County, NV
  When User validates website for county "Douglas County, NV" with fips "32005"

Scenario: Validate county Elko County, NV
  When User validates website for county "Elko County, NV" with fips "32007"

Scenario: Validate county Esmeralda County, NV
  When User validates website for county "Esmeralda County, NV" with fips "32009"

Scenario: Validate county Eureka County, NV
  When User validates website for county "Eureka County, NV" with fips "32011"

Scenario: Validate county Humboldt County, NV
  When User validates website for county "Humboldt County, NV" with fips "32013"

Scenario: Validate county Lander County, NV
  When User validates website for county "Lander County, NV" with fips "32015"

Scenario: Validate county Lincoln County, NV
  When User validates website for county "Lincoln County, NV" with fips "32017"

Scenario: Validate county Lyon County, NV
  When User validates website for county "Lyon County, NV" with fips "32019"

Scenario: Validate county Mineral County, NV
  When User validates website for county "Mineral County, NV" with fips "32021"

Scenario: Validate county Nye County, NV
  When User validates website for county "Nye County, NV" with fips "32023"

Scenario: Validate county Pershing County, NV
  When User validates website for county "Pershing County, NV" with fips "32027"

Scenario: Validate county Storey County, NV
  When User validates website for county "Storey County, NV" with fips "32029"

Scenario: Validate county Washoe County, NV
  When User validates website for county "Washoe County, NV" with fips "32031"

Scenario: Validate county White Pine County, NV
  When User validates website for county "White Pine County, NV" with fips "32033"

Scenario: Validate county Carson City, NV
  When User validates website for county "Carson City, NV" with fips "32510"


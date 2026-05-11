@MidAtlantic @api
Feature:County Website Validation for NJ

Scenario: Validate county Atlantic County, NJ
 Given User fetches state codes from database
  And User navigates to County Explorer page
  And User retrieves county and fips for state "NJ"
  When User validates website for county "Atlantic County, NJ" with fips "34001"

Scenario: Validate county Bergen County, NJ
  When User validates website for county "Bergen County, NJ" with fips "34003"

Scenario: Validate county Burlington County, NJ
  When User validates website for county "Burlington County, NJ" with fips "34005"

Scenario: Validate county Camden County, NJ
  When User validates website for county "Camden County, NJ" with fips "34007"

Scenario: Validate county Cape May County, NJ
  When User validates website for county "Cape May County, NJ" with fips "34009"

Scenario: Validate county Cumberland County, NJ
  When User validates website for county "Cumberland County, NJ" with fips "34011"

Scenario: Validate county Essex County, NJ
  When User validates website for county "Essex County, NJ" with fips "34013"

Scenario: Validate county Gloucester County, NJ
  When User validates website for county "Gloucester County, NJ" with fips "34015"

Scenario: Validate county Hudson County, NJ
  When User validates website for county "Hudson County, NJ" with fips "34017"

Scenario: Validate county Hunterdon County, NJ
  When User validates website for county "Hunterdon County, NJ" with fips "34019"

Scenario: Validate county Mercer County, NJ
  When User validates website for county "Mercer County, NJ" with fips "34021"

Scenario: Validate county Middlesex County, NJ
  When User validates website for county "Middlesex County, NJ" with fips "34023"

Scenario: Validate county Monmouth County, NJ
  When User validates website for county "Monmouth County, NJ" with fips "34025"

Scenario: Validate county Morris County, NJ
  When User validates website for county "Morris County, NJ" with fips "34027"

Scenario: Validate county Ocean County, NJ
  When User validates website for county "Ocean County, NJ" with fips "34029"

Scenario: Validate county Passaic County, NJ
  When User validates website for county "Passaic County, NJ" with fips "34031"

Scenario: Validate county Salem County, NJ
  When User validates website for county "Salem County, NJ" with fips "34033"

Scenario: Validate county Somerset County, NJ
  When User validates website for county "Somerset County, NJ" with fips "34035"

Scenario: Validate county Sussex County, NJ
  When User validates website for county "Sussex County, NJ" with fips "34037"

Scenario: Validate county Union County, NJ
  When User validates website for county "Union County, NJ" with fips "34039"

Scenario: Validate county Warren County, NJ
  When User validates website for county "Warren County, NJ" with fips "34041"


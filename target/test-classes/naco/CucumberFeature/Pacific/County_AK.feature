@Pacific @West
Feature: County Website Validation for AK


 Scenario: Validate county Aleutians East Borough, AK
Given User fetches state codes from database
  And User navigates to County Explorer page
  And User retrieves county and fips for state "AK"
  When User validates website for county "Aleutians East Borough, AK" with fips "02013"

Scenario: Validate county Anchorage Borough, AK
  When User validates website for county "Anchorage Borough, AK" with fips "02020"

Scenario: Validate county Bristol Bay Borough, AK
  When User validates website for county "Bristol Bay Borough, AK" with fips "02060"

Scenario: Validate county Denali Borough, AK
  When User validates website for county "Denali Borough, AK" with fips "02068"

Scenario: Validate county Fairbanks North Star Borough, AK
  When User validates website for county "Fairbanks North Star Borough, AK" with fips "02090"

Scenario: Validate county Haines Borough, AK
  When User validates website for county "Haines Borough, AK" with fips "02100"

Scenario: Validate county City and Borough of Juneau, AK
  When User validates website for county "City and Borough of Juneau, AK" with fips "02110"

Scenario: Validate county Kenai Peninsula Borough, AK
  When User validates website for county "Kenai Peninsula Borough, AK" with fips "02122"

Scenario: Validate county Ketchikan Gateway Borough, AK
  When User validates website for county "Ketchikan Gateway Borough, AK" with fips "02130"

Scenario: Validate county Kodiak Island Borough, AK
  When User validates website for county "Kodiak Island Borough, AK" with fips "02150"

Scenario: Validate county Lake And Peninsula Borough, AK
  When User validates website for county "Lake And Peninsula Borough, AK" with fips "02164"

Scenario: Validate county Matanuska-Susitna Borough, AK
  When User validates website for county "Matanuska-Susitna Borough, AK" with fips "02170"

Scenario: Validate county North Slope Borough, AK
  When User validates website for county "North Slope Borough, AK" with fips "02185"

Scenario: Validate county Northwest Arctic Borough, AK
  When User validates website for county "Northwest Arctic Borough, AK" with fips "02188"

Scenario: Validate county Petersburg Borough, AK
  When User validates website for county "Petersburg Borough, AK" with fips "02195"

Scenario: Validate county City and Borough of Sitka, AK
  When User validates website for county "City and Borough of Sitka, AK" with fips "02220"

Scenario: Validate county Skagway Borough, AK
  When User validates website for county "Skagway Borough, AK" with fips "02230"

Scenario: Validate county City and Borough of Wrangell, AK
  When User validates website for county "City and Borough of Wrangell, AK" with fips "02275"

Scenario: Validate county City and Borough of Yakutat, AK
  When User validates website for county "City and Borough of Yakutat, AK" with fips "02282"


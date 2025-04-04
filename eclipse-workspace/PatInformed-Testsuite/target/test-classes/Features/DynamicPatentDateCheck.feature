Feature: Patent Date Calculation

  Scenario: Calculate days between patent filing, publication, and grant dates
    Given Open the patent information page
    When Click on the search button and select the first patent
    Then Calculate the number of days between available dates

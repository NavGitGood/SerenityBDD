Feature: Article Search

  Scenario: Article Search
    Given the base url for NYT REST API and article endpoint
    When api is called using "election" as query parameter and valid apikey
    Then the response should be 200
    And there should be 10 records in body

  Scenario: Article Search with Sort descending
    Given the base url for NYT REST API and article endpoint
    When api is called using "election" as query parameter, valid apikey and sort value as "newest"
    Then the records should be ordered in descending publishing date

  Scenario: Article Search with Sort ascending
    Given the base url for NYT REST API and article endpoint
    When api is called using "election" as query parameter, valid apikey and sort value as "oldest"
    Then the records should be ordered in ascending publishing date
@ignore
Feature: failing scenarios

  Scenario: Article Search - with Sort ascending
    Given the base url for NYT REST API and article endpoint
    When api is called with query parameter "sort" and value "oldest"
    Then the records should be ordered in ascending publishing date

  Scenario: Article Search - with begin_date
    Given the base url for NYT REST API and article endpoint
    When api is called with query parameter "begin_date" and value "19900101"
    Then all the records should be from year "1990"
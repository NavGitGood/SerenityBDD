@ignore
Feature: failing scenarios

  # failing as results are not sorted when there is not other query parameter
  Scenario: Article Search - with Sort ascending
    Given the base url for NYT REST API and article endpoint
    When api is called with query parameter "sort" and value "oldest"
    Then the records should be ordered in ascending publishing date

  # failing as it does not work without end_date
  Scenario: Article Search - with begin_date
    Given the base url for NYT REST API and article endpoint
    When api is called with query parameter "begin_date" and value "19900101"
    Then all the records should be from year "1990"

  # unhandled error, response is 500 instead of 400
  Scenario: Invalid parameter value - facet_fields
    Given the base url for NYT REST API and article endpoint
    When api is called with query parameter "facet_fields" having value "invalid" and query parameter "facet" having value "true"
    Then the response should be 400
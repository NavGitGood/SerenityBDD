@positive
Feature: Positive Scenario

  Scenario: Article Search - no query parameter
    Given the base url for NYT REST API and article endpoint
    When api is called with valid apikey
    Then the response should be 200
    And there should be 10 records in body

  Scenario: Article Search - keyword (q) query parameter
    Given the base url for NYT REST API and article endpoint
    When api is called with query parameter "q" and value "election"
    Then the response should be 200
    And there should be 10 records in body

  Scenario: Article Search - with Sort descending
    Given the base url for NYT REST API and article endpoint
    When api is called with query parameter "sort" and value "newest"
    Then the records should be ordered in descending publishing date

  Scenario: Article Search - with Sort ascending and keyword (q) query parameter
    Given the base url for NYT REST API and article endpoint
    When api is called with query parameter "sort" having value "oldest" and query parameter "q" having value "election"
    Then the records should be ordered in ascending publishing date

  Scenario: Article Search - with begin_date and end_date
    Given the base url for NYT REST API and article endpoint
    When api is called with query parameter "begin_date" having value "19900101" and query parameter "end_date" having value "19900101"
    Then all the records should be from year "1990"

  Scenario: Article Search - with filter list
    Given the base url for NYT REST API and article endpoint
    When api is called with query parameter "fl" and value "abstract"
    Then the records should only have "abstract" field inside docs

  Scenario Outline: Valid parameter value
    Given the base url for NYT REST API and article endpoint
    When api is called with query parameter <queryParamKey> having valid value <queryParamValue>
    Then the response should be 200

    Examples:
      | queryParamKey  | queryParamValue |
      | "sort"         | "newest"        |
      | "sort"         | "oldest"        |
      | "facet"        | "false"         |
      | "facet_filter" | "false"         |

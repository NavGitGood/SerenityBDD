@negative
Feature: Negative Scenario

  Scenario: Article Search - Invalid key
    Given the base url for NYT REST API and article endpoint
    When api is called with invalid apikey
    Then the response should be 401

  Scenario: Article Search - no key
    Given the base url for NYT REST API and article endpoint
    When api is called with no apikey
    Then the response should be 401

  Scenario Outline: Bad data type
    Given the base url for NYT REST API and article endpoint
    When api is called with query parameter <queryParamKey> having numeric value 1 instead of string
    Then the response should be 400

    Examples:
      | queryParamKey   |
      | "begin_date"    |
      | "end_date"      |

  Scenario Outline: Invalid parameter value
    Given the base url for NYT REST API and article endpoint
    When api is called with query parameter <queryParamKey> having invalid value <queryParamValue>
    Then the response should be 400

    Examples:
      | queryParamKey  | queryParamValue |
      | "facet"        | "yess"          |
      | "facet_filter" | "noo"           |
      | "sort"         | "latest"        |
      | "page"         | "1000"          |

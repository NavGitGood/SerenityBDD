Feature: Bad Requests

  Scenario: Article Search - Invalid key
    Given the base url for NYT REST API and article endpoint
    When api is called using "election" as query parameter and invalid apikey
    Then the response should be 401

  Scenario: Article Search - no key
    Given the base url for NYT REST API and article endpoint
    When api is called using "election" as query parameter and no apikey
    Then the response should be 401
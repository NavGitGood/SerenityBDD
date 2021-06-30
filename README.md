# SerenityBDD #
[GitHub](https://github.com/NavGitGood/SerenityBDD.git)
[GitLab](https://gitlab.com/nnav4u/SerenityBDD.git)

## API Test Automation framework with following features ##
1. Use of Serenity BDD with Cucumber
2. Test Automation of [NYT Article API](https://api.nytimes.com/svc/search/v2/articlesearch.json) using SerenityAssured (RestAssured)
3. Integration with GitLab CI pipeline for build, test execution and report generation
4. API Documentation with swagger 2.0

## Scenarios ##
1. 10 positive scenarios in `positive_scenarios.feature`
2. 8 negative scenarios in `negative_scenarios.feature`
3. 3 failing scenarios in `failing_scenarios.feature`

## Pre-requisites to run and use locally ##
1. add env variable NYT_Access_Token and add your token as value (get you token from [NYT Website](https://developer.nytimes.com/apis))
2. make sure maven is installed (verified on maven 3.6.0 and java 8)

## Adding more tests ##
1. Add new scenarios either in one of the feature files already present or create your own
2. Use tags with a new feature (if required)
3. Add associated step definitions in either `BaseSteps` file or create your own step definition file (make sure to use `bdd.step_definitions` package or else update the glue in `TestRunner` file)

# IMPORTANT! #
> As NYT API has rate limiting (per minute, per day), not too many scenarios could be executed together otherwise it would start responding with 429.
> So, use @positive tag to run positive scenarios, @negative tag for negative scenarios and @ignore tag for failing scenarios

## How to run on powershell or terminal ##
1. open `powershell / terminal` and navigate to root directory of the project
2. update the tag (if required) in `TestRunner` file
3. run `mvn clean verify`

## How to run and view report on gitlab ##
1. push the code on your gitlab repo
2. navigate to `Settings -> CI/CD -> Variables`
3. add env variable NYT_Access_Token and add your token as value
4. navigate to `CI/CD -> Pipelines` and click on `Run pipeline`
5. after execution is complete, go to `Jobs` and click on `Download artifacts` button under `Coverage` column to download the report, unzip it and open `index.html`
6. to view the report directly, go to build logs, browse the `Job artifacts` and open `index.html`

## How to view and use the API documentation ##
1. Open the link [Swagger Viewer](https://editor.swagger.io/)
2. Click on `File`, select `Import file` and then select `documentation.yml` from your local directory
3. Documentation would be generated on the right.
4. Click on `Authorize` button, paste or type in your token and click `Authorize`
5. Expand documentation by clicking on `GET`
6. Click on `Try it out` to use the api
7. Enter values in some query parameters (as required) and click on `Execute` button

## Some issues in API (after comparing with [official documentation](https://developer.nytimes.com/docs/articlesearch-product/1/routes/articlesearch.json/get)) ##
1. Sorting does not work if used without any other query parameter (to verify, run the ignored scenario `Scenario: Article Search - with Sort ascending -> this will fail due to a bug`)
2. When only `begin_date` is provided, you would expect the results to respect the condition, but this does not happen (works as expected when `end_date` is also provided)
    <details> 
   <summary>Example</summary>
    On passing begin_date as 19901001 the pub_date still has the latest year instead of 1990
    </details>
3. For query parameters `facet` and `facet_filter`, it is mentioned that valid values are only [true, false] but they also accept [yes, no, 1, 0] (documentation bug ?)
4. When `facet` is passed as `true` and `facet_filter` has some invalid value (other than [day_of_week, document_type, ingredients, news_desk, pub_month, pub_year, section_name, source, subsection_name, type_of_material]), api response is `500` (ideally it should be handled)
5. Official documentation mentions acceptable values for `page` to be an integer in range [0,100] but in reality it is [0,200] as evident from API response

# SerenityBDD
[GitHub](https://github.com/NavGitGood/SerenityBDD.git)
[GitLab](https://gitlab.com/nnav4u/SerenityBDD.git)

## API Test Automation framework with following features ##
1. Use of Serenity BDD with Cucumber
2. Test Automation of [NYT Article API](https://api.nytimes.com/svc/search/v2/articlesearch.json) using SerenityAssured (RestAssured)
3. Integration with GitLab CI pipeline for build, test execution and report generation
4. API Documentation with swagger 2.0

## Pre-requisites to run locally ##
1. add env variable NYT_Access_Token and add your token as value
2. make sure maven is installed

## How to run on powershell or terminal ##
1. open `powershell / terminal` and navigate to root directory of the project
2. run `mvn clean verify`

## How to run and view report on gitlab ##
1. push the code on your gitlab repo
2. navigate to `Settings -> CI/CD -> Variables`
3. add env variable NYT_Access_Token and add your token as value
4. navigate to `CI/CD -> Pipelines` and click on `Run pileline`
5. after execution is complete, go to `Jobs` and click on `Download artifacts` button under `Coverage` column to download the report, unzip it and open `index.html`
6. to view the report directly, go to build logs and browse the `Job artifacts`

## How to view the API documentation ##
1. Open the link [Swagger Viewer](https://editor.swagger.io/)
2. Click on `File`, select `Import file` and then select `documentation.yml` from your local directory
3. Documentation would be generated on the right.

## Some issues in API ##
1. Sorting does not work if used without any other query parameter (to verify, run the ignored scenario `Scenario: Article Search - with Sort ascending -> this will fail due to a bug`)
2. When only `begin_date` is provided, you would expect the results to respect the condition, but this does not happen (works as expected when `end_date` is also provided)
    <details> 
   <summary>Example</summary>
    On passing `begin_date` as `19901001` the `pub_date` still has the latest year instead of `1990`
    </details>
3. To run failing scenarios, use tag `@ignore` in `TestRunner` file
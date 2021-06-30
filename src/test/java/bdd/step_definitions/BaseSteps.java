package bdd.step_definitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import utils.APIRequestMaker;
import utils.ConfigurationLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static net.serenitybdd.rest.SerenityRest.then;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class BaseSteps {

    public static String url;
    public static String invalidKey;

    @Steps
    APIRequestMaker apiRequestMaker;

    @Steps
    ConfigurationLoader configurationLoader;

    @Before
    public void setup()
    {
        configurationLoader.readProperties();
        invalidKey = "h984nhwge8h3";
    }

    @Given("the base url for NYT REST API and article endpoint")
    public void withArticleSearchAPIEndpoint() {
        url = configurationLoader.getPropertyValue("baseURL") +
                configurationLoader.getPropertyValue("articleSearch");
    }

    @When("api is called with valid apikey")
    public void queryArticleWithValidAuth() {
        apiRequestMaker.makeAuthorisedGetRequest(
                url
        );
    }

    @When("api is called with invalid apikey")
    public void queryArticleWithInValidAuth() {
        apiRequestMaker.makeGetRequestWithAuthKeyAsParam(
                url,
                invalidKey
        );
    }

    @When("api is called with no apikey")
    public void queryArticleWithNoAuth() {
        apiRequestMaker.makeGetRequestNoAuth(
                url
        );
    }

    @When("api is called with query parameter {string} and value {string}")
    @When("api is called with query parameter {string} having valid value {string}")
    @When("api is called with query parameter {string} having invalid value {string}")
    public void queryArticleWithQueryParameter(String queryParamKey, String queryParamValue) {
        apiRequestMaker.makeAuthorisedGetRequest(
                url,
                queryParamKey,
                queryParamValue
        );
    }

    @When("api is called with query parameter {string} having numeric value {int} instead of string")
    public void queryArticleWithBadQueryParameterValue(String queryParamKey, Integer queryParamValue) {
        apiRequestMaker.makeAuthorisedGetRequest(
                url,
                queryParamKey,
                queryParamValue
        );
    }

    @When("api is called with query parameter {string} having value {string} and query parameter {string} having value {string}")
    public void queryArticleWithQueryParameters(String queryParamKey1, String queryParamValue1, String queryParamKey2, String queryParamValue2) {
        apiRequestMaker.makeAuthorisedGetRequest(
                url,
                queryParamKey1,
                queryParamValue1,
                queryParamKey2,
                queryParamValue2
        );
    }

    @Then("the response should be {int}")
    public void assertResponseCode(Integer responseCode) {
        restAssuredThat(response -> response.statusCode(responseCode));
    }

    @Then("there should be {int} records in body")
    public void assertResponseCount(Integer recordCount) {
        then().assertThat().body("response.docs.size()", is(recordCount));
    }

    @Then("the records should be ordered in descending publishing date")
    public void assertResponseOrderDescending() {
        Response response = then().extract().response();
        List<String> pub_date = response.jsonPath().getList("response.docs.pub_date");
        List<String> pub_date_reversed = response.jsonPath().getList("response.docs.pub_date");
        Collections.reverse(pub_date_reversed);

        assertThat(
                pub_date.stream().sorted().collect(Collectors.toList()).equals(pub_date_reversed),
                is(true)
        );
    }

    @Then("the records should be ordered in ascending publishing date")
    public void assertResponseOrderAscending() {
        Response response = then().extract().response();
        List<String> pub_date_list = response.jsonPath().getList("response.docs.pub_date");
        assertThat(
                pub_date_list.stream().sorted().collect(Collectors.toList()).equals(pub_date_list),
                is(true)
        );
    }

    @Then("all the records should be from year {string}")
    public void assertResponseFromYear(String year) {
        Response response = then().extract().response();
        List<String> pub_date_list = response.jsonPath().getList("response.docs.pub_date");
        assertThat(
                pub_date_list
                        .stream()
                        .map(pub_date -> pub_date.split("-")[0])
                .collect(Collectors.toList()),
                everyItem(is(year))
        );
    }

    @Then("the records should only have {string} field inside docs")
    public void assertExactProperty(String field) {
        Response response = then().extract().response();
        List<Map<String, String >> doc_arr = response.jsonPath().getList("response.docs");

        assertThat(
                doc_arr
                        .stream()
                        .map(doc -> new ArrayList<>(doc.keySet()))
                        .collect(Collectors.toList())
                        .stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toList()),
                everyItem(is(field))
        );
    }

}

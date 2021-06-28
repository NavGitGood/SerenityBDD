package bdd.step_definitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import utils.APIRequestMaker;
import utils.ConfigurationLoader;

import java.util.Collections;
import java.util.List;
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

    @When("api is called using {string} as query parameter and valid apikey")
    public void queryArticleWithValidAuth(String queryParam) {
        apiRequestMaker.makeGetRequest(
                url,
                queryParam,
                System.getenv("NYT_Access_Token")
        );
    }

    @When("api is called using {string} as query parameter and invalid apikey")
    public void queryArticleWithInValidAuth(String queryParam) {
        apiRequestMaker.makeGetRequest(
                url,
                queryParam,
                invalidKey
        );
    }

    @When("api is called using {string} as query parameter and no apikey")
    public void queryArticleWithNoAuth(String queryParam) {
        apiRequestMaker.makeGetRequest(
                url,
                queryParam
        );
    }

    @When("api is called using {string} as query parameter, valid apikey and sort value as {string}")
    public void queryArticleWithValidAuthAndSort(String queryParam, String sortAs) {
        apiRequestMaker.makeGetRequest(
                url,
                queryParam,
                System.getenv("NYT_Access_Token"),
                sortAs
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
        List<String> pub_date = response.jsonPath().getList("response.docs.pub_date");
        assertThat(
                pub_date.stream().sorted().collect(Collectors.toList()).equals(pub_date),
                is(true)
        );
    }

}

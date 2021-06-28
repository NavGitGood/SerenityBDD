package utils;

import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class APIRequestMaker {

    @Step("")
    public void makeGetRequest(String url, String query, String apiKey) {
        SerenityRest.given()
                .queryParam("q", query)
                .queryParam("api-key", apiKey)
                .when().get(url);
    }

    @Step("")
    public void makeGetRequest(String url, String query, String apiKey, String sortAs) {
        SerenityRest.given()
                .queryParam("q", query)
                .queryParam("api-key", apiKey)
                .queryParam("sort", sortAs)
                .when().get(url);
    }

    @Step("")
    public void makeGetRequest(String url, String query) {
        SerenityRest.given()
                .queryParam("q", query)
                .when().get(url);
    }

}
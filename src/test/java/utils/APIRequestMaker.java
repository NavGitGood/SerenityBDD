package utils;

import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class APIRequestMaker {

    @Step("")
    public void makeGetRequestNoAuth(String url) {
        SerenityRest.given()
                .when().get(url);
    }

    @Step("")
    public void makeGetRequestWithAuthKeyAsParam(String url, String apiKey) {
        SerenityRest.given()
                .queryParam("api-key", apiKey)
                .when().get(url);
    }

    @Step("")
    public void makeAuthorisedGetRequest(String url) {
        SerenityRest.given()
                .queryParam("api-key", System.getenv("NYT_Access_Token"))
                .when().get(url);
    }

    @Step("")
    public void makeAuthorisedGetRequest(String url, String queryParamKey, String queryParamValue) {
        SerenityRest.given()
                .queryParam(queryParamKey, queryParamValue)
                .queryParam("api-key", System.getenv("NYT_Access_Token"))
                .when().get(url);
    }

    @Step("")
    public void makeAuthorisedGetRequest(String url, String queryParamKey1, String queryParamValue1, String queryParamKey2, String queryParamValue2) {
        SerenityRest.given()
                .queryParam(queryParamKey1, queryParamValue1)
                .queryParam(queryParamKey2, queryParamValue2)
                .queryParam("api-key", System.getenv("NYT_Access_Token"))
                .when().get(url);
    }

}
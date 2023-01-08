package uk.gov.hmcts.dts.controllers.util;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.CollectionUtils;
import uk.gov.hmcts.dts.mytime.Application;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@ContextConfiguration
@SpringBootTest(classes = {Application.class},
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FunctionalTestBase {
    protected static final String CONTENT_TYPE_VALUE = "application/json";

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost:4001";
    }

    protected Response doGetRequest(final String path) {
        return doGetRequest(path, null);
    }

    protected Response doGetRequest(final String path, final Map<String, String> additionalHeaders) {
        return given()
            .relaxedHTTPSValidation()
            .headers(getRequestHeaders(additionalHeaders))
            .when()
            .get(path)
            .thenReturn();
    }

    protected Response doPutRequest(final String path, final String body) {
        return doPutRequest(path, null, body);
    }

    protected Response doPutRequest(final String path, final Map<String, String> additionalHeaders, final String body) {
        return given()
            .relaxedHTTPSValidation()
            .headers(getRequestHeaders(additionalHeaders))
            .body(body)
            .when()
            .put(path)
            .thenReturn();
    }

    protected Response doPostRequest(final String path, final String body) {
        return doPostRequest(path, null, body);
    }

    protected Response doPostRequest(final String path,
                                     final Map<String, String> additionalHeaders, final String body) {
        return given()
            .relaxedHTTPSValidation()
            .headers(getRequestHeaders(additionalHeaders))
            .body(body)
            .when()
            .post(path)
            .thenReturn();
    }

    protected Response doDeleteRequest(final String path) {
        return given()
            .relaxedHTTPSValidation()
            .when()
            .delete(path)
            .thenReturn();
    }

    protected Response doDeleteRequest(final String path, final String body) {
        return doDeleteRequest(path, null, body);
    }

    protected Response doDeleteRequest(final String path,
                                       final Map<String, String> additionalHeaders, final String body) {
        return given()
            .relaxedHTTPSValidation()
            .headers(getRequestHeaders(additionalHeaders))
            .body(body)
            .when()
            .delete(path)
            .thenReturn();
    }

    private static Map<String, String> getRequestHeaders(final Map<String, String> additionalHeaders) {
        final Map<String, String> headers = new ConcurrentHashMap<>(Map.of(CONTENT_TYPE, CONTENT_TYPE_VALUE));
        if (!CollectionUtils.isEmpty(additionalHeaders)) {
            headers.putAll(additionalHeaders);
        }
        return headers;
    }
}

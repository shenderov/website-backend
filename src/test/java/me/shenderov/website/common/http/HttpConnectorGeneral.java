package me.shenderov.website.common.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import me.shenderov.website.common.TestTools;
import me.shenderov.website.security.entities.LoginWrapper;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class HttpConnectorGeneral {
    HttpConnectorGeneral(String hostname, int port, String basePath) {
        if (System.getProperty("baseURI") != null)
            RestAssured.baseURI = System.getProperty("baseURI");
        else
            RestAssured.baseURI = "http://" + hostname;
        if (System.getProperty("port") != null)
            RestAssured.port = Integer.parseInt(System.getProperty("port"));
        else
            RestAssured.port = port;
        if (System.getProperty("basePath") != null)
            RestAssured.basePath = System.getProperty("basePath");
        else
            RestAssured.basePath = basePath;
    }

    protected TestTools testTools = new TestTools();
    protected ObjectMapper mapper = new ObjectMapper();

//    HttpResponse sendPostRequest(String api, String request, Map<String, String> headers) {
//        Response response = sendHttpPostRequest(api, request, headers);
//        return new HttpResponse(response.asString(), response);
//    }
//
//    HttpResponse sendPostRequest(String api, String request, String token) {
//        Response response = sendHttpPostRequest(api, request, testTools.setToken(token));
//        return new HttpResponse(response.asString(), response);
//    }
//
//    HttpResponse sendPostRequest(String api, String request) {
//        Response response = sendHttpPostRequest(api, request, new HashMap<>());
//        return new HttpResponse(response.asString(), response);
//    }
//
//    HttpResponse sendPostRequest(String api, Object request, Map<String, String> headers) {
//        Response response = sendHttpPostRequest(api, testTools.objectToJson(request), headers);
//        return new HttpResponse(response.asString(), response);
//    }
//
//    HttpResponse sendPostRequest(String api, Object request, String token) {
//        Response response = sendHttpPostRequest(api, testTools.objectToJson(request), testTools.setToken(token));
//        return new HttpResponse(response.asString(), response);
//    }
//
//    HttpResponse sendPostRequest(String api, Object request) {
//        Response response = sendHttpPostRequest(api, testTools.objectToJson(request), new HashMap<>());
//        return new HttpResponse(response.asString(), response);
//    }
//
//    HttpResponse sendGetRequest(String api, Map<String, String> headers) {
//        Response response = sendHttpGetRequest(api, headers);
//        return new HttpResponse(response.asString(), response);
//    }
//
//    HttpResponse sendGetRequest(String api, String token) {
//        Response response = sendHttpGetRequest(api, testTools.setToken(token));
//        return new HttpResponse(response.asString(), response);
//    }

    HttpResponse sendGetRequest(String api) {
        Response response = sendHttpGetRequest(api, new HashMap<>(), new HashMap<>());
        return new HttpResponse(response.asString(), response);
    }

    HttpResponse sendGetRequest(String api, Map<String, String> params) {
        Response response = sendHttpGetRequest(api, new HashMap<>(), params);
        return new HttpResponse(response.asString(), response);
    }

    HttpResponse sendGetRequest(String api, LoginWrapper wrapper) {
        Response response = sendHttpGetRequestBasicAuth(api, new HashMap<>(), new HashMap<>(), wrapper);
        return new HttpResponse(response.asString(), response);
    }

    HttpResponse sendGetRequest(String api, Map<String, String> params, LoginWrapper wrapper) {
        Response response = sendHttpGetRequestBasicAuth(api, new HashMap<>(), params, wrapper);
        return new HttpResponse(response.asString(), response);
    }

    //Post requests
    HttpResponse sendPostRequest(String api, Object request, LoginWrapper wrapper) {
        Response response = sendHttpPostRequestBasicAuth(api, testTools.objectToJson(request), new HashMap<>(), wrapper);
        return new HttpResponse(response.asString(), response);
    }

    HttpResponse sendPostRequest(String api, Object request) {
        Response response = sendHttpPostRequest(api, testTools.objectToJson(request), new HashMap<>());
        return new HttpResponse(response.asString(), response);
    }

    //Delete requests
    HttpResponse sendDeleteRequest(String api, Map<String, ?> params) {
        Response response = sendHttpDeleteRequest(api, new HashMap<>(), params);
        return new HttpResponse(response.asString(), response);
    }

    HttpResponse sendDeleteRequest(String api) {
        Response response = sendHttpDeleteRequest(api, new HashMap<>(), new HashMap<>());
        return new HttpResponse(response.asString(), response);
    }

    HttpResponse sendDeleteRequest(String api, Map<String, ?> params, LoginWrapper wrapper) {
        Response response = sendHttpDeleteRequestBasicAuth(api, new HashMap<>(), params, wrapper);
        return new HttpResponse(response.asString(), response);
    }

    HttpResponse sendDeleteRequest(String api, LoginWrapper wrapper) {
        Response response = sendHttpDeleteRequestBasicAuth(api, new HashMap<>(), new HashMap<>(), wrapper);
        return new HttpResponse(response.asString(), response);
    }

    private Response sendHttpGetRequestBasicAuth(String api, Map<String, String> headers, Map<String, String> params, LoginWrapper wrapper) {
        Response response;
        response =
                given().
                        auth().basic(wrapper.getUsername(), wrapper.getPassword()).
                        headers(headers).
                        queryParams(params).
                        when().
                        get(api).
                        then().
//                        contentType(ContentType.JSON).
                        extract().response();
        return response;
    }

    private Response sendHttpGetRequest(String api, Map<String, String> headers, Map<String, String> params) {
        Response response;
        response =
                given().
                        headers(headers).
                        queryParams(params).
                        when().
                        get(api).
                        then().
                        contentType(ContentType.JSON).
                        extract().response();
        return response;
    }

    private Response sendHttpDeleteRequestBasicAuth(String api, Map<String, String> headers, Map<String, ?> params, LoginWrapper loginWrapper) {
        Response response;
        response =
                given().
                        auth().basic(loginWrapper.getUsername(), loginWrapper.getPassword()).
                        headers(headers).
                        queryParams(params).
                        when().
                        delete(api).
                        then().
                        contentType(ContentType.JSON).
                        extract().response();
        return response;
    }

    private Response sendHttpDeleteRequest(String api, Map<String, String> headers, Map<String, ?> params) {
        Response response;
        response =
                given().
                        headers(headers).
                        queryParams(params).
                        when().
                        delete(api).
                        then().
                        contentType(ContentType.JSON).
                        extract().response();
        return response;
    }

    private Response sendHttpPostRequestBasicAuth(String api, Object request, Map<String, String> headers, LoginWrapper loginWrapper) {
        Response response;
        response =
                given().
                        auth().basic(loginWrapper.getUsername(), loginWrapper.getPassword()).
                        body(request).
                        headers(headers).
                        contentType(ContentType.JSON).
                        when().
                        post(api).
                        then().
                        extract().response();
        return response;
    }

    private Response sendHttpPostRequest(String api, Object request, Map<String, String> headers) {
        Response response;
        response =
                given().
                        body(request).
                        headers(headers).
                        contentType(ContentType.JSON).
                        when().
                        post(api).
                        then().
                        extract().response();
        return response;
    }
}

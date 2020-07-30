package me.shenderov.website.common.http;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.Map;

@SuppressWarnings({"UnusedDeclaration"})
public class HttpResponseJson extends HttpResponse {

    public HttpResponseJson() {
    }

    HttpResponseJson(JsonObject jsonObject, Map<String, String> cookies, Headers headers, int httpStatusCode) {
        super(jsonObject, cookies, headers, httpStatusCode);
    }

    public HttpResponseJson(JsonObject jsonObject, Response response) {
        super(jsonObject, response);
    }

    public HttpResponseJson(Response response) {
        JsonObject jsonObject = new JsonParser().parse(response.asString()).getAsJsonObject();
        super.setObject(jsonObject);
        super.setCookies(response.getCookies());
        super.setHeaders(response.getHeaders());
        super.setHttpStatusCode(response.getStatusCode());
    }

    @Override
    public JsonObject getObject() {
        return (JsonObject) super.getObject();
    }

    public void setObject(JsonObject jsonObject) {
        super.setObject(jsonObject);
    }
}

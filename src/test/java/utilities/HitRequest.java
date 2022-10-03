package utilities;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.http.Header;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static utilities.APISetUp.request_spec;


public class HitRequest {

    public static void send_basic_auth_post_request(String body, String type) {
        Browser.generate_logs("INFO", "Parameters  = <<  " + PathAndVariable.params, "");
        if (type.toLowerCase().contains("post"))
            Browser.generate_logs("INFO", "API Body = <<  " + body, "");
        else if (type.toLowerCase().contains("put"))
            Browser.generate_logs("INFO", "API Body = <<  " + body, "");
        hitRequest(body, PathAndVariable.basic_auth, type.toLowerCase());
    }

    public static void hitRequest(String data, String auth, String type) {
        try {
            StringWriter writerRequest;
            PrintStream captor;
            writerRequest = new StringWriter();
            captor = new PrintStream(new WriterOutputStream(writerRequest), true);
            if (type.contains("json")) {
                PathAndVariable.api_type = "JSON";
                if (type.contains("post")) {
                    Response response =
                            given(request_spec(type, auth)).
                                    formParams(PathAndVariable.form_params).
                                    filter(new RequestLoggingFilter(captor)).
                                    when().
                                    post(PathAndVariable.service).
                                    then().
                                    spec(new ResponseSpecBuilder().
                                            expectContentType(ContentType.JSON).
                                            build()).
                                    extract().response();
                    PathAndVariable.message = response.getBody().asString();
                    PathAndVariable.statusCode = String.valueOf(response.getStatusCode());
                    Browser.generate_logs("INFO", response.getBody().asString(), "");
                } else if (type.contains("get")) {
                    Response response =
                            given(request_spec(type, auth)).
                                    filter(new RequestLoggingFilter(captor)).
                                    when().
                                    get(PathAndVariable.service).
                                    then().
                                    spec(new ResponseSpecBuilder().
                                            expectContentType(ContentType.JSON).
                                            build()).
                                    extract().
                                    response();
                    PathAndVariable.message = response.getBody().asString();
                    PathAndVariable.statusCode = String.valueOf(response.getStatusCode());
                } else if (type.contains("put")) {
                    Response response =
                            given(request_spec(type, auth)).
                                    auth().oauth2(PathAndVariable.basic_auth).
                                    filter(new RequestLoggingFilter(captor)).
                                    when().
                                    put(PathAndVariable.service).
                                    then().
                                    extract().response();
                    PathAndVariable.message = response.getBody().asString();
                    PathAndVariable.statusCode = String.valueOf(response.getStatusCode());
                }
            }
            Browser.generate_logs("INFO", "API Status Code = <<  " + PathAndVariable.statusCode, "");
            Browser.generate_logs("INFO", "API Response = <<  " + PathAndVariable.message, "");
        } catch (Exception e) {
            PathAndVariable.error = "Exception while hitting service for api";
            PathAndVariable.error = e.toString();
        }
    }
}
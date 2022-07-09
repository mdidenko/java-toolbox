package utils;

/*  MAVEN
    <dependency>
        <groupId>com.mashape.unirest</groupId>
        <artifactId>unirest-java</artifactId>
        <version>1.4.9</version>
    </dependency>
*/

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Hashtable;

import models.RestApiResponse;
import states.RestApiResponseFormat;

public class RestApiUtils {

    private static RestApiResponse convertStringHttpResponse(final HttpResponse<String> httpResponse) {
        return new RestApiResponse(httpResponse.getStatus(), httpResponse.getBody());
    }

    private static RestApiResponse convertJsonHttpResponse(final HttpResponse<JsonNode> httpResponse) {
        if (httpResponse.getBody().isArray()) {
            return new RestApiResponse(httpResponse.getStatus(), httpResponse.getBody().getArray().toString());
        } else {
            return new RestApiResponse(httpResponse.getStatus(), httpResponse.getBody().toString());
        }
    }

    public static RestApiResponse sendPostRequest(final RestApiResponseFormat restApiResponseFormat, final String url, final Hashtable<String, Object> fields) {
        final RestApiResponse restApiResponse;
        switch (restApiResponseFormat) {
            case STRING: {
                final HttpResponse<String> httpResponse;
                try {
                    httpResponse = Unirest.post(url).fields(fields).asString();
                } catch (UnirestException restExc) {
                    throw new RuntimeException(restExc);
                }
                restApiResponse = convertStringHttpResponse(httpResponse);
                break;
            }
            case JSON: {
                final HttpResponse<JsonNode> httpResponse;
                try {
                    httpResponse = Unirest.post(url).fields(fields).asJson();
                } catch (UnirestException restExc) {
                    throw new RuntimeException(restExc);
                }
                restApiResponse = convertJsonHttpResponse(httpResponse);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown format for receiving a response to POST request!");
            }
        }
        return restApiResponse;
    }
}

package utils;

/*  MAVEN
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.13.3</version>
    </dependency>
*/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;

public class JsonUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static String serialize(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException jsonExc) {
            throw new UncheckedIOException("JSON serialization error.", jsonExc);
        }
    }

    public static Object deserialize(final String json, final Class<?> modelClass) {
        try {
            return objectMapper.readValue(json, modelClass);
        } catch (JsonProcessingException jsonExc) {
            throw new UncheckedIOException("JSON deserialization error.", jsonExc);
        }
    }

    public static String getValue(final String json, final String keyPath) {
        final JsonNode jsonNode;
        final String exceptionDescription = String.format("Get value by \"%s\" error from \"%s\" JSON.", keyPath, json);
        try {
            jsonNode = objectMapper.readTree(json).at(keyPath);
        } catch (IOException ioException) {
            throw new UncheckedIOException(exceptionDescription, ioException);
        }
        if (jsonNode.isMissingNode()) {
            throw new IllegalArgumentException(exceptionDescription);
        }
        return jsonNode.toString();
    }
}

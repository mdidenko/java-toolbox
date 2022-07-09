package models;

/*  MAVEN
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>LATEST</version>
        <scope>provided</scope>
    </dependency>
*/

import lombok.Getter;

@Getter
public class RestApiResponse {

    private final int statusCode;
    private final String body;

    public RestApiResponse(final int statusCode, final String body) {
        this.statusCode = statusCode;
        this.body = body;
    }
}

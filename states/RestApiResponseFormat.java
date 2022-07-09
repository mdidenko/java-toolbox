package states;

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
public enum RestApiResponseFormat {

    STRING(),
    JSON();

    RestApiResponseFormat() { }
}

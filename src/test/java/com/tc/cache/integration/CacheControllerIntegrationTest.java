package com.tc.cache.integration;

import com.google.gson.Gson;
import com.tc.cache.model.CacheData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import java.nio.charset.Charset;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = {"spring.main.allow-bean-definition-overriding=true", "server.servlet.context-path=/"})
public class CacheControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void givenCacheURI_whenSendingReq_asCachePut_thenVerifyResponseStatus() {
        CacheData cacheData = new CacheData();
        cacheData.setId(0);
        cacheData.setData("A");
        Gson gson = new Gson();
        String requestJson = gson.toJson(cacheData);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestJson)
                .when()
                .put("/cache")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void givenCacheURI_whenSendingReq_asCacheGetId_thenVerifyResponseStatus() {
        given().get("/cache/0")
                .then()
                .statusCode(200);
    }
}

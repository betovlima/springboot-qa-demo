package com.example.qademo;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppTests {

    @LocalServerPort
    int port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void list_should_return_seeded_items() {
        given()
        .when()
            .get("/api/todos")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(3));
    }

    @Test
    void create_and_get_and_update_and_delete_flow() {
        // Create
        Long id =
        given()
            .contentType("application/json")
            .body("{\"title\": \"Estudar RestAssured\"}")
        .when()
            .post("/api/todos")
        .then()
            .statusCode(201)
            .body("title", equalTo("Estudar RestAssured"))
            .extract().jsonPath().getLong("id");

        // Get
        given()
        .when()
            .get("/api/todos/{id}", id)
        .then()
            .statusCode(200)
            .body("id", equalTo(id.intValue()))
            .body("done", equalTo(false));

        // Update
        given()
            .contentType("application/json")
            .body("{\"title\": \"Estudar RestAssured (OK)\", \"done\": true}")
        .when()
            .put("/api/todos/{id}", id)
        .then()
            .statusCode(200)
            .body("done", equalTo(true));

        // Delete
        given()
        .when()
            .delete("/api/todos/{id}", id)
        .then()
            .statusCode(204);
    }
}

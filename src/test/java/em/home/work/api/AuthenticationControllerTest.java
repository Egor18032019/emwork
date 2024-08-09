package em.home.work.api;

import em.home.work.model.SignInRequest;
import em.home.work.model.SignUpRequest;
import em.home.work.store.users.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        userRepository.deleteAll();

    }

    @Test
    void signUpUserShouldReturnStatus201() {
        SignUpRequest request = new SignUpRequest("me", "role@mail.ru", "empty");
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/api/auth/register")
                .then()
                .statusCode(HttpStatus.SC_CREATED);

    }

    @Test
    void signInUserShouldReturnStatus200() throws JSONException {
        SignUpRequest request = new SignUpRequest("me", "role@mail.ru", "empty");
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/api/auth/register");
        SignInRequest signInRequest = new SignInRequest("role@mail.ru", "empty");
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(signInRequest)
                .post("/api/auth/login")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}

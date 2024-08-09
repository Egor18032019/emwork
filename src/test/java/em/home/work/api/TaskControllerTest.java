package em.home.work.api;

import em.home.work.model.SignUpRequest;
import em.home.work.model.TaskRequest;
import em.home.work.model.TaskRequestForFilter;
import em.home.work.model.TaskRequestForUpdate;
import em.home.work.store.tasks.Task;
import em.home.work.store.tasks.TaskRepository;
import em.home.work.store.users.repository.UserRepository;
import em.home.work.utils.Priority;
import em.home.work.utils.Status;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {
    String token;
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
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() throws JSONException {
        RestAssured.baseURI = "http://localhost:" + port;
        taskRepository.deleteAll();
        userRepository.deleteAll();
        SignUpRequest request = new SignUpRequest("me", "role@mail.ru", "empty");
        String response = RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/api/auth/register")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .asString();
        JSONObject jsonObject = new JSONObject(response);
        token = "Bearer " + jsonObject.getString("token");
    }

    @Test
    void createTaskShouldReturnStatus201() {
        TaskRequest request = new TaskRequest("GREAT", "Dady", "HIGH", "make good");
        given()
                .contentType(ContentType.JSON)
                .body(request)
                .header("Authorization", token)
                .header("Application-Authorization", " token")
                .post("/tasks/great")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .asString();

    }
    @Test
    void updateTaskIfYouCreatorShouldReturnStatus200() {
        Task task = new Task("me", Status.GREAT, "get me task for id", Priority.HIGH, "DB", new ArrayList<>());
        taskRepository.save(task);
        TaskRequestForUpdate request = new TaskRequestForUpdate(task.getId(), Status.REVIEWED, "Papa", Priority.LOW, "update this task");
        given()
                .contentType(ContentType.JSON)
                .body(request)
                .header("Authorization", token)
                .header("Application-Authorization", " token")
                .put("/tasks/update")
                .then()
                .statusCode(HttpStatus.SC_OK);
        Task updateTask = taskRepository.findById(task.getId())
                .orElseThrow();
        Assertions.assertEquals(updateTask.getContractor(), request.getContractor());
        Assertions.assertEquals(updateTask.getStatus(), request.getStatus());
        Assertions.assertEquals(updateTask.getPriority(), request.getPriority());
        Assertions.assertEquals(updateTask.getDescription(), request.getDescription());
    }
    @Test
    void updateInvalidTaskShouldReturnStatus400() {

        TaskRequestForUpdate request = new TaskRequestForUpdate(100001L, Status.REVIEWED, "Papa", Priority.LOW, "update this task");
        given()
                .contentType(ContentType.JSON)
                .body(request)
                .header("Authorization", token)
                .header("Application-Authorization", " token")
                .put("/tasks/update")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
    @Test
    void getTaskShouldReturnStatus200() {
        Task task = new Task("Papa", Status.GREAT, "get me task for id", Priority.HIGH, "DB", new ArrayList<>());
        taskRepository.save(task);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .header("Application-Authorization", " token")
                .when()
                .get("/tasks/" + task.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    void getTaskShouldReturnStatus400() {

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .header("Application-Authorization", " token")
                .when()
                .get("/tasks/" + 400)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void getTaskByNameCreatorShouldReturnStatus200(){
        Task task = new Task("me", Status.GREAT, "get me task for id", Priority.HIGH, "DB", new ArrayList<>());
        taskRepository.save(task);
        TaskRequestForFilter request = new TaskRequestForFilter();
        request.setName(task.getCreator());
        given()
                .contentType(ContentType.JSON)
                .body(request)
                .header("Authorization", token)
                .header("Application-Authorization", " token")
                .get("/tasks/creator")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
    @Test
    void getTaskByNameContractorShouldReturnStatus200(){
        Task task = new Task("me", Status.GREAT, "get me task for id", Priority.HIGH, "DB", new ArrayList<>());
        taskRepository.save(task);
        TaskRequestForFilter request = new TaskRequestForFilter();
        request.setName(task.getContractor());
        given()
                .contentType(ContentType.JSON)
                .body(request)
                .header("Authorization", token)
                .header("Application-Authorization", " token")
                .get("/tasks/contractor")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
    @Test
    void deleteTaskShouldReturnStatus200(){
        Task task = new Task("me", Status.GREAT, "get me task for id", Priority.HIGH, "DB", new ArrayList<>());
        taskRepository.save(task);
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .header("Application-Authorization", " token")
                .delete("/tasks/"+task.getId())
                .then()
                .statusCode(HttpStatus.SC_OK);
        Assertions.assertTrue(taskRepository.findById(task.getId()).isEmpty());
    }
}

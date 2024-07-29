package Mindera.School.Mindera.service;

import Mindera.School.Mindera.dto.CourseCreationDto;
import Mindera.School.Mindera.dto.CourseDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CourseControllerIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private CourseService courseService;


    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        courseService.deleteAllCourses();
    }


    @Nested
    class CreateCourse {

        @Test
        void createCourse() {

            String courseName = "Course 1";

            CourseCreationDto courseDto = new CourseCreationDto(courseName);
            courseDto.setName("Teste");

            given()
                    .contentType("application/json")
                    .body(courseDto)
                    .when()
                    .post("/api/v1/course/add")
                    .then()
                    .statusCode(200);

            List<CourseDto> courses = given()
                    .when()
                    .get("/api/v1/course/all")
                    .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .jsonPath().getList("courses", CourseDto.class);
            assertEquals(1, courses.size());

            Assertions.assertEquals(1, courses.size());


        }

        @Test
        void createCourseWithEmptyName() {
            String courseName = "Course 1";

            CourseCreationDto courseDto = new CourseCreationDto(courseName);
            courseDto.setName("");

            given()
                    .contentType("application/json")
                    .body(courseDto)
                    .when()
                    .post("/api/v1/course/add")
                    .then()
                    .statusCode(400);
        }


        @Test
        void deleteCourse() {

            String courseName = "Course 1";
            CourseCreationDto courseDto = new CourseCreationDto(courseName);
            courseDto.setName("Teste");

            CourseDto createdCourse = given()
                    .contentType(ContentType.JSON)
                    .body(courseDto)
                    .when()
                    .post("/api/v1/course/add")
                    .then()
                    .statusCode(200)
                    .extract().body().as(CourseDto.class);

            String courseId = String.valueOf(createdCourse.getId());

            System.out.println("Created Course ID: " + courseId);

            given()
                    .when()
                    .delete("/api/v1/course/" + courseId)
                    .then()
                    .statusCode(204);

            List<CourseDto> courses = given()
                    .when()
                    .get("/api/v1/course/all")
                    .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .jsonPath().getList("", CourseDto.class);

            assertEquals(0, courses.size());
        }

        @Test
        void deleteCourseWithEmptyId() {
            given()
                    .when()
                    .delete("/api/v1/course/0")
                    .then()
                    .statusCode(404);
        }

        @Test
        void deleteCourseWithInvalidId() {
            given()
                    .when()
                    .delete("/api/v1/course/invalidId")
                    .then()
                    .statusCode(400);
        }


        @Test
        void deleteCourseWithNonExistingId() {
            given()
                    .when()
                    .delete("/api/v1/course/1")
                    .then()
                    .statusCode(404);
        }

        @Test
        void updateCourse() {

            String courseName = "Course 1";
            CourseCreationDto courseDto = new CourseCreationDto(courseName);
            courseDto.setName("Teste");

            CourseDto createdCourse = given()
                    .contentType(ContentType.JSON)
                    .body(courseDto)
                    .when()
                    .post("/api/v1/course/add")
                    .then()
                    .statusCode(200)
                    .extract().body().as(CourseDto.class);

            String courseId = String.valueOf(createdCourse.getId());

            System.out.println("Created Course ID: " + courseId);

            CourseDto updatedCourse = given()
                    .contentType(ContentType.JSON)
                    .body(courseDto)
                    .when()
                    .put("/api/v1/course/" + courseId)
                    .then()
                    .statusCode(200)
                    .extract().body().as(CourseDto.class);

            assertEquals("Teste", updatedCourse.getName());

        }


        @Test
        void getCourse_ById() {

            String courseName = "Course 1";
            CourseCreationDto courseDto = new CourseCreationDto(courseName);
            courseDto.setName("Teste");

            CourseDto createdCourse = given()
                    .contentType(ContentType.JSON)
                    .body(courseDto)
                    .when()
                    .post("/api/v1/course/add")
                    .then()
                    .statusCode(200)
                    .extract().body().as(CourseDto.class);

            Long courseId = createdCourse.getId();

            System.out.println("Created Course ID: " + courseId);

            CourseDto course = given()
                    .when()
                    .get("/api/v1/course/" + courseId)
                    .then()
                    .statusCode(200)
                    .extract().body().as(CourseDto.class);

            assertEquals(courseId, course.getId());

        }

        @Test
        void getCourse_ByIdWithInvalidId() {
            given()
                    .when()
                    .get("/api/v1/course/invalidId")
                    .then()
                    .statusCode(400);
        }

        @Test
        void getCourse_ByIdWithNonExistingId() {
            given()
                    .when()
                    .get("/api/v1/course/1")
                    .then()
                    .statusCode(404);
        }


    }
}
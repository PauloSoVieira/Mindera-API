package Mindera.School.Mindera.service;


import Mindera.School.Mindera.dto.CourseCreationDto;
import Mindera.School.Mindera.dto.CourseDto;
import Mindera.School.Mindera.dto.TeacherCreateDto;
import Mindera.School.Mindera.dto.TeacherDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class TeacherControllerIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;


    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        teacherService.deleteAll();
    }


    @Nested
    class crudTeacher {

        @Test
        void get_AllTeachers_returns200() {
            TeacherCreateDto teacherCreateDto = new TeacherCreateDto();
            teacherCreateDto.setName("John");

            given()
                    .contentType(ContentType.JSON)
                    .body(teacherCreateDto)
                    .when()
                    .post("/api/v1/teacher")
                    .then()
                    .statusCode(200);

            List teachers =
                    given()
                            .when()
                            .get("/api/v1/teacher/all")
                            .then()
                            .statusCode(200)
                            .extract()
                            .body()
                            .as(List.class);

            Assertions.assertEquals(1, teachers.size());
        }


        @Test
        void get_AllTeacherEmptyList() {
            List<TeacherCreateDto> createDtos = given()
                    .when()
                    .get("/api/v1/teacher/all")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getList(".", TeacherCreateDto.class);
        }

        @Test
        void get_Teacher_ById() {
            TeacherCreateDto teacherCreateDto = new TeacherCreateDto();
            teacherCreateDto.setName("Paulo");

            String teacherId = given()
                    .contentType(ContentType.JSON)
                    .body(teacherCreateDto)
                    .when()
                    .post("/api/v1/teacher")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getString("id");

            TeacherDto teacherDto = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get("/api/v1/teacher/" + teacherId)
                    .then()
                    .statusCode(200)
                    .extract().body().as(TeacherDto.class);

            Long expectedId = Long.parseLong(teacherId);
            Assertions.assertEquals(teacherDto.getName(), "Paulo");
            Assertions.assertEquals(teacherDto.getId(), expectedId);

        }


        @Test
        void get_Teacher_ById_NotFound() {
            given()
                    .when()
                    .get("/api/v1/teacher/1")
                    .then()
                    .statusCode(404);
        }

        @Test
        void get_Teacher_ById_Empty() {
            given()
                    .when()
                    .get("/api/v1/teacher/0")
                    .then()
                    .statusCode(404);
        }

        @Test
        void delete_Teacher_ById() {
            TeacherCreateDto teacherCreateDto = new TeacherCreateDto();
            teacherCreateDto.setName("Paulo");

            String teacherId = given()
                    .contentType(ContentType.JSON)
                    .body(teacherCreateDto)
                    .when()
                    .post("/api/v1/teacher")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getString("id");

            given()
                    .when()
                    .delete("/api/v1/teacher/" + teacherId)
                    .then()
                    .statusCode(200);

            given()
                    .when()
                    .get("/api/v1/teacher/" + teacherId)
                    .then()
                    .statusCode(404);


        }

        @Test
        void update_Teacher_ById() {
            TeacherCreateDto teacherCreateDto = new TeacherCreateDto();
            teacherCreateDto.setName("Paulo");

            String teacherId = given()
                    .contentType(ContentType.JSON)
                    .body(teacherCreateDto)
                    .when()
                    .post("/api/v1/teacher")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getString("id");

            TeacherDto teacherDto = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .get("/api/v1/teacher/" + teacherId)
                    .then()
                    .statusCode(200)
                    .extract().body().as(TeacherDto.class);

            teacherDto.setName("Paulo2");

            given()
                    .contentType(ContentType.JSON)
                    .body(teacherDto)
                    .when()
                    .put("/api/v1/teacher/update/" + teacherId)
                    .then()
                    .statusCode(200);

            given()
                    .when()
                    .get("/api/v1/teacher/" + teacherId)
                    .then()
                    .statusCode(200)
                    .body("name", equalTo("Paulo2"));
        }


        @Test
        void add_Teacher_To_Course() {

            TeacherCreateDto teacher = new TeacherCreateDto();
            teacher.setName("Paulo");

            TeacherDto teacherDto = given()
                    .contentType(ContentType.JSON)
                    .body(teacher)
                    .when()
                    .post("/api/v1/teacher")
                    .then()
                    .statusCode(200)
                    .extract().body().as(TeacherDto.class);

            String teacherId = teacherDto.getId().toString();

            CourseCreationDto course = new CourseCreationDto("Test Course");

            CourseDto courseDto = given()
                    .contentType(ContentType.JSON)
                    .body(course)
                    .when()
                    .post("/api/v1/course/add")
                    .then()
                    .statusCode(200)
                    .extract().body().as(CourseDto.class);

            String courseId = courseDto.getId().toString();

            TeacherDto updatedTeacherDto = given()
                    .contentType(ContentType.JSON)
                    .when()
                    .put("/api/v1/teacher/" + teacherId + "/course/" + courseId)
                    .then()
                    .statusCode(200)
                    .extract().body().as(TeacherDto.class);

            Assertions.assertEquals(1, updatedTeacherDto.getCourses().size());
            Assertions.assertEquals("Test Course", updatedTeacherDto.getCourses().get(0).getName());
        }

    }


}
package Mindera.School.Mindera.service;

import Mindera.School.Mindera.dto.StudentCreationDto;
import Mindera.School.Mindera.dto.StudentDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class StudentControllerIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private StudentService studentService;


    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        studentService.deleteAllStudents();
    }

    @Nested
    class crudStudents {
        @Test
        void getAllStudentsReturnsEmptyList() {
            List<StudentDto> students = given()
                    .when()
                    .get("/api/v1/students/all")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getList(".", StudentDto.class);

            Assertions.assertEquals(0, students.size());
        }

        @Test
        void getAllStudentsReturnsAllStudents() {
            StudentCreationDto student = new StudentCreationDto(
                    null,
                    "Mindera",
                    null
            );

            studentService.addStudent(student);
            List<StudentDto> students = given()
                    .when()
                    .get("/api/v1/students/all")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getList(".", StudentDto.class);

            Assertions.assertEquals(1, students.size());

        }

        @Test
        void createStudentReturns200AndStudent() {
            StudentCreationDto student = new StudentCreationDto(
                    null,
                    "Mindera",
                    null
            );

            StudentDto studentDto =

                    given()
                            .contentType(ContentType.JSON)
                            .body(student)
                            .when()
                            .post("/api/v1/students/")
                            .then()
                            .statusCode(200).extract().body().as(StudentDto.class);

            Assertions.assertEquals(studentDto.getName(), "Mindera");
            Assertions.assertNotNull(studentDto.getId());
            // Assertions.assertEquals(0, studentDto.getCourses().size());

            List<StudentDto> students = given()
                    .when()
                    .get("/api/v1/students/all")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getList(".", StudentDto.class);

            Assertions.assertEquals(1, students.size());

        }
    }

    @Nested
    class validation {
        @Test
        void createStudentWithNullName() {
            StudentCreationDto student = new StudentCreationDto(
                    null,
                    null,
                    null
            );

            given()
                    .contentType(ContentType.JSON)
                    .body(student)
                    .when()
                    .post("/api/v1/students/")
                    .then()
                    .statusCode(400);
        }
    }
}
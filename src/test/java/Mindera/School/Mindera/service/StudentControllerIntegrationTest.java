package Mindera.School.Mindera.service;

import Mindera.School.Mindera.dto.CourseCreationDto;
import Mindera.School.Mindera.dto.CourseDto;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class StudentControllerIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;


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

            assertEquals(0, students.size());
        }

        @Test
        void getAll_StudentsReturns_AllStudents() {
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

            assertEquals(1, students.size());

        }


        @Test
        void getStudentById_ReturnsStudent() {


            StudentCreationDto student = new StudentCreationDto(
                    null,
                    "Mindera",
                    null
            );

            String studentId = given()
                    .contentType(ContentType.JSON)
                    .body(student)
                    .when()
                    .post("/api/v1/students/")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getString("id");

            StudentDto studentDto = given()
                    .when()
                    .get("/api/v1/students/" + studentId)
                    .then()
                    .statusCode(200)
                    .extract().body().as(StudentDto.class);
            assertEquals(student.getName(), studentDto.getName());
            

        }

        @Test
        void getStudentById_NotFound_String() {

            StudentCreationDto student = new StudentCreationDto(
                    null,
                    "Mindera",
                    null
            );

            String studentId = given()
                    .contentType(ContentType.JSON)
                    .body(student)
                    .when()
                    .post("/api/v1/students/")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getString("id");

            String nonExistentStudentId = "nonexistent-id";


            given()
                    .when()
                    .get("/api/v1/students/" + nonExistentStudentId)
                    .then()
                    .statusCode(400);


        }

        @Test
        void getStudentById_notFound() {

            StudentCreationDto student = new StudentCreationDto(
                    null,
                    "Mindera",
                    null
            );

            String studentId = given()
                    .contentType(ContentType.JSON)
                    .body(student)
                    .when()
                    .post("/api/v1/students/")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getString("id");

            Long nonExistentStudentId = 123456789L;


            given()
                    .when()
                    .get("/api/v1/students/" + nonExistentStudentId)
                    .then()
                    .statusCode(404);

        }

        @Test
        void delete_Student_Returns204() {
            StudentCreationDto student = new StudentCreationDto(
                    null,
                    "Mindera",
                    null
            );

            StudentDto studentDto = given()
                    .contentType(ContentType.JSON)
                    .body(student)
                    .when()
                    .post("/api/v1/students/")
                    .then()
                    .statusCode(200)
                    .extract().body().as(StudentDto.class);

            String studentId = given()
                    .contentType(ContentType.JSON)
                    .body(studentDto)
                    .when()
                    .post("/api/v1/students/")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getString("id");


            given()
                    .when()
                    .delete("/api/v1/students/" + studentId)
                    .then()
                    .statusCode(204);

/*
            List<StudentDto> students = given()
                    .when()
                    .get("/api/v1/students/all")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getList(".", StudentDto.class);

            assertEquals(0, students.size());

 */

        }


        @Test
        void addCourseToStudent_Returns200() {

            StudentCreationDto student = new StudentCreationDto(
                    null,
                    "Mindera",
                    null
            );


            StudentDto studentDto = given()
                    .contentType(ContentType.JSON)
                    .body(student)
                    .when()
                    .post("/api/v1/students/")
                    .then()
                    .statusCode(200)
                    .extract().body().as(StudentDto.class);

            String studentId = given()
                    .contentType(ContentType.JSON)
                    .body(studentDto)
                    .when()
                    .post("/api/v1/students/")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getString("id");


            StudentDto studentDto2 = given()

                    .when()
                    .get("/api/v1/students/" + studentId)
                    .then()
                    .statusCode(200)
                    .extract().body().as(StudentDto.class);


            CourseCreationDto course = new CourseCreationDto("Test Course");


            CourseDto courseDto = given()
                    .contentType(ContentType.JSON)
                    .body(course)
                    .when()
                    .post("/api/v1/course/add")
                    .then()
                    .statusCode(200)
                    .extract().body().as(CourseDto.class);


            String courseId = given()
                    .contentType(ContentType.JSON)
                    .body(courseDto)
                    .when()
                    .post("/api/v1/students/")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getString("id");

            CourseDto courseDto2 = given()

                    .when()
                    .get("/api/v1/course/" + courseId)
                    .then()
                    .statusCode(200)
                    .extract().body().as(CourseDto.class);

            studentDto2.setCourses(List.of(courseDto2));

            given()
                    .contentType(ContentType.JSON)
                    .body(studentDto2)
                    .when()
                    .put("/api/v1/students/" + studentId)
                    .then()
                    .statusCode(200)
                    .extract().body().as(StudentDto.class);

            Assertions.assertEquals(1, studentDto2.getCourses().size());

        }


        @Test
        void update_Student_Returns200AndStudent() {

            StudentCreationDto student = new StudentCreationDto(
                    null,
                    "asdas",
                    null
            );

            String studentId = given()
                    .contentType(ContentType.JSON)
                    .body(student)
                    .when()
                    .post("/api/v1/students/")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getString("id");

            student.setName("Mindera2");

            given()
                    .contentType(ContentType.JSON)
                    .body(student)
                    .when()
                    .put("/api/v1/students/" + studentId)
                    .then()
                    .statusCode(200);


            StudentDto updatedStudent = given()
                    .when()
                    .get("/api/v1/students/" + studentId)
                    .then()
                    .statusCode(200)
                    .extract().body().as(StudentDto.class);

            assertEquals("Mindera2", updatedStudent.getName());


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

        @Test
        void createStudentWithNumberName() {
            StudentCreationDto student = new StudentCreationDto(
                    null,
                    "23",
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

        @Test
        void create_Student_Returns200AndStudent() {
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

            Assertions.assertNotNull(studentDto.getId());
            // Assertions.assertEquals(0, studentDto.getCourses().size());

            List<StudentDto> students = given()
                    .when()
                    .get("/api/v1/students/all")
                    .then()
                    .statusCode(200)
                    .extract().body().jsonPath().getList(".", StudentDto.class);

            assertEquals(1, students.size());


        }

    }
}
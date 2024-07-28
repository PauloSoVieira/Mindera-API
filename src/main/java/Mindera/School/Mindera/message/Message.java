package Mindera.School.Mindera.message;

public enum Message {
    COURSE_NOT_FOUND("Course not found"),
    COURSE_CANNOT_BE_FOUND("Course cannot be found"),
    STUDENT_CANNOT_BE_FOUND("Student cannot be found"),
    TEACHER_CANNOT_BE_FOUND("Teacher cannot be found"),
    TEACHER_CANT_BE_NULL("Teacher can't be null"),
    STUDENT_CANT_BE_NULL("Student can't be null"),
    STUDENT_NAME_CANT_BE_NUMBER("Student name can't be number"),
    BAD_REQUEST("Bad request");


    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

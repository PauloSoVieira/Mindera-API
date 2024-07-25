package Mindera.School.Mindera.exception;

import Mindera.School.Mindera.message.Message;

public class StudentException extends RuntimeException {

    public StudentException(Message message) {
        super(message.getMessage());
    }
}

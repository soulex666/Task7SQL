package ua.com.foxminded.task7sql.validator;

public class DBRuntimeException extends RuntimeException {
    public DBRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}

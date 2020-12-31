package ua.com.foxminded.task7sql.validator;

public class DBRuntimeException extends RuntimeException {

    public DBRuntimeException() {
    }

    public DBRuntimeException(String message) {
        super(message);
    }

    public DBRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBRuntimeException(Throwable cause) {
        super(cause);
    }
}

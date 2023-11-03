package su.ezhidze.movieApi.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class BadArgumentException extends RuntimeException {
    public BadArgumentException(String message) {
        super(message);
    }
}

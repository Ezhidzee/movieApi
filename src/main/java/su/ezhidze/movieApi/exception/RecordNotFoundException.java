package su.ezhidze.movieApi.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String message) {
        super(message);
    }
}

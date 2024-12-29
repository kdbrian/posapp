package io.apptales.minipos.util.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> NoResourceFoundException(
            NoResourceFoundException exception
    ) {
        return new ResponseEntity<>("Missing resource " + exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}

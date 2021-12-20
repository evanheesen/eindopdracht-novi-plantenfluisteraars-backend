package nl.evanheesen.plantenfluisteraars.controller;

import nl.evanheesen.plantenfluisteraars.exception.FileStorageException;
import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.exception.UserNotFoundException;
import nl.evanheesen.plantenfluisteraars.exception.UsernameExistsAlready;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = FileStorageException.class)
    public ResponseEntity<Object> exception(FileStorageException exception) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<Object> exception(RecordNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UsernameExistsAlready.class)
    public ResponseEntity<Object> exception(UsernameExistsAlready exception) {
        return new ResponseEntity<>(exception.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> exception(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}

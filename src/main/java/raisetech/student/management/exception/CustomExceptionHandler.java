package raisetech.student.management.exception;



import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class CustomExceptionHandler {
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<String> handleCustomException(CustomException ex){
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<String> handleDuplicateKeyException(DuplicateKeyException ex){
    return ResponseEntity.status(HttpStatus.CONFLICT).body("既に受講生として登録済みです");
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex){


    StringBuilder message = new StringBuilder();
    for(ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()){
      message.append(constraintViolation.getMessage());
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.toString());
  }
}









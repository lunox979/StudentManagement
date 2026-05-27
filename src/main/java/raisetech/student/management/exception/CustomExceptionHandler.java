package raisetech.student.management.exception;



import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raisetech.student.management.exception.ValidationErrorResponse.ValidationFieldError;


@RestControllerAdvice
public class CustomExceptionHandler {
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ValidationErrorResponse> handleCustomException(CustomException ex){
    HttpStatus status = HttpStatus.NOT_FOUND;
    List<ValidationFieldError> validationFieldError = List.of(new ValidationFieldError(ex.getMessage()));
    ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(status.value(),validationFieldError,LocalDateTime.now());
    return ResponseEntity.status(status.value()).body(validationErrorResponse);
  }

  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<ValidationErrorResponse> handleDuplicateKeyException(DuplicateKeyException ex){
    HttpStatus status = HttpStatus.CONFLICT;
    List<ValidationFieldError> validationFieldError = List.of(new ValidationFieldError("message: 既にメールアドレスが存在しています"));

    ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(status.value(),validationFieldError,LocalDateTime.now());
    return ResponseEntity.status(status.value()).body(validationErrorResponse);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(ConstraintViolationException ex){

    HttpStatus status = HttpStatus.BAD_REQUEST;
    StringBuilder message = new StringBuilder();
    for(ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()){
      message.append(constraintViolation.getMessage());
    }
    List<ValidationFieldError> validationErrors = List.of(new ValidationFieldError(message.toString()));
    ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(status.value(),validationErrors,LocalDateTime.now());
    return ResponseEntity.status(status.value()).body(validationErrorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
    HttpStatus status = HttpStatus.BAD_REQUEST;
    List<ValidationFieldError> fieldErrors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(fieldError -> new ValidationFieldError(
            fieldError.getDefaultMessage()
        )).toList();

    ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(status.value(),fieldErrors,LocalDateTime.now());
    return ResponseEntity.status(status).body(validationErrorResponse);
  }
}









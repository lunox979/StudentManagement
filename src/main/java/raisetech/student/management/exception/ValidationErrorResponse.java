package raisetech.student.management.exception;


import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorResponse(
  int status,
  List<ValidationFieldError> fieldErrors,
  LocalDateTime timestamp
) {
  public record ValidationFieldError(
      String message
  ){
  }
}



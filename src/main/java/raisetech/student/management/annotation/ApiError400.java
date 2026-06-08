package raisetech.student.management.annotation;

import io.swagger.v3.oas.annotations.media.Content;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import raisetech.student.management.exception.ValidationErrorResponse;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "400",
    description = "入力したデータの形式が正しくありません。",
    content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ValidationErrorResponse.class)
    )
)
public @interface ApiError400 {}

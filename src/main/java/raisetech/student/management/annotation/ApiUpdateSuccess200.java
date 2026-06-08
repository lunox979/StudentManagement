package raisetech.student.management.annotation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "200",
    description = "受講生の情報の更新に成功しました",
    content = @Content(
        mediaType = "text/plain",
        schema = @Schema(implementation = String.class,
            example = "更新処理が成功しました")
      )
  )
public @interface ApiUpdateSuccess200 {}


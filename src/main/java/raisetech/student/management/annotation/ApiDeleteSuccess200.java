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
@ApiResponse(
    responseCode = "200",
    description = "論理削除処理が成功しました",
    content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = String.class,
            example = "削除処理が完了しました")
    )
)




public @interface ApiDeleteSuccess200 {}

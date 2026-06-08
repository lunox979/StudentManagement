package raisetech.student.management.annotation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import raisetech.student.management.exception.ValidationErrorResponse;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "409",
    description = "登録済みの削除済みの受講生です。",
    content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = ValidationErrorResponse.class),
        examples = @ExampleObject(value = """
        {
            "status": 409,
                "fieldErrors": [
                    {
                        "message": "既に削除済みの受講生なため、削除処理ができません。"
                    }
                ],
                "timestamp": "2026-06-05T13:05:13.59553"
        }
        """)
    )

)
public @interface ApiError409AlreadyDeleted {}

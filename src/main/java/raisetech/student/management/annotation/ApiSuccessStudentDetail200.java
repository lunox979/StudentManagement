package raisetech.student.management.annotation;



import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import raisetech.student.management.domain.StudentDetail;



@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
    responseCode = "200",
    description = "受講生の登録に成功したことを表す",
    content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = StudentDetail.class)
    )


)

public @interface ApiSuccessStudentDetail200 {}


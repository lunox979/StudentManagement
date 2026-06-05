package raisetech.student.management.exception;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "バリデーションエラー時のJSON形式のエラーレスポンス")
public record ValidationErrorResponse(
  @Schema(description = "HTTPステータスコード",example = "400")
  int status,

  @Schema(description = "発生したエラーのリスト")
  List<ValidationFieldError> fieldErrors,

  @Schema(description = "エラーが発生した時刻",example = "2026-05-20T19:36:20")
  LocalDateTime timestamp
) {
  public record ValidationFieldError(
      @Schema(description = "エラーメッセージ",example = "入力したデータの形式が正しくありません.")
      String message
  ){
  }
}




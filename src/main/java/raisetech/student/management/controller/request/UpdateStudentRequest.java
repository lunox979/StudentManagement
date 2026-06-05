package raisetech.student.management.controller.request;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import raisetech.student.management.controller.request.RegisterStudentRequest.Register;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;


@Getter
@Setter
public class UpdateStudentRequest {
  public interface Update extends Register {}


  @Schema(
      description = "ID　UUIDを自動付与",
      example = "5998fd5d-a2cd-11ef-b71f-6825f345144"
  )
  private String id;

  @Schema(description = "氏名", example = "山田太郎")
  @NotBlank(message = "名前を入力してください")
  private String name;

  @Schema(description = "フリガナ、 カタカナと半角・全角スペースのみ許可",
      example = "ヤマダタロウ")
  @Pattern(
      regexp = "^[ァ-ヶー\\s　]+$",
      message = "カタカナとスペースのみを入力してください"
  )
  @NotBlank(message = "フリガナを入力してください")
  private String kanaName;

  @Schema(description = "ニックネーム",example = "たろ")
  private String nickname;

  @Schema(description = "メールアドレス", example = "yamada@example.com")
  @NotBlank (message = "メールアドレスを入力してください")
  @Email(message = "メールアドレスの形式が正しくありません")
  private String email;

  @Schema(description = "住んでいる地域の入力", example = "東京都港区")
  @NotBlank (message = "地域を入力してください")
  private String livingArea;

  @Schema(description = "年齢 0~150までを許可",example = "32")
  @Range(min = 0,max = 150,message = "0~150歳の範囲から値を入力してください")
  private Integer age;

  @Schema(description = "性別",example = "男性")
  private String sex;

  @Schema(description = "備考",example = "プログラミングを学習しています")
  private String remark;

  @Schema(description = "受講生の登録するコース",example = "Javaコース")
  @NotBlank(message = "登録するコースを選択してください")
  private String courseName;

}



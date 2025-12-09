package raisetech.student.management.data;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
public class Student {
  @NotBlank(message = "名前を入力してください")
  private String name;
  private String id;
  @NotBlank(message = "フリガナを入力してください")
  private String kanaName;

  private String nickname;
  @NotBlank(message = "メールアドレスを入力してください")
  @Email(message = "メールアドレスの形式が正しくありません")
  private String email;
  @NotBlank(message = "地域を入力してください")
  private String livingArea;
  private Integer age;
  @NotBlank(message = "性別を入力してください")
  private String sex;
  private String remark;
  private boolean isDeleted;




}







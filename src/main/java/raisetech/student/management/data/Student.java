package raisetech.student.management.data;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter// get setを自動で生成してくれる
public class Student {

  private String id;
  @NotBlank(message = "名前を入力してください")
  private String name;
  @NotBlank(message = "フリガナを入力してください")
  private String kanaName;
  private String nickname;
  @NotBlank (message = "メールアドレスを入力してください")
  @Email(message = "メールアドレスの形式が正しくありません")
  private String email;
  @NotBlank (message = "地域を入力してください")
  private String livingArea;
  private Integer age;
  private String sex;
  private String remark;
  private boolean isDeleted;





}







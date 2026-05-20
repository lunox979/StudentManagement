package raisetech.student.management.controller;


import jakarta.validation.constraints.Pattern;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;


/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるControllerです．
 */
@Validated
@RestController
public class StudentController {

  private StudentService service;


  /**
   * コンストラクタ
   * @param service 受講生サービス
   */
  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索
   * 全件検索を行うので、条件指定は行わない．
   * @return 受講生詳細一覧(全件)
   */



  @GetMapping("/studentList")
  public List<StudentDetail> getStudentDetailList() {
    //リクエスト加工処理 入力チェックとか入ったりする
    return service.searchStudentDetailList();



  }

  /**
   * 受講生詳細の一覧検索
   * IDにひもづく任意の受講生の情報を取得する
   * @param userId 受講生ID
   * @return 受講生
   */

  @GetMapping("/student/{userId}")
  public StudentDetail getStudent(@PathVariable @Pattern(regexp = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-"
      + "[0-9a-fA-F]{12}", message="idの形式が正しくありません．") String userId){

    return service.searchStudentDetail(userId);

  }


  /**
   * 受講生詳細の登録を行います．
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Validated StudentDetail studentDetail){

  StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
  return ResponseEntity.ok(responseStudentDetail);

  }

  /**
   * 受講生詳細の更新を行います．
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Validated  StudentDetail studentDetail){

      service.updateStudent(studentDetail);
      return ResponseEntity.ok("更新処理が成功しました．");
  }


  /**
   *
   * @param id 受講生ID
   * @return 全受講生の詳細情報
   */
  @PostMapping("/softDeleteStudent/{userId}")
  public ResponseEntity<String> softDeleteStudent(@PathVariable("userId")
      @Pattern(regexp = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}",
          message="idの形式が正しくありません．") String id){

    boolean isDeleted = service.searchStudent(id).isDeleted();
    service.softDeleteStudent(id,!isDeleted);

    if(isDeleted){
      return ResponseEntity.ok("論理削除を解除しました");
    }
    else{
      return ResponseEntity.ok("論理削除が完了しました");
    }
  }







}












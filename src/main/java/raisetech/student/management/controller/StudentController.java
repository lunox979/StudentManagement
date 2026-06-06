package raisetech.student.management.controller;


import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.Pattern;

import java.util.List;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.annotation.ApiDeleteSuccess200;
import raisetech.student.management.annotation.ApiError400;
import raisetech.student.management.annotation.ApiError404;
import raisetech.student.management.annotation.ApiError409AlreadyActive;
import raisetech.student.management.annotation.ApiError409AlreadyDeleted;
import raisetech.student.management.annotation.ApiError409DuplicateEmail;
import raisetech.student.management.annotation.ApiRestoreSuccess200;
import raisetech.student.management.annotation.ApiUpdateSuccess200;
import raisetech.student.management.annotation.ApiSuccessStudentDetail200;
import raisetech.student.management.annotation.ApiSuccessStudentDetailList200;
import raisetech.student.management.controller.mapper.StudentMapper;
import raisetech.student.management.controller.request.RegisterStudentRequest;
import raisetech.student.management.controller.request.UpdateStudentRequest;
import raisetech.student.management.data.Student;

import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.exception.CustomException;

import raisetech.student.management.service.StudentService;


/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるControllerです．
 */
@Validated
@RestController
@RequiredArgsConstructor

public class StudentController {

  private final  StudentService service;
  private final  StudentMapper studentMapper;
  /**
   * コンストラクタ
   * @param service 受講生サービス
   */



  /**
   * 受講生詳細の一覧検索
   * 全件検索を行うので、条件指定は行わない．
   * @return 受講生詳細一覧(全件)
   */


  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
  @ApiSuccessStudentDetailList200
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentDetailList(){
    //リクエスト加工処理 入力チェックとか入ったりする
    return service.searchStudentDetailList();


  }


  /**
   * 受講生詳細の一覧検索
   * IDにひもづく任意の削除済みでない受講生の情報を取得する
   * @param id 受講生ID
   * @return 受講生
   */
  @Operation(summary = "単一検索",description ="受講生の単一検索を行います。")
  @ApiSuccessStudentDetail200
  @ApiError404
  @ApiError400
  @GetMapping("/student/{id}")
  public ResponseEntity<StudentDetail> getStudent(@PathVariable @Pattern(regexp = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-"
      + "[0-9a-fA-F]{12}", message="idの形式が正しくありません。") String id){
    StudentDetail studentDetail = service.searchStudentDetail(id);
    if(studentDetail == null || studentDetail.getStudent() == null){
      throw new CustomException("該当するIDが存在しません。", HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(studentDetail);

  }


  /**
   * 受講生詳細の登録を行います．
   * @param registerStudentRequest 受講生登録時のDTO
   * @return 実行結果
   */
  @Operation(summary = "受講生登録",description = "受講生を登録します。")
  @ApiSuccessStudentDetail200
  @ApiError400
  @ApiError409DuplicateEmail

  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Validated RegisterStudentRequest registerStudentRequest){
    StudentDetail studentDetail = toRegisterStudentDetail(registerStudentRequest);
    StudentDetail responsesStudentDetail = service.registerStudent(studentDetail);

    return ResponseEntity.ok(responsesStudentDetail);

  }

  private StudentDetail toRegisterStudentDetail(RegisterStudentRequest registerStudentRequest) {
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    student = studentMapper.toStudent(registerStudentRequest);
    studentCourse = studentMapper.toStudentCourse(registerStudentRequest);
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setRegisterStudentCourse(studentCourse);
    return studentDetail;
  }


  /**
   * 受講生詳細の更新を行います．
   * @param updateStudentRequest 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生更新", description = "受講生情報の更新を行います。")
  @ApiError404
  @ApiError409DuplicateEmail
  @ApiError400
  @ApiUpdateSuccess200
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Validated UpdateStudentRequest updateStudentRequest){

    Student student = service.searchStudent(updateStudentRequest.getId());
    if(student == null){
      throw new CustomException("該当するIDが存在しません。", HttpStatus.NOT_FOUND);
    }
    if(student.isDeleted()){
      throw new CustomException("既に削除済みの受講生情報のため、更新できません。", HttpStatus.CONFLICT);
    }

    StudentDetail studentDetail = toUpdateStudentDetail(updateStudentRequest);
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  private StudentDetail toUpdateStudentDetail(UpdateStudentRequest updateStudentRequest) {
    Student student = new Student();
    student = studentMapper.toUpdateInformation(updateStudentRequest);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseName(updateStudentRequest.getCourseName());
    studentDetail.setStudentCourseList(List.of(studentCourse));
    return studentDetail;
  }


  /**
   *
   * @param id 受講生ID
   * @return 全受講生の詳細情報
   */

  @Operation(summary = "受講生の論理削除",description = "受講生の情報の論理削除を行います。")
  @ApiError400
  @ApiDeleteSuccess200
  @ApiError404
  @ApiError409AlreadyDeleted
  @PatchMapping ("/softDeleteStudent/{id}")
  public ResponseEntity<String> softDeleteStudent(@PathVariable("id")
      @Pattern(regexp = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}",
          message="idの形式が正しくありません。") String id){

    Student student = service.searchStudentIncludeDeleted(id);
    if(student == null){
      throw new CustomException("該当するIDが見つかりません。",HttpStatus.NOT_FOUND);
    }
    if(student.isDeleted()){
      throw new CustomException("既に削除済みの受講生ため、削除処理ができません。",HttpStatus.CONFLICT);
    }
    service.softDeleteStudent(id,true);

    return ResponseEntity.ok("論理削除が完了しました。");

  }


  @Operation(summary = "受講生の復元", description = "削除済み受講生の復元を行います。")
  @ApiError400
  @ApiError409AlreadyActive
  @ApiRestoreSuccess200
  @ApiError404
  @PatchMapping ("/student/{id}/restore")
  public ResponseEntity<String> studentRestore(@PathVariable("id")
      @Pattern(regexp = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}",
      message = "idの形式が正しくありません。")String id){
    Student student = service.searchStudentIncludeDeleted(id);
    if(student == null){
      throw new CustomException("該当するIDが存在しません。", HttpStatus.NOT_FOUND);
    }
    if(!student.isDeleted()){
      throw new CustomException("既に登録済みの受講生なため、復元処理ができません。", HttpStatus.CONFLICT);
    }
    service.restoreStudent(id,false);
    return ResponseEntity.ok("受講生の復元に成功しました。");
  }

}












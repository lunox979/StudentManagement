package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.controller.converter.StudentsConverter;

import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです．
 * 受講生の検索や登録・更新処理を行います．
 */
@Service
public class StudentService {

  private StudentsConverter converter;
  private StudentRepository repository;

  @Autowired //SpringBootから引っ張ってくることを表す
  public StudentService(StudentRepository repository,StudentsConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細の一覧検索です．
   * 全件検索を行うので条件指定を行いません．
   *
   * @return 受講生一覧(全件)
   */

  public List<StudentDetail> searchStudentDetailList() {
    //絞り込みをする．年齢が30代のみを抽出する
    //抽出したリストをコントローラに返す.
    List<Student> studentList= repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(studentList, studentCourseList);


  }

  /**
   *
   * @param id 受講生ID
   * @return 受講生情報
   */
  public Student searchStudentIncludeDeleted(String id){

    return repository.searchStudentIncludeDeleted(id);
  }



  /**
   * 受講生詳細検索です．
   * IDにひもづく受講生情報を取得した後、その受講生に紐付く受講生コース情報を取得して設定します．
   *
   * @param id 受講生ID
   * @return  受講生詳細
   */
  public StudentDetail searchStudentDetail(String id){
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourse = repository.matchStudentCourses(id);
    return new StudentDetail(student, studentCourse, new StudentCourse());
  }






  /**
   * 受講生詳細の更新を行います．
   * 受講生と受講生コース情報をそれぞれ更新する．
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail){


    repository.updateRegisterStudent(studentDetail.getStudent());

    studentDetail.getStudentCourseList()
        .forEach(studentCourse -> repository.updateRegisterStudentCourse(studentCourse));


  }

  /**
   * 受講生情報
   *
   * の論理削除を行います．
   * @param id 受講生ID
   */
  @Transactional
  public void softDeleteStudent(String id){


    repository.softDeleteStudent(id);




  }


  /**
   * 受講生詳細の登録を行います
   * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報と紐づける値を設定します．
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail){

    UUID uuid  = UUID.randomUUID();
    studentDetail.getStudent().setId(uuid.toString());
    initStudentsCourse(studentDetail, uuid);
    repository.registerStudent(studentDetail.getStudent());
    repository.registerStudentCourse(studentDetail.getRegisterStudentCourse());
    Student student = repository.searchStudent(uuid.toString());

    List<StudentCourse> studentCourse = repository.matchStudentCourses(uuid.toString());

    return new StudentDetail(student, studentCourse, new StudentCourse());

  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定する．
   * @param studentDetail 受講生詳細情報
   * @param uuid　受講生コース情報に紐づく受講生のID
   */
  void initStudentsCourse(StudentDetail studentDetail, UUID uuid) {
    if(studentDetail.getRegisterStudentCourse() == null){
      studentDetail.setRegisterStudentCourse(new StudentCourse());
    }
    studentDetail.getRegisterStudentCourse().setStudentId(uuid.toString());
    studentDetail.getRegisterStudentCourse().setCourseStartAt(LocalDateTime.now());
  }

  /**
   * 削除された受講生情報の復元を行います。
   * @param id 受講生Id
   */
  public void restoreStudent(String id){
    repository.restoreStudent(id);
  }

}


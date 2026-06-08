package raisetech.student.management.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです．
 */
@Mapper
public interface StudentRepository {

  /**
   * 削除済み受講生も含めて単一検索を行います。
   * @return Student
   */
   Student searchStudentIncludeDeleted(String id);

  /**
   *受講生の全件検索を行います．
   *
   * @return 受講生一覧(全件)
   */


  List<Student> search();

  /**
   * 受講生の検索を行います．
   *
   * @param id 受講生ID
   * @return 受講生
   */

  Student searchStudent(String id);

  /**
   * 受講生IDに紐づく受講生コース情報を検索します．
   *
   * @param id 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */

  List<StudentCourse> matchStudentCourses(String id);

  /**
   * 受講生のコース情報の全件検索を行います．
   *
   * @return 受講生コース情報(全件)
   */

  List<StudentCourse> searchStudentCourseList();


  /**
   * 受講生を新規登録します．
   * @param student 受講生
   */

  //@Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);


  /**
   * 受講生コース情報を新規登録します．
   * @param studentCourse 受講生コース情報
   */

  void registerStudentCourse(StudentCourse studentCourse);


  /**
   * 受講生を更新します．
   * @param student 受講生
   */

  void updateRegisterStudent(Student student);

  /**
   * 受講生コース情報を更新します．
   * @param studentCourse 受講生コース情報のコース名を更新します．
   */

  void updateRegisterStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生情報の論理削除を行います．
   * @param id 受講生ID
   */

  void softDeleteStudent(@Param("id") String id);

  void restoreStudent(@Param("id") String id);

}

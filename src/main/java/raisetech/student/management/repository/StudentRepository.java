package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;

/**
 * 受講生情報を扱うリポジトリ
 *
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです．
 */
@Mapper
public interface StudentRepository {


  /**
   *全件検索します．
   *
   * @return 全件検索した受講生情報の一覧
   */
  @Select("SELECT id, name, kana_name, nickname, email, living_area, age, sex, remark FROM students")
  List<Student> search();

  @Select ("SELECT id, student_id, course_name, course_start_at,course_end_at  FROM students_courses")
  List<StudentsCourses> searchCourses();

  @Insert ("INSERT INTO students (id, name, kana_name, nickname, email, living_area, age, sex, remark, is_deleted)"
      + " VALUES (#{student.id}, #{student.name}, #{student.kanaName}, #{student.nickname}, #{student.email}, #{student.livingArea}, #{student.age}, #{student.sex}, #{student.remark}, false)")
  //@Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(StudentDetail studentDetail);

  @Insert ("INSERT INTO students_courses (course_name, student_id) VALUES (#{registerStudentCourse.courseName}, #{registerStudentCourse.studentId})")
  void registerStudentCourse(StudentDetail studentDetail);


}

package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);

  @Select("SELECT * FROM students_courses WHERE student_id = #{id}")
  List<StudentsCourses> matchCourses(String id);


  @Select ("SELECT id, student_id, course_name, course_start_at,course_end_at  FROM students_courses")
  List<StudentsCourses> searchCourses();

  @Insert("Insert Into students (id, name, kana_name, email, age)"
      + "VALUES(#{id},#{name},#{kana},#{email},#{age})")
  void createStudent(@Param("name") String name, @Param("age") int age, @Param("kana")String kana, @Param("email") String email, @Param("id") String id);

  @Insert ("INSERT INTO students (id, name, kana_name, nickname, email, living_area, age, sex, remark, is_deleted)"
      + " VALUES (#{id}, #{name}, #{kanaName}, #{nickname}, #{email}, #{livingArea}, #{age}, #{sex}, #{remark}, false)")
  //@Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  @Insert ("INSERT INTO students_courses (course_name, student_id, course_start_at) VALUES (#{courseName}, #{studentId},#{courseStartAt})")
  void registerStudentCourse(StudentsCourses studentsCourses);



  @Update ("UPDATE students SET name = #{name}, kana_name = #{kanaName}, nickname = #{nickname}, "
      + "email = #{email}, living_area = #{livingArea}, age = #{age}, "
      + "sex = #{sex},remark = #{remark},is_deleted = #{isDeleted}  WHERE id = #{id}")
  void updateRegisterStudent(Student student);

  @Update ("UPDATE students_courses SET course_name = #{courseName} WHERE id = #{id}")
  void updateRegisterStudentCourse(StudentsCourses studentsCourses);

  @Update("UPDATE students SET is_deleted = #{isDeleted} WHERE id = #{id}")
  void softDeleteStudent(@Param("id") String id,@Param("isDeleted") boolean isDeleted);





  @Delete("DELETE FROM students WHERE name = #{name}")
  void deleteStudent(@Param("name") String name);


}

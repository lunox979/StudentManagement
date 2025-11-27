package raisetech.student.management;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface StudentRepository {



  @Select("SELECT * FROM students")
  List<Student> search();

  @Select ("SELECT id, student_id, course_name, course_start_at,course_end_at  FROM students_courses")
  List<StudentCourse> searchCourses();




}

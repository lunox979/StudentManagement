package raisetech.student.management.domain;

import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;


@Getter
@Setter
public class StudentDetail {
  @Valid //子のバリデーションまでチェックする
  private Student student;
  private List<StudentsCourses> studentsCourses;

  private StudentsCourses registerStudentCourse = new StudentsCourses();


}

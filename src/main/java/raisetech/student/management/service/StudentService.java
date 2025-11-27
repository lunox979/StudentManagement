package raisetech.student.management.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList(){
    //絞り込みをする．年齢が30代のみを抽出する
    //抽出したリストをコントローラに返す
    return repository.search().stream()
        .filter(student -> student.getAge() == 30)
        .collect(Collectors.toList());

  }

  public List<StudentCourse> searchStudentCourseList(){
    //絞り込み検索で「Javaコースの情報のみを抽出する」
    //抽出したリストをコントローラに返す
    return repository.searchCourses().stream()
        .filter(studentCourse -> studentCourse.getCourseName().equals("Javaコース"))
        .collect(Collectors.toList());
  }
}


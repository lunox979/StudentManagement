package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;
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
    return repository.search();

  }

  public List<StudentsCourses> searchStudentCourseList(){
    //絞り込み検索で「Javaコースの情報のみを抽出する」
    //抽出したリストをコントローラに返す
    return repository.searchCourses();
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail){

    UUID uuid = UUID.randomUUID();
    String newId = uuid.toString();
    studentDetail.getStudent().setId(newId);
    studentDetail.getRegisterStudentCourse().setStudentId(newId);
    studentDetail.getRegisterStudentCourse().setCourseStartAt(LocalDateTime.now());
    studentDetail.getRegisterStudentCourse().setCourseEndAt(LocalDateTime.now().plusYears(1));

    repository.registerStudent(studentDetail);
    // TODO: コース登録情報も行う
    repository.registerStudentCourse(studentDetail);
  }
}


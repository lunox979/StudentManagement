package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired //SpringBootから引っ張ってくることを表す
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    //絞り込みをする．年齢が30代のみを抽出する
    //抽出したリストをコントローラに返す
    List<Student> studentLists= repository.search();
    //List<Student> matchStudentList = studentLists.stream()
       // .filter(studentList -> studentList.getAge() >= 30 && studentList.getAge() < 40).toList();
    return List.copyOf(studentLists);


  }

  public Student searchStudent(String id){
    return repository.searchStudent(id);

  }

  public List<StudentsCourses> searchStudentCourseList() {
    //絞り込み検索で「Javaコースの情報のみを抽出する」
    //抽出したリストをコントローラに返す
    List<StudentsCourses> studentsCourses= repository.searchCourses();

    return List.copyOf(studentsCourses);
  }

  public List<StudentsCourses> searchStudentmatchCourseList(String id){
    return repository.matchCourses(id);
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail){


    repository.updateRegisterStudent(studentDetail.getStudent());

    if(studentDetail.getStudentsCourses() == null || studentDetail.getStudentsCourses().isEmpty()){
      return;
    }
    for(StudentsCourses studentsCourses : studentDetail.getStudentsCourses()){
        if(studentsCourses.getId() == null || studentsCourses.getId().isEmpty()){
          continue;
        }

      try{
        repository.updateRegisterStudentCourse(studentsCourses);
      }
      catch(DuplicateKeyException e){
        throw new RuntimeException("すでに同じコースが登録されています．");
      }

    }




  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail){
    UUID uuid  = UUID.randomUUID();
    studentDetail.getStudent().setId(uuid.toString());
    studentDetail.getRegisterStudentCourse().setStudentId(uuid.toString());
    repository.registerStudent(studentDetail.getStudent());


    try{
      repository.registerStudentCourse(studentDetail.getRegisterStudentCourse());
    }
    catch(DuplicateKeyException e){
      throw new RuntimeException("すでに同じコースが登録されています．");
    }

  }



}


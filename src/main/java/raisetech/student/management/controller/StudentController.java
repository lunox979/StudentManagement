package raisetech.student.management.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//import raisetech.student.management.controller.converter.StudentConverter;
import org.springframework.web.bind.annotation.RequestParam;
import raisetech.student.management.controller.converter.StudentsConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;
//import raisetech.student.management.service.StudentService;


@Controller
public class StudentController {
  private StudentService service;
  private StudentsConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentsConverter converter) {
    this.service = service;
    this.converter = converter;
  }



  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    //リクエスト加工処理 入力チェックとか入ったりする
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentCourseList();

    model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourses));
    return "studentList";

  }


  @GetMapping("/student/{userId}")
  public String getStudent(@PathVariable("userId") String userId, Model model){


    Student students = service.searchStudent(userId);
    List<StudentsCourses> studentCourses = service.searchStudentmatchCourseList(userId);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(students);
    studentDetail.setStudentsCourses(studentCourses);






    //List<String> courseList = getCourseList();
    //model.addAttribute("courseList", courseList);

    model.addAttribute("studentDetail", studentDetail);

    return "registerUpdateStudent";
  }







  @GetMapping("/newStudent")
  public String newStudent(Model model){
    model.addAttribute("studentDetail", new StudentDetail());
    List<String> courseList = getCourseList();
    model.addAttribute("courseList", courseList);
    return "registerStudent";
  }



  @PostMapping("/registerStudent")
  public String registerStudent(@Validated @ModelAttribute StudentDetail studentDetail, BindingResult result){

    System.out.println("POSTメソッドが呼ばれました");
    System.out.println("エラーあり: " + result.hasErrors());
    if(result.hasErrors()){
      return "registerStudent";
    }




    service.registerStudent(studentDetail);


    System.out.println(studentDetail.getStudent().getName() + "さんが新規受講生として登録されました．");
    return "redirect:/studentList";
  }

  @PostMapping("/updateStudent")
  public String updateStudent(@Validated @ModelAttribute StudentDetail studentDetail, BindingResult result,  Model model){

    if(result.hasErrors()){



      return "registerUpdateStudent";

    }




    service.updateStudent(studentDetail);




    return "redirect:/studentList";
  }

  private static List<String> getCourseList() {
    return List.of("Javaコース","React応用講座","Webプログラミング基礎","UI/UXデザインコース");
  }

}








/*
  @GetMapping("/updateStudent")
  public String updateStudent(Model model){

    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }
*/



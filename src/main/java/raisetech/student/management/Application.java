package raisetech.student.management;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

  @Autowired
  private StudentRepository repository;


	public static void main(String[] args) {
    //localhost:8080
    SpringApplication.run(Application.class, args);
	}

  @GetMapping("/student")
  public List<Student> getStudent(){
    List<Student> student = repository.getStudentList();
    return List.copyOf(student);
  }



  @PostMapping("/student")
  public ResponseEntity<Void> registerStudent(String name,Integer age){
    if(name == null || age == null){
      return ResponseEntity.badRequest().build();
    }
    repository.registerStudent(name,age);
    return ResponseEntity.status(201).build();

  }

  @PatchMapping("/student")
  public ResponseEntity<Void> updateStudent(String name,Integer age){
    if(name == null || age == null){
      return ResponseEntity.badRequest().build();
    }
   repository.updateStudent(name, age);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/student")
  public ResponseEntity<Void> deleteStudent(String name){
    if(name == null){
      return ResponseEntity.badRequest().build();
    }
    repository.deleteStudent(name);
    return ResponseEntity.noContent().build();
  }


}

package raisetech.student.management;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentsCourses;
import raisetech.student.management.repository.StudentRepository;


@SpringBootApplication


public class Application {

  private StudentRepository repository;
	public static void main(String[] args) {
    //localhost:8080
    SpringApplication.run(Application.class, args);

	}

/*
  @PostMapping("/student")
  public void CreateStudent(String name, int age, String kana, String id,String email){
    repository.createStudent(name, age, kana, email, id);
  }

  @PatchMapping("/student")
  public void updateStudent(String name, int age) {
    repository.updateStudent(name,age);
  }

  @DeleteMapping("/student")
  public void deleteStudent(String name){
    repository.deleteStudent(name);
  }
*/

}

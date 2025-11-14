package raisetech.student.management;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {



  private final Map<Integer, String> studentName = new HashMap<>();


	public static void main(String[] args) {
    //localhost:8080
    SpringApplication.run(Application.class, args);
	}


  @GetMapping("/studentInfo")
  public Map<Integer,String> getStudentInfo() {
    return studentName;


  }

  @PostMapping("/studentInfo")
  public void setStudentInfo(Integer id,String name){

    studentName.put(id,name);

  }

  @PostMapping("/studentName")
  public void updateStudentName(String name,String changeName){
    studentName.entrySet().stream().filter(entry -> Objects.equals(name, entry.getValue()))
        .forEach(entry -> entry.setValue(changeName));
  }


}

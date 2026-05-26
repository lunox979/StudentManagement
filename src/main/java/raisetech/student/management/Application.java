package raisetech.student.management;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import raisetech.student.management.repository.StudentRepository;


@SpringBootApplication


public class Application {

  private StudentRepository repository;
	public static void main(String[] args) {
    //localhost:8080
    SpringApplication.run(Application.class, args);

	}

}

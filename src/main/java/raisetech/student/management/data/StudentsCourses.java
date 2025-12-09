package raisetech.student.management.data;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {
    private String id;
    private String studentId;
    @NotBlank(message = "登録するコースを選択してください")
    private String courseName;
    private LocalDateTime courseStartAt;
    private LocalDateTime courseEndAt;



}


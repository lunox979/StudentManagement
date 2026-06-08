package raisetech.student.management.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {
    @Schema(description = "自動連番されたID",example = "10")
    private String id;

    @Schema(description = "受講生を登録したと同時に生成された関連するUUIDを付与",
        example = "5998fd5d-a2cd-11ef-b71f-6825f345144")
    private String studentId;

    @Schema(description = "受講生の登録するコース",example = "Javaコース")
    @NotBlank(message = "登録するコースを選択してください")
    private String courseName;

    @Schema(description = "コースを開始した日時",example = "2026-05-20T19:36:20")
    private LocalDateTime courseStartAt;

    @Schema(description = "コースを終了した日時",example = "null")
    private LocalDateTime courseEndAt;



}


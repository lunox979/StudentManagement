package raisetech.student.management.controller.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import raisetech.student.management.controller.request.RegisterStudentRequest;
import raisetech.student.management.controller.request.UpdateStudentRequest;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

@Mapper(componentModel = "spring")
public interface StudentMapper {
  @Mapping(target = "id",ignore = true)
  @Mapping(target = "deleted",ignore = true)
  Student toStudent(RegisterStudentRequest request);

  @Mapping(target = "id",ignore = true)
  @Mapping(target = "studentId",ignore = true)
  @Mapping(target = "courseStartAt",ignore = true)
  @Mapping(target = "courseEndAt",ignore = true)
  StudentCourse toStudentCourse(RegisterStudentRequest request);

  @Mapping(target = "deleted" ,ignore = true)
  Student toUpdateInformation(UpdateStudentRequest request);

}

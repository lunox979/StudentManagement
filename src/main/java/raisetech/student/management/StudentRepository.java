package raisetech.student.management;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.DeleteMapping;

@Mapper
public interface StudentRepository {



  @Select("SELECT * FROM student")
  List<Student> getStudentList();


  @Insert("INSERT student values(#{name}, #{age})")
  void registerStudent(String name, Integer age);

  @Update("UPDATE student SET age = #{age} WHERE name = #{name}")
  void updateStudent(String name, Integer age);

  @Delete("DELETE FROM student WHERE name = #{name}")
  void deleteStudent(String name);

}

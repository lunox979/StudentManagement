package raisetech.student.management;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface StudentRepository {



  @Select("SELECT * FROM student")
  List<Student> getStudentList();


  @Insert("INSERT INTO student (name,age) values(#{name}, #{age})")
  void registerStudent(@Param("name") String name, @Param("age")Integer age);

  @Update("UPDATE student SET age = #{age} WHERE name = #{name}")
  void updateStudent(@Param("name") String name, @Param("age") Integer age);

  @Delete("DELETE FROM student WHERE name = #{name}")
  void deleteStudent(@Param("name") String name);

}

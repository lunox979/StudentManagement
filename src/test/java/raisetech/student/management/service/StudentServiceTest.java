package raisetech.student.management.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import org.mockito.Mock;


import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;



import raisetech.student.management.controller.converter.StudentsConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentsConverter converter;

  private StudentService sut;
  @BeforeEach
  void before(){
    sut = new StudentService(repository, converter);
  }
  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバータの処理が適切に呼び出せていること(){


    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    sut.searchStudentDetailList();

    verify(repository,times(1)).search();
    verify(repository,times(1)).searchStudentCourseList();
    verify(converter,times(1)).convertStudentDetails(studentList,studentCourseList);




  }

  @Test
  void 削除済みも含めた受講生情報の検索_リポジトリの処理が適切に呼び出せていること(){
    Student student = new Student();
    UUID uuid = UUID.randomUUID();
    when(repository.searchStudentIncludeDeleted(uuid.toString())).thenReturn(student);
    ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
    Student showStudent =sut.searchStudentIncludeDeleted(uuid.toString());

    verify(repository,times(1)).searchStudentIncludeDeleted(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(uuid.toString());
    assertThat(showStudent).isEqualTo(student);
  }

  @Test
  void 存在しないIDでの検索_削除済みも含めた全ての受講生検索_null値が返ること(){

    String id = "32e0049f-84d5-4478-933e-39721ee116a2";
    Student student = sut.searchStudentIncludeDeleted(id);


    assertThat(student).isNull();

  }



  @Test
  void 受講生のIDに紐づく受講生詳細検索_リポジトリの処理が適切に呼び出され正しい値が返ること(){
    Student student = new Student();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    UUID uuid = UUID.randomUUID();

    ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);


    when(repository.searchStudent(uuid.toString())).thenReturn(student);
    when(repository.matchStudentCourses(uuid.toString())).thenReturn(studentCourseList);

    StudentDetail studentDetail = sut.searchStudentDetail(uuid.toString());

    assertThat(studentDetail.getStudent()).isEqualTo(student);
    assertThat(studentDetail.getStudentCourseList()).isEqualTo(studentCourseList);
    verify(repository,times(1)).searchStudent(idCaptor.capture());
    verify(repository,times(1)).matchStudentCourses(idCaptor.capture());
    assertThat(idCaptor.getAllValues()).containsExactly(uuid.toString(),uuid.toString());

  }

  @Test
  void DBに存在しないUUIDでの受講生詳細検索_null値が返ること(){
    String id = "32e0049f-84d5-4478-933e-39721ee116a2";

    StudentDetail studentDetail = sut.searchStudentDetail(id);

    assertThat(studentDetail.getStudent()).isNull();


  }




  @Test
  void 受講生詳細の更新_リポジトリの処理が適切に呼び出されていること(){
    StudentDetail studentDetail = new StudentDetail();
    StudentCourse studentCourse = new StudentCourse();
    Student student = new Student();

    ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
    ArgumentCaptor<StudentCourse> studentCourseCaptor = ArgumentCaptor.forClass(StudentCourse.class);

    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(List.of(studentCourse));

    sut.updateStudent(studentDetail);

    verify(repository,times(1)).updateRegisterStudent(studentCaptor.capture());
    verify(repository,times(1)).updateRegisterStudentCourse(studentCourseCaptor.capture());

    assertThat(studentCaptor.getValue()).isEqualTo(student);
    assertThat(studentCourseCaptor.getValue()).isEqualTo(studentCourse);
  }

  @Test
  void 更新時の重複_更新時のメールアドレスが重複している時に重複例外が返されること(){
    StudentDetail studentDetail = new StudentDetail();
    StudentCourse studentCourse = new StudentCourse();
    Student student = new Student();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(List.of(studentCourse));

    doThrow(DuplicateKeyException.class).when(repository).updateRegisterStudent(student);

    assertThatThrownBy(()-> sut.updateStudent(studentDetail))
        .isInstanceOf(DuplicateKeyException.class);
  }






  @Test
  void 受講生情報の論理削除_リポジトリの処理が適切に呼び出されていること(){
    UUID uuid = UUID.randomUUID();

    ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
    sut.softDeleteStudent(uuid.toString());

    verify(repository,times(1)).softDeleteStudent(idCaptor.capture());

    assertThat(idCaptor.getValue()).isEqualTo(uuid.toString());
  }






  @Test
  void 受講生登録用情報への詰め替え_渡された登録情報が正しく入力されていること(){
    UUID uuid = UUID.randomUUID();
    StudentDetail studentDetail = new StudentDetail();
    LocalDateTime localDateTime = LocalDateTime.now();



    sut.initStudentsCourse(studentDetail,uuid);


    assertThat(studentDetail.getRegisterStudentCourse()).isNotNull();
    assertThat(studentDetail.getRegisterStudentCourse().getStudentId()).isEqualTo(uuid.toString());
    assertThat(studentDetail.getRegisterStudentCourse().getCourseStartAt()).isCloseTo(localDateTime,within(100,
        ChronoUnit.MILLIS));
  }

  @Test
  void 受講生情報の登録_リポジトリの処理が適切に呼び出せれ正しい値が返ること(){
    UUID uuid = UUID.randomUUID();
    StudentCourse studentCourse = new StudentCourse();
    Student student = new Student();
    StudentDetail studentDetail = new StudentDetail();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentDetail.setRegisterStudentCourse(studentCourse);
    studentDetail.setStudent(student);


    ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
    ArgumentCaptor<StudentCourse> studentCourseCaptor = ArgumentCaptor.forClass(StudentCourse.class);
    ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);


    when(repository.searchStudent(any())).thenReturn(student);
    when(repository.matchStudentCourses(any())).thenReturn(studentCourseList);

    StudentDetail showStudentDetail = sut.registerStudent(studentDetail);
    String id = showStudentDetail.getStudent().getId();
    verify(repository,times(1)).registerStudent(studentCaptor.capture());
    verify(repository,times(1)).registerStudentCourse(studentCourseCaptor.capture());
    verify(repository,times(1)).searchStudent(idCaptor.capture());
    verify(repository,times(1)).matchStudentCourses(idCaptor.capture());

    assertThat(studentCaptor.getValue()).isEqualTo(showStudentDetail.getStudent());
    assertThat(studentCourseCaptor.getValue()).isEqualTo(studentCourse);
    assertThat(idCaptor.getAllValues()).containsExactly(id,id);
    assertThat(showStudentDetail.getStudentCourseList()).isEqualTo(studentCourseList);


  }



  @Test
  void 受講生登録時_studentがnull値の場合例外が発生すること(){
    UUID uuid = UUID.randomUUID();
    StudentCourse studentCourse = new StudentCourse();
    Student student = new Student();
    StudentDetail studentDetail = new StudentDetail();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentDetail.setRegisterStudentCourse(studentCourse);

    assertThatThrownBy(()->sut.registerStudent(studentDetail))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void 受講生メール登録時_入力したメールアドレスが既に存在する場合例外が発生すること(){

    StudentDetail studentDetail = new StudentDetail();
    Student student = new Student();
    studentDetail.setStudent(student);


    doThrow(DuplicateKeyException.class).when(repository).registerStudent(student);

    assertThatThrownBy(()-> sut.registerStudent(studentDetail))
        .isInstanceOf(DuplicateKeyException.class);
  }

  @Test
  void 受講生情報の復元_リポジトリの処理が適切に呼び出されていること(){
    UUID uuid = UUID.randomUUID();

    ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);

    sut.restoreStudent(uuid.toString());


    verify(repository,times(1)).restoreStudent(idCaptor.capture());
    assertThat(idCaptor.getValue()).isEqualTo(uuid.toString());

  }













}
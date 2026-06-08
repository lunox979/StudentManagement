package raisetech.student.management.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import static org.mockito.ArgumentMatchers.any;
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
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
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
  void 受講生情報の検索_リポジトリの処理が適切に呼び出せていること(){
    Student student = new Student();
    UUID uuid = UUID.randomUUID();
    when(repository.searchStudentIncludeDeleted(uuid.toString())).thenReturn(student);

    Student showStudent =sut.searchStudentIncludeDeleted(uuid.toString());

    verify(repository,times(1)).searchStudentIncludeDeleted(uuid.toString());

    assertThat(showStudent).isEqualTo(student);
  }

  @Test
  void 受講生のIDに紐づく受講生詳細検索_リポジトリの処理が適切に呼び出され正しい値が返ること(){
    Student student = new Student();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    UUID uuid = UUID.randomUUID();
    when(repository.searchStudent(uuid.toString())).thenReturn(student);
    when(repository.matchStudentCourses(uuid.toString())).thenReturn(studentCourseList);

    StudentDetail studentDetail = sut.searchStudentDetail(uuid.toString());

    assertThat(studentDetail.getStudent()).isEqualTo(student);
    assertThat(studentDetail.getStudentCourseList()).isEqualTo(studentCourseList);
    verify(repository,times(1)).searchStudent(uuid.toString());
    verify(repository,times(1)).matchStudentCourses(uuid.toString());
  }

  @Test
  void 受講生詳細の更新_リポジトリの処理が適切に呼び出されていること(){
    StudentDetail studentDetail = new StudentDetail();
    StudentCourse studentCourse = new StudentCourse();
    Student student = new Student();

    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(List.of(studentCourse));

    sut.updateStudent(studentDetail);

    verify(repository,times(1)).updateRegisterStudent(student);
    verify(repository,times(1)).updateRegisterStudentCourse(studentCourse);
  }

  @Test
  void 受講生情報の論理削除_リポジトリの処理が適切に呼び出されていること(){
    UUID uuid = UUID.randomUUID();

    sut.softDeleteStudent(uuid.toString());

    verify(repository,times(1)).softDeleteStudent(uuid.toString());

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
    StudentCourse studentCourse = new StudentCourse();
    Student student = new Student();
    StudentDetail studentDetail = new StudentDetail();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentDetail.setRegisterStudentCourse(studentCourse);
    studentDetail.setStudent(student);

    when(repository.searchStudent(any())).thenReturn(student);
    when(repository.matchStudentCourses(any())).thenReturn(studentCourseList);

    StudentDetail showStudentDetail = sut.registerStudent(studentDetail);

    verify(repository,times(1)).registerStudent(student);
    verify(repository,times(1)).registerStudentCourse(studentCourse);
    verify(repository,times(1)).searchStudent(any());
    verify(repository,times(1)).matchStudentCourses(any());

    assertThat(showStudentDetail.getStudent()).isEqualTo(student);
    assertThat(showStudentDetail.getStudentCourseList()).isEqualTo(studentCourseList);

  }

  @Test
  void 受講生情報の復元_リポジトリの処理が適切に呼び出されていること(){
    UUID uuid = UUID.randomUUID();

    sut.restoreStudent(uuid.toString());

    verify(repository,times(1)).restoreStudent(uuid.toString());

  }









}
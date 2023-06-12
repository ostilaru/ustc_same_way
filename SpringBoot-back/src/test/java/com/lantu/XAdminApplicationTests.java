package com.lantu;

import com.lantu.sys.entity.*;
import com.lantu.sys.entity.Class;
import com.lantu.sys.mapper.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class XAdminApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Resource
    private ClassMapper classMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private CourseSelectionMapper courseSelectionMapper;

    @Resource
    private CourseTeachingMapper courseTeachingMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private PhysicalExamMapper physicalExamMapper;

    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Test
    void contextLoads() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void testClass() {
        List<Class> classes = classMapper.selectList(null);
        classes.forEach(System.out::println);
    }

    @Test
    void testCourse() {
        List<Course> courses = courseMapper.selectList(null);
        courses.forEach(System.out::println);
    }

    @Test
    void testCourseSelection() {
        List<CourseSelection> courseSelections = courseSelectionMapper.selectList(null);
        courseSelections.forEach(System.out::println);
    }

    @Test
    void testCourseTeaching() {
        List<CourseTeaching> courseTeachings = courseTeachingMapper.selectList(null);
        courseTeachings.forEach(System.out::println);
    }

    @Test
    void testDepartment() {
        List<Department> departments = departmentMapper.selectList(null);
        departments.forEach(System.out::println);
    }

    @Test
    void testPhysicalExam() {
        List<PhysicalExam> physicalExams = physicalExamMapper.selectList(null);
        physicalExams.forEach(System.out::println);
    }

    @Test
    void testScore() {
        List<Score> scores = scoreMapper.selectList(null);
        scores.forEach(System.out::println);
    }

    @Test
    void testStudent() {
        List<Student> students = studentMapper.selectList(null);
        students.forEach(System.out::println);
    }

    @Test
    void testTeacher() {
        List<Teacher> teachers = teacherMapper.selectList(null);
        teachers.forEach(System.out::println);
    }

    @Test
    void testUser_MINE() {
        List<String> roleNameByUserId = userMapper.getRoleNameByUserId(9);
        System.out.println(roleNameByUserId);
    }

    @Test
    void testStudentScore() {
        List<MyScore> studentScores = studentMapper.getScoreByStudentId("PB200502049");
        studentScores.forEach(System.out::println);
    }

}

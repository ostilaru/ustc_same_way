package com.lantu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author woowoo
 * @since 2023-06-03
 */
public class CourseSelection implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer courseId;

    @TableId(value = "student_id")
    private String studentId;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "CourseSelection{" +
            "courseId = " + courseId +
            ", studentId = " + studentId +
        "}";
    }
}

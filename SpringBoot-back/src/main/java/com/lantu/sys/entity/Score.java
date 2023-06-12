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
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer courseId;

    @TableId(value = "student_id")
    private String studentId;

    private Integer score;



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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }




    @Override
    public String toString() {
        return "Score{" +
                "courseId = " + courseId +
                ", studentId = " + studentId +
                ", score = " + score +
                "}";
    }
}

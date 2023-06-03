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
public class CourseTeaching implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer courseId;

    @TableId(value = "teacher_id")
    private String teacherId;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "CourseTeaching{" +
            "courseId = " + courseId +
            ", teacherId = " + teacherId +
        "}";
    }
}

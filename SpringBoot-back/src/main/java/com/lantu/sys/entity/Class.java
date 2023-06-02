package com.lantu.sys.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author woowoo
 * @since 2023-06-03
 */
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer classId;

    private Integer departmentId;

    private Integer studentCount;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    @Override
    public String toString() {
        return "Class{" +
            "classId = " + classId +
            ", departmentId = " + departmentId +
            ", studentCount = " + studentCount +
        "}";
    }
}

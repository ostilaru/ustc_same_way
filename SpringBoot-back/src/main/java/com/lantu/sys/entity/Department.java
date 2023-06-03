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
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "department_id")
    private Integer departmentId;

    private String departmentName;

    private Integer adminClassCount;

    private Integer totalStudents;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getAdminClassCount() {
        return adminClassCount;
    }

    public void setAdminClassCount(Integer adminClassCount) {
        this.adminClassCount = adminClassCount;
    }

    public Integer getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }

    @Override
    public String toString() {
        return "Department{" +
            "departmentId = " + departmentId +
            ", departmentName = " + departmentName +
            ", adminClassCount = " + adminClassCount +
            ", totalStudents = " + totalStudents +
        "}";
    }
}

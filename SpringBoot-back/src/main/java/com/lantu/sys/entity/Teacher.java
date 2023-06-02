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
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    private String teacherId;

    private String teacherName;

    private String gender;

    private Integer departmentId;

    private String contactPhone;

    private String contactMail;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "teacherId = " + teacherId +
            ", teacherName = " + teacherName +
            ", gender = " + gender +
            ", departmentId = " + departmentId +
            ", contactPhone = " + contactPhone +
            ", contactMail = " + contactMail +
        "}";
    }
}

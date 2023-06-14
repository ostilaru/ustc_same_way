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
public class Teacher implements Serializable {

    private static long serialVersionUID = 1L;

    @TableId(value = "teacher_id")
    private String teacherId;

    private String teacherName;

    private String gender;

    private Integer departmentId;

    private String contactPhone;

    private String contactMail;

    private String status;

    public Teacher() {
    }

    public Teacher(long serialVersionUID, String teacherId, String teacherName, String gender, Integer departmentId, String contactPhone, String contactMail, String status) {
        this.serialVersionUID = serialVersionUID;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.gender = gender;
        this.departmentId = departmentId;
        this.contactPhone = contactPhone;
        this.contactMail = contactMail;
        this.status = status;
    }

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

    /**
     * 获取
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}

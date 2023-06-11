package com.lantu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author woowoo
 * @since 2023-06-03
 */
public class Student implements Serializable {

    private static long serialVersionUID = 1L;

    @TableId(value = "student_id")
    private String studentId;

    private String name;

    private String gender;

    private Integer departmentId;

    private Integer classId;

    private String enrollmentDate;

    private String graduationDate;

    private String contactPhone;

    private String contactMail;

    private String status;

    public Student() {
    }

    public Student(long serialVersionUID,  String studentId, String name, String gender, Integer departmentId, Integer classId, String enrollmentDate, String graduationDate, String contactPhone, String contactMail, String status) {
        Student.serialVersionUID = serialVersionUID;
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.departmentId = departmentId;
        this.classId = classId;
        this.enrollmentDate = enrollmentDate;
        this.graduationDate = graduationDate;
        this.contactPhone = contactPhone;
        this.contactMail = contactMail;
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Student{" +
            "studentId = " + studentId +
            ", name = " + name +
            ", gender = " + gender +
            ", departmentId = " + departmentId +
            ", classId = " + classId +
            ", enrollmentDate = " + enrollmentDate +
            ", graduationDate = " + graduationDate +
            ", contactPhone = " + contactPhone +
            ", contactMail = " + contactMail +
            ", status = " + status +
        "}";
    }

}

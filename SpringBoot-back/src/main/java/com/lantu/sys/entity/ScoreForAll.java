package com.lantu.sys.entity;

public class ScoreForAll  {
    private Integer courseId;
    private String courseName;
    private Integer score;
    private String studentId;
    private String studentName;
    private String departmentName;
    private String teacherName;
    public ScoreForAll() {
    }

    public ScoreForAll(Integer courseId, String courseName, Integer score, String studentId, String studentName, String departmentName, String teacherName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.score = score;
        this.studentId = studentId;
        this.studentName = studentName;
        this.departmentName = departmentName;
        this.teacherName = teacherName;
    }
    public Integer getCourseId() {
        return courseId;
    }
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    /**
     * 获取
     * @return score
     */
    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
    /**
     * 获取
     * @return studentId
     */
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId= studentId;
    }
    /**
     * 获取
     * @return studentName
     */
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName= studentName;
    }
    /**
     * 获取
     * @return departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName= departmentName;
    }
    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName= teacherName;
    }
    @Override
    public String toString() {
        return "ScoreForAll{" +
                "courseId = " + courseId +
                ", courseName = " + courseName +
                ", score = " + score +
                ", studentId = " + studentId +
                ", studentName = " + studentName +
                ", departmentName = " + departmentName +
                ", teacherName = " + teacherName +
                "}";
    }
}

package com.lantu.sys.entity;

public class MyScore {

    private Integer courseId;

    private String courseName;

    private Integer score;

    private Integer creditPoints;

    private String studentId;

    private String studentName;

    public MyScore() {
    }

    public MyScore(Integer courseId, String courseName, Integer score, Integer credit_points, String studentId, String studentName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.score = score;
        this.creditPoints = credit_points;
        this.studentId = studentId;
        this.studentName = studentName;
    }

    /**
     * 获取
     * @return courseId
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * 设置
     * @param courseId
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    /**
     * 获取
     * @return courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * 设置
     * @param courseName
     */
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

    /**
     * 设置
     * @param score
     */
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

    /**
     * 设置
     * @param studentId
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * 获取
     * @return studentName
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * 设置
     * @param studentName
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String toString() {
        return "MyScore{courseId = " + courseId + ", courseName = " + courseName + ", score = " + score + ", credit_points = " + creditPoints + ", studentId = " + studentId + ", studentName = " + studentName + "}";
    }

    /**
     * 获取
     * @return creditPoints
     */
    public Integer getCreditPoints() {
        return creditPoints;
    }

    /**
     * 设置
     * @param creditPoints
     */
    public void setCreditPoints(Integer creditPoints) {
        this.creditPoints = creditPoints;
    }
}

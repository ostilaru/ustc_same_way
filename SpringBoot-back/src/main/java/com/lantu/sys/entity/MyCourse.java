package com.lantu.sys.entity;

public class MyCourse {

    private String teacherId;

    private String teacherName;

    private Integer courseId;

    private String courseName;

    private Integer creditPoints;

    private String classSchedule;

    private String classroomLocation;

    private Integer departmentId;


    public MyCourse() {
    }

    public MyCourse(String teacherId, String teacherName, Integer courseId, String courseName, Integer creditPoints, String classSchedule, String classroomLocation, Integer departmentId) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.courseId = courseId;
        this.courseName = courseName;
        this.creditPoints = creditPoints;
        this.classSchedule = classSchedule;
        this.classroomLocation = classroomLocation;
        this.departmentId = departmentId;
    }

    /**
     * 获取
     * @return teacherId
     */
    public String getTeacherId() {
        return teacherId;
    }

    /**
     * 设置
     * @param teacherId
     */
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * 获取
     * @return teacherName
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * 设置
     * @param teacherName
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    /**
     * 获取
     * @return classSchedule
     */
    public String getClassSchedule() {
        return classSchedule;
    }

    /**
     * 设置
     * @param classSchedule
     */
    public void setClassSchedule(String classSchedule) {
        this.classSchedule = classSchedule;
    }

    /**
     * 获取
     * @return classroomLocation
     */
    public String getClassroomLocation() {
        return classroomLocation;
    }

    /**
     * 设置
     * @param classroomLocation
     */
    public void setClassroomLocation(String classroomLocation) {
        this.classroomLocation = classroomLocation;
    }

    /**
     * 获取
     * @return departmentId
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     * 设置
     * @param departmentId
     */
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String toString() {
        return "MyCourse{teacherId = " + teacherId + ", teacherName = " + teacherName + ", courseId = " + courseId + ", courseName = " + courseName + ", creditPoints = " + creditPoints + ", classSchedule = " + classSchedule + ", classroomLocation = " + classroomLocation + ", departmentId = " + departmentId + "}";
    }
}

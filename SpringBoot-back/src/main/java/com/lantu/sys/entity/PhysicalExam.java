package com.lantu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author woowoo
 * @since 2023-06-03
 */
public class PhysicalExam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "student_id")
    private String studentId;

    private String name;

    private String gender;

    private BigDecimal height;

    private BigDecimal weight;

    private String vision;

    private Integer lungCapacity;

    private LocalDate examDate;

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

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public Integer getLungCapacity() {
        return lungCapacity;
    }

    public void setLungCapacity(Integer lungCapacity) {
        this.lungCapacity = lungCapacity;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    @Override
    public String toString() {
        return "PhysicalExam{" +
            "studentId = " + studentId +
            ", name = " + name +
            ", gender = " + gender +
            ", height = " + height +
            ", weight = " + weight +
            ", vision = " + vision +
            ", lungCapacity = " + lungCapacity +
            ", examDate = " + examDate +
        "}";
    }
}

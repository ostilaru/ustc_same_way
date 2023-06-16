package com.lantu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author woowoo
 * @since 2023-06-15
 */
@TableName("User_File")
public class UserFile implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uploaderId;

    @TableId(value = "file_id")
    private String fileId;

    private String fileName;

    private String fileType;

    private Integer fileSize;

    private LocalDate uploadDate;

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public String toString() {
        return "UserFile{" +
            "uploaderId = " + uploaderId +
            ", fileId = " + fileId +
            ", fileName = " + fileName +
            ", fileType = " + fileType +
            ", fileSize = " + fileSize +
            ", uploadDate = " + uploadDate +
        "}";
    }
}

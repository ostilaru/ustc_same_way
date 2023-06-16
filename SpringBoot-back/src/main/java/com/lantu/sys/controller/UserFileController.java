package com.lantu.sys.controller;

import com.lantu.sys.entity.UserFile;
import com.lantu.sys.service.IUserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.IMAGE_JPEG;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author woowoo
 * @since 2023-06-15
 */
@RestController
@RequestMapping("/userFile")
public class UserFileController {

    @Autowired
    private IUserFileService userFileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("uploader_id") String uploaderId) {
        try {
            // 检查文件是否为空
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("上传文件不能为空");
            }

            // 获取文件名
            String fileName = file.getOriginalFilename();
            System.out.println("图片名：" + fileName);

            // 获取文件的二进制数据
            byte[] fileData = file.getBytes();
            System.out.println("图片大小：" + fileData.length + "字节");

            // 检查文件大小
            if (fileData.length > 1024 * 1024 * 1) {
                return ResponseEntity.badRequest().body("上传文件不能超过 1MB");
            }

            // 获取文件的类型
            String fileType = file.getContentType();

            // 保存文件
            // 保存文件到服务器的本地文件系统
            String filePath = "/Users/jianglei/somebook_and_course/team/ustc_same_way/SpringBoot-back/src/main/resources/imgs/" + fileName;
            File destFile = new File(filePath);
            file.transferTo(destFile);

            // 封装一个 UserFile 对象，存到数据库中
            UserFile userFile = new UserFile();

            userFile.setUploaderId(uploaderId);

            // 判断 fileId 是否超过 40 个字符
            if (uploaderId.length() + fileName.length() > 40) {
                return ResponseEntity.badRequest().body("文件名过长");
            }

            userFile.setFileId(uploaderId + fileName);
            userFile.setFileName(fileName);
            userFile.setFileType(fileType);
            userFile.setFileSize(fileData.length);

            // 数据库中是 DATE 类型，所以这里要设置一下
            userFile.setUploadDate(new Date(new java.util.Date().getTime()).toLocalDate());

            // 设置完成后，保存到数据库中
            userFileService.save(userFile);

            return ResponseEntity.ok("文件上传成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件上传失败");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getFileList() throws IOException {
        System.out.println("正在获取文件列表");
        String folderPath = "/Users/jianglei/somebook_and_course/team/ustc_same_way/SpringBoot-back/src/main/resources/imgs";

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


        for (File file : files) {
            if (file.isFile()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                fileInputStream.close();
            }
        }

        byte[] fileBytes = outputStream.toByteArray();

        return ResponseEntity.ok().body(fileBytes);

    }

}

package com.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private org.springframework.core.env.Environment env;

    /**
     * 文件上传接口
     *
     * @param file 前端传递过来的文件
     * @return 文件访问URL
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "文件为空";
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 定义一个文件唯一的标识码
        String fileUUID = UUID.randomUUID().toString().replace("-", "") + fileExtension;

        // 获取项目根路径
        String projectRoot = getProjectRootPath();

        // 构建webapp/files路径
        Path uploadPath = Paths.get(projectRoot, "src", "main", "webapp", "files");

        // 确保上传目录存在
        File uploadDir = uploadPath.toFile();
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 文件的绝对路径
            File uploadFile = new File(uploadDir, fileUUID);

        // 保存文件
        file.transferTo(uploadFile);

        // 动态获取运行的端口
        String port = env.getProperty("server.port");
        if (port == null) {
            port = "8080"; // 默认端口，如果未设置
        }

        // 上传后、图片回显url
        return "http://localhost:" + port + "/files/" + fileUUID;
    }

    private String getProjectRootPath() {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        String rootPath = path.substring(1, path.indexOf("/target"));
        return rootPath;
    }
}
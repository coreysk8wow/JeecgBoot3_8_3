package org.jeecg.modules.newbie.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.newbie.model.entity.NewbieFile;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Tag(name = "文件上传接口")
@RestController
@RequestMapping("/newbie/file")
public class NewbieFileController {
    public static final String UPLOAD_DIR = "/tmp/uploads/";

    @Operation(summary = "文件上传接口")
    @PostMapping(value = "/uploadFile")
    public Result<NewbieFile> uploadFile(@RequestParam("file") MultipartFile file) {
        // Save the file to local disk.
//        String uploadPath = System.getProperty("user.dir") + "/uploads/";

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String uuid = UUID.randomUUID().toString();
        String fileName = uuid.replace("-", "") + "_" + file.getOriginalFilename();
        File dest = new File(UPLOAD_DIR + fileName);

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            return Result.error("文件上传失败: " + e.getMessage());
        }

        NewbieFile newbieFile = new NewbieFile() {{
            setUrl(dest.getAbsolutePath());
        }};
        Result<NewbieFile> r = new Result();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setResult(newbieFile);
        r.setMessage("文件上传成功了");

        return r;
    }

    /**
     * 这种不行。
     * @param fileUrl
     * @return
     */
/*
    @Operation(summary = "文件下载接口")
    @GetMapping(value = "/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam() String fileUrl) {
        try {
            File file = new File(fileUrl);
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            Path path = Paths.get(file.getAbsolutePath());
            Resource resource = new UrlResource(path.toUri());

            String contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }


            String encodedFilename = URLEncoder.encode(file.getName(), "UTF-8").replaceAll("\\+", "%20");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename*=UTF-8''" + encodedFilename)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
*/

    @Operation(summary = "文件下载接口")
    @GetMapping(value = "/downloadFile")
    public void downloadFile(@RequestParam() String fileUrl, HttpServletResponse response) {
        InputStream in = null;
        OutputStream out = null;
        try {
            File file = new File(fileUrl);
            if (!file.exists()) {
                throw new FileNotFoundException("File not found: " + fileUrl);
            }

            Path path = Paths.get(file.getAbsolutePath());
            Resource resource = new UrlResource(path.toUri());

            String contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = "application/vnd.ms-excel";
            }
            response.setContentType(contentType);
            response.setCharacterEncoding("utf-8");

            String fileName = URLEncoder.encode(file.getName(), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            //response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setContentLengthLong(file.length());

            // 读取文件并写入响应流
            in = new FileInputStream(file);
            out = response.getOutputStream();

            byte[] buffer = new byte[8192];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.flush();

        } catch (IOException e) {
            log.error("Error downloading file", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("Error closing streams", e);
                throw new RuntimeException(e);
            }
        }
    }

}

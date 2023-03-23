package com.blog.api;

import com.blog.form.UploadForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@RestController
public class UploadFileRestController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    /**
     * Hàm upload file từ from input html
     *
     * @param form
     * @return String (Đường dẫn file)
     */
    @PostMapping("/rest/uploadMultiFiles")
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadForm form) {

        String result = null;
        try {
            result = this.saveUploadedFiles(form.getFiles());
        }
        // Here Catch IOException only.
        // Other Exceptions catch by RestGlobalExceptionHandler class.
        catch (IOException e) {
            logger.error("Upload file failed !");
            logger.error("Exception : {}",e.getMessage());
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        logger.info("Upload file success !");
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    // Save Files
    private String saveUploadedFiles(MultipartFile[] files) throws IOException {
        DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        // Make sure directory exists!
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        StringBuilder sb = new StringBuilder();
        for (MultipartFile image : files) {
            String fileName = timeStampPattern.format(java.time.LocalDateTime.now())+"."+getFileExtension(image.getOriginalFilename());
            if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
                Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
            }
            Path file = CURRENT_FOLDER.resolve(staticPath)
                    .resolve(imagePath).resolve(getNewFile(fileName,image).getOriginalFilename());
            try (OutputStream os = Files.newOutputStream(file)) {
                os.write(getNewFile(fileName,image).getBytes());
            }
            String uploadFilePath = imagePath.resolve(getNewFile(fileName,image).getOriginalFilename()).toString();
            sb.append(uploadFilePath);
        }
        return sb.toString();
    }

    /**
     * Hàm lấy định dạng file (Đuôi file png,jpg,...)
     *
     * @param fileName
     * @return String
     */
    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String[] fileNameParts = fileName.split("\\.");

        return fileNameParts[fileNameParts.length - 1];
    }

    /**
     * Hàm đổi tên file
     *
     * @param fileName
     * @param currentFile
     * @return MultipartFile
     */
    private MultipartFile getNewFile(String fileName, MultipartFile currentFile){
        return new MultipartFile() {
            @Override
            public String getName() {
                return currentFile.getName();
            }

            @Override
            public String getOriginalFilename() {
                return fileName;
            }

            @Override
            public String getContentType() {
                return currentFile.getContentType();
            }

            @Override
            public boolean isEmpty() {
                return currentFile.isEmpty();
            }

            @Override
            public long getSize() {
                return currentFile.getSize();
            }

            @Override
            public byte[] getBytes() throws IOException {
                return currentFile.getBytes();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return currentFile.getInputStream();
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {

            }
        };
    }
}

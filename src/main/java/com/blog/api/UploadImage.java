package com.blog.api;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class UploadImage {
    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    @PostMapping("/image")
    public ResponseEntity<?> upload(@RequestParam MultipartFile image ) throws IOException{
        DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        System.out.println(timeStampPattern.format(java.time.LocalDateTime.now()));
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        String fileName = timeStampPattern.format(java.time.LocalDateTime.now())+"."+getFileExtension(image.getOriginalFilename());
        Path file = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(getNewFile(fileName,image).getOriginalFilename());
        System.out.println(getNewFile(fileName,image).getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(getNewFile(fileName,image).getBytes());
        }
        return ResponseEntity.ok().body(imagePath.resolve(getNewFile(fileName,image).getOriginalFilename()).toString());
    }

    /**
     * Hàm lây đuôi file
     * @param fileName
     * @return
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
     * @param fileName
     * @param currentFile
     * @return
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

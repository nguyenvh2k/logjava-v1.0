package com.blog.api;

import com.blog.form.UploadForm;
import com.blog.service.impl.FirebaseFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1")
public class ResourceController {
    @Autowired
    private FirebaseFileService firebaseFileService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Hàm upload file lên firebase
     * @param
     * @return
     */

    @PostMapping("/firebase/upload")
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadForm form) {
        String result = null;
        result = this.create(form.getFiles());
        logger.info("Upload file success !");
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }


    public String create( MultipartFile[] files) {
        String fileName = "";
        for (MultipartFile file : files) {
            try {
                fileName = firebaseFileService.saveTest(file);
                logger.info(fileName);
                // do whatever you want with that
            } catch (Exception e) {
                //  throw internal error;
                logger.error("Upload failed !");
                logger.error("Exception: {}",e.getMessage());
            }
        }
        StringBuilder url = new StringBuilder();
        url.append("https://firebasestorage.googleapis.com/v0/b/logjava-9bb57.appspot.com/o/");
        url.append(fileName);
        url.append("?alt=media");
        return url.toString();
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
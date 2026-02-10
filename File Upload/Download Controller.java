package com.vaultshare.controller;

import com.vaultshare.model.FileEntity;
import com.vaultshare.repository.FileRepository;
import com.vaultshare.security.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    private SecretKey aesKey;

    public FileController() throws Exception {
        this.aesKey = EncryptionUtil.generateAESKey();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("username") String username) {
        try {
            byte[] encryptedData = EncryptionUtil.encryptAES(file.getBytes(), aesKey);

            FileEntity fileEntity = new FileEntity();
            fileEntity.setFilename(file.getOriginalFilename());
            fileEntity.setFileType(file.getContentType());
            fileEntity.setData(encryptedData);
            fileEntity.setUploadedAt(LocalDateTime.now());
            fileEntity.setUploadedBy(username);

            fileRepository.save(fileEntity);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        try {
            FileEntity fileEntity = fileRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("File not found"));

            byte[] decryptedData = EncryptionUtil.decryptAES(fileEntity.getData(), aesKey);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileEntity.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFilename() + "\"")
                    .body(decryptedData);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/list/{username}")
    public List<FileEntity> listFiles(@PathVariable String username) {
        return fileRepository.findByUploadedBy(username);
    }
}

package com.example.property.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

@Service
public class FileUploadService {
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // file name
        String originalfileName = file.getOriginalFilename();
        String fileExt = this.getFileExtension(originalfileName);
        String name = UUID.randomUUID().toString() + "." + fileExt;
        // file path
        String filePath = path + File.separator + name;
        // create folder if not created
        File newFile = new File(path);
        if(!newFile.exists()){
            newFile.mkdir();
        }
        // file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return name;
    }

    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        return (lastDotIndex == -1) ? "unknown" : filename.substring(lastDotIndex + 1);
    }
}

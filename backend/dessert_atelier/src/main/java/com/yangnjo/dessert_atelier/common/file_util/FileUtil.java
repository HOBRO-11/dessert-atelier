package com.yangnjo.dessert_atelier.common.file_util;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

    public static final List<String> SUPPORT_EXTENSIONS = Arrays
            .asList(new String[] { "image/png", "image/jpg", "image/jpeg" });

    public static String extractExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if(extension.isEmpty()){
            return ""; // 확장자가 없을
        }
        return extension;
    }

    public static String extractBaseName(MultipartFile file) {
        return FilenameUtils.getBaseName(file.getOriginalFilename());
    }

    public static String extractFileName(MultipartFile file) {
        return FilenameUtils.getName(file.getOriginalFilename());
    }

    public static String generateUUIDBaseName(){
        return UUID.randomUUID().toString();
    }

    public static boolean isSupportExtension(MultipartFile file) {
        return SUPPORT_EXTENSIONS.contains(file.getContentType());
    }
}

package com.yangnjo.dessert_atelier.common.file_util;

import java.nio.file.Paths;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

public class FileUtil {

    public static String extractExtension(String filePath) {
        String extension = FilenameUtils.getExtension(filePath);
        if(extension.isEmpty()){
            return ""; // 확장자가 없을
        }
        return extension;
    }

    public static String extractBaseName(String urlPath) {
        return FilenameUtils.getBaseName(urlPath);
    }

    public static String extractFileName(String urlPath) {
        return FilenameUtils.getName(urlPath);
    }

    public static String generateUUIDFileName(){
        return UUID.randomUUID().toString();
    }
}

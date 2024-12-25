package com.yangnjo.dessert_atelier.common.file_util;

import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {

    public static String extractExtension(String filePath) {
        Pattern pattern = Pattern.compile("\\.([a-zA-Z0-9]+)$");
        Matcher matcher = pattern.matcher(filePath);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return ""; // 확장자가 없을
    }

    public static String extractFileName(String urlPath) {
        return Paths.get(urlPath).getFileName().toString();
    }
}

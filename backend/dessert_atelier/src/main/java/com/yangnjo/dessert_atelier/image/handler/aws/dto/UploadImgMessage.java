package com.yangnjo.dessert_atelier.image.handler.aws.dto;

public class UploadImgMessage {

    public static String delete(String bucketName, String savedPath, String[] filenames) {
        String[] files = getFiles(filenames);
        String join = String.join(",", files);

        return "{\"operator\": \"delete\", \"filename\": [" + join
                + "], \"path\": \"" + savedPath + "\", \"bucketname\": \"" + bucketName + "\"}";
    }

    private static String[] getFiles(String[] filenames) {
        String[] files = new String[filenames.length];
        int cnt = 0;
        for (String filename : filenames) {
            files[cnt++] = "\"" + filename + "\"";
        }
        return files;
    }
}

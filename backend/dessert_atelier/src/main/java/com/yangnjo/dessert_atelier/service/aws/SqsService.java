package com.yangnjo.dessert_atelier.service.aws;

public interface SqsService {
    
    /*
     * 단 건 업로드, 성공시 true, 실패시 false
     * 
     */
    void sendMessage(String queueUrl, String message, int retryCount) throws Exception;
}

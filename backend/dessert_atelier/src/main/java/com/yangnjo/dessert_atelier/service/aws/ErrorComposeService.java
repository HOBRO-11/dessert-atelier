package com.yangnjo.dessert_atelier.service.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.handler.aws.SqsHandler;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ErrorComposeService {
    
    @Value("${cloud.aws.sqs.error-compose.url}")
    private String sqsUrl;

    private final SqsHandler sqsHandler;

    @PostConstruct
    public void init(){
        sqsHandler.healthCheck(sqsUrl);
    }

    public void compose(String message) {
        sqsHandler.sendMessage(sqsUrl, message, 3);
    }

}

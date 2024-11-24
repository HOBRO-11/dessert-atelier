package com.yangnjo.dessert_atelier.handler.aws;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsSqsHandler {

    private final AmazonSQSAsync amazonSQSAsync;

    public void sendMessage(String queueUrl, SQSMessage sqsMessage) {
        // SendMessageRequest 생성
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(sqsMessage.getMessageBody()) // JSON 메시지
                .withMessageAttributes(sqsMessage.getMessageAttributes());

        CompletableFuture.runAsync(() -> {
            amazonSQSAsync.sendMessage(sendMessageRequest);
        }).join();
    }

}

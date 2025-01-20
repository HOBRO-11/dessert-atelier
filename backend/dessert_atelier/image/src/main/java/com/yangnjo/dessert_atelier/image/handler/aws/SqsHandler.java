package com.yangnjo.dessert_atelier.image.handler.aws;

import java.util.function.Function;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.ListQueuesResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SqsHandler {

    private final AmazonSQSAsync sqsClient;

    public void healthCheck(String url) {
        ListQueuesResult listQueues = sqsClient.listQueues();
        if(listQueues.getQueueUrls().contains(url) == false){
            String name = FilenameUtils.getName(url);
            throw new IllegalStateException("Queue가 정상적으로 작동하지 않을 수 있습니다. : " + name);
        }

    }

    public void sendMessage(String queueUrl, String message, int retryCount) {
        try {
            String[] param = new String[] { queueUrl, message };
            trySendMessage(param, retryCount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AmazonSQSException(message);
        }
    }

    private Function<String[], Void> createTask() {
        return (param) -> {
            try {
                sqsClient.sendMessage(param[0], param[1]);
                return null;
            } catch (Exception e) {
                throw e;
            }
        };
    }

    private void trySendMessage(String[] param, int retryCount) {
        if (retryCount < 1) {
            throw new IllegalArgumentException("tryCount must be greater than 0");
        }

        Function<String[], Void> task = createTask();
        if (task == null) {
            throw new IllegalArgumentException("task must not be null");
        }

        String errorMessage = "";
        for (int i = 0; i < retryCount; i++) {
            try {
                task.apply(param);
            } catch (Exception e) {
                if (i == retryCount - 1) {
                    errorMessage = e.getMessage();
                }
            }
        }

        if (errorMessage.isEmpty()) {
            return;
        }

        throw new AmazonSQSException(errorMessage);
    }

}

package com.yangnjo.dessert_atelier.service.aws.impl;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.yangnjo.dessert_atelier.service.aws.SqsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SqsServiceImpl implements SqsService {

    @Autowired
    private AmazonSQSAsync sqsClient;

    @Override
    public void sendMessage(String queueUrl, String message, int retryCount) throws Exception {
        String[] param = new String[] { queueUrl, message };
        trySendMessage(param, retryCount);
    }

    private Function<String[], Boolean> createTask() {
        return (param) -> {
            try {
                sqsClient.sendMessage(param[0], param[1]);
                return true;
            } catch (Exception e) {
                throw e;
            }
        };
    }

    private void trySendMessage(String[] param, int retryCount) throws Exception {
        if (retryCount < 1) {
            throw new IllegalArgumentException("tryCount must be greater than 0");
        }

        Function<String[], Boolean> task = createTask();
        if(task == null) {
            throw new IllegalArgumentException("task must not be null");
        }

        Exception throwable = null;
        for (int i = 0; i < retryCount; i++) {
            boolean isSuccess = false;
            try {
                isSuccess = task.apply(param);
            } catch (Exception e) {
                throwable = e;
                e.printStackTrace();
            }

            if (isSuccess) {
                return;
            }
        }

        throw throwable;
    }

}

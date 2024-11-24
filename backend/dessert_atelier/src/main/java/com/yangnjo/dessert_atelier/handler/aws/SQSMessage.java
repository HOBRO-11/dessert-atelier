package com.yangnjo.dessert_atelier.handler.aws;

import java.util.Map;

import com.amazonaws.services.sqs.model.MessageAttributeValue;

public interface SQSMessage {

    String getQueueUrl();

    String getMessageBody();

    void addMessageAttributes(String key, String value, String type);

    Map<String, MessageAttributeValue> getMessageAttributes();
}

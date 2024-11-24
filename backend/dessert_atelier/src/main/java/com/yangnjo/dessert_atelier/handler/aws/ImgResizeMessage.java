package com.yangnjo.dessert_atelier.handler.aws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.sqs.model.MessageAttributeValue;

public class ImgResizeMessage implements SQSMessage {
    private String queueUrl;
    private ImgResizeOperator operator;
    private String path;
    private List<String> imgNames;
    private Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();

    private static final String JSON_FORMAT = "\"operator\":\"%s\", \"path\":\"%s\", \"imgNames\":[%s]";

    public ImgResizeMessage(String queueUrl, ImgResizeOperator operator, String path, List<String> imgNames) {
        this.queueUrl = queueUrl;
        this.operator = operator;
        this.path = path;
        this.imgNames = imgNames;
    }

    @Override
    public void addMessageAttributes(String key, String value, String type) {
        this.messageAttributes.put(key, new MessageAttributeValue()
                .withStringValue(value)
                .withDataType(type));
    }

    @Override
    public Map<String, MessageAttributeValue> getMessageAttributes() {
        return this.messageAttributes;
    }

    @Override
    public String getMessageBody() {
        // imgNames의 각 항목을 "로 감싸고 쉼표로 연결
        String imgNamesJson = imgNames.stream()
                .map(name -> "\"" + name + "\"") // 각 이름을 "로 감쌈
                .collect(Collectors.joining(","));
        return String.format(JSON_FORMAT, operator, path, imgNamesJson);
    }

    @Override
    public String getQueueUrl() {
        return this.queueUrl;
    }
}

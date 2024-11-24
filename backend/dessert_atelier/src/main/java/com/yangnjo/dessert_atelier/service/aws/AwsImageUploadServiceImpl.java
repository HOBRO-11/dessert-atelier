package com.yangnjo.dessert_atelier.service.aws;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.yangnjo.dessert_atelier.common.web_util.ImgMetadata;
import com.yangnjo.dessert_atelier.common.web_util.MultipartParserContext;
import com.yangnjo.dessert_atelier.handler.aws.AwsS3ImageUploadHandler;
import com.yangnjo.dessert_atelier.handler.aws.AwsSqsHandler;
import com.yangnjo.dessert_atelier.handler.aws.SQSMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AwsImageUploadServiceImpl implements AwsImageUploadService {

    @Value("${cloud.aws.s3.default-path}")
    private String defaultPath;

    private final AwsS3ImageUploadHandler imageUploadHandler;
    private final AwsSqsHandler sqsHandler;

    @Override
    public boolean processImgStream(MultipartParserContext mpc, SQSMessage successMessage, SQSMessage failMessage) {
        List<ImgMetadata> imgMetadata = mpc.getImgMetadatas();
        boolean isSuccess = false;

        if (imgMetadata != null) {
            List<String> imgNames = new ArrayList<>();
            for (ImgMetadata im : imgMetadata) {
                imgNames.add(im.newName());
            }

            try {
                imageUploadHandler.uploadImageStreamParallel(mpc, defaultPath);
                isSuccess = true;
                return isSuccess;
            } catch (AmazonServiceException e) {
                return isSuccess;
            } finally {
                if (isSuccess) {
                    sqsHandler.sendMessage(successMessage.getQueueUrl(), successMessage);
                } else {
                    sqsHandler.sendMessage(failMessage.getQueueUrl(), failMessage);
                }
            }
        }

        return isSuccess;
    }
}

package com.yangnjo.dessert_atelier.service.react;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.yangnjo.dessert_atelier.common.web_util.ImgMetadata;
import com.yangnjo.dessert_atelier.common.web_util.MultipartParserContext;
import com.yangnjo.dessert_atelier.domain_service.react.ReviewCommandService;
import com.yangnjo.dessert_atelier.handler.aws.AwsS3ImageUploadHandler;
import com.yangnjo.dessert_atelier.handler.aws.AwsSqsHandler;
import com.yangnjo.dessert_atelier.handler.aws.ImgResizeMessage;
import com.yangnjo.dessert_atelier.handler.aws.ImgResizeOperator;
import com.yangnjo.dessert_atelier.web.requestDto.ReviewCreateForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private static final String REVIEW_BASE_PATH = "reviews/";
    private static final String IMAGE_BASE_PATH = "images/";

    @Value("${cloud.aws.sqs.img-handler.url}")
    String queueUrl;

    private final ReviewCommandService reviewCommandService;
    private final AwsS3ImageUploadHandler imageUploadHandler;
    private final AwsSqsHandler sqsHandler;

    public void createReview(ReviewCreateForm form, MultipartParserContext mpc, Long memberId) {
        List<ImgMetadata> imgMetadata = form.getImgMetadata();
        ImgResizeMessage irm = null;

        if (imgMetadata != null) {
            List<String> imgNames = new ArrayList<>();
            for (ImgMetadata im : imgMetadata) {
                imgNames.add(im.newName());
            }

            reviewCommandService.createMemberReview(form.toDto(memberId, imgMetadata));

            try {
                imageUploadHandler.uploadImageStreamParallel(mpc, IMAGE_BASE_PATH);
                irm = new ImgResizeMessage(queueUrl, ImgResizeOperator.create, null, imgNames);
            } catch (AmazonServiceException e) {
                irm = new ImgResizeMessage(queueUrl, ImgResizeOperator.delete, REVIEW_BASE_PATH, imgNames);
            } finally {
                sqsHandler.sendMessage(irm.getMessageBody(), irm);
            }
        }
    }

}

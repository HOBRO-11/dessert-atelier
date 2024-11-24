package com.yangnjo.dessert_atelier.handler.aws;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.yangnjo.dessert_atelier.common.web_util.ImgMetadata;
import com.yangnjo.dessert_atelier.common.web_util.ImgStreamProperties;
import com.yangnjo.dessert_atelier.common.web_util.MultipartParser;
import com.yangnjo.dessert_atelier.common.web_util.MultipartParserContext;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AwsS3ImageUploadHandler {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.s3.default-path}")
    private String defaultPath;

    private final AmazonS3Client s3Client;

    /**
     * 
     * @param parser
     * @param imgMetadatas
     * @param path         이미지 저장을 할 디렉토리로 /로 끝나는 경로
     * @throws AmazonServiceException
     */
    public void uploadImageStreamParallel(MultipartParserContext mpc,
            final String path)
            throws AmazonServiceException {

        List<ImgMetadata> imgMetadatas = mpc.getImgMetadatas();
        int size = imgMetadatas.size();

        CountDownLatch latch = new CountDownLatch(size);

        for (int i = 0; i < size; i++) {
            try {
                // subStream이 제대로 생성되었는지 확인
                ImgStreamProperties imgStream = MultipartParser.extractImgStreamProps(mpc);

                if (imgStream == null) {
                    throw new IllegalStateException("스트림을 생성할 수 없습니다.");
                }

                PutObjectRequest putObjectRequest = imgStream.getPutObjectRequest(bucketName, path);

                s3Client.putObject(putObjectRequest);

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                latch.countDown();
            }
        }

        try {
            // 모든 작업이 완료될 때까지 대기
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 인터럽트 신호를 다시 설정
            throw new RuntimeException("Image upload tasks were interrupted", e);
        }
    }

}

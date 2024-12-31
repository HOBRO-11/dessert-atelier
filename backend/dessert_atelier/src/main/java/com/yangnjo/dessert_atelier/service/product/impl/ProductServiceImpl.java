package com.yangnjo.dessert_atelier.service.product.impl;

import static com.yangnjo.dessert_atelier.common.file_util.FileUtil.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_service.product.ProductCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.ProductQueryService;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ProductNotFoundException;
import com.yangnjo.dessert_atelier.handler.aws.S3Handler;
import com.yangnjo.dessert_atelier.handler.aws.SqsHandler;
import com.yangnjo.dessert_atelier.handler.aws.dto.UploadFileRequest;
import com.yangnjo.dessert_atelier.handler.aws.dto.UploadImgMessage;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductDto;
import com.yangnjo.dessert_atelier.service.aws.ErrorComposeService;
import com.yangnjo.dessert_atelier.service.product.ProductService;
import com.yangnjo.dessert_atelier.service.product.dto.ProductCreateForm;
import com.yangnjo.dessert_atelier.service.product.dto.ProductUpdateForm;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;
    private final ErrorComposeService errorComposeService;
    private final S3Handler s3Handler;
    private final SqsHandler sqsHandler;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.s3.default-path}")
    private String defaultPath;

    @Value("${cloud.aws.sqs.img-handler.url}")
    private String sqsUrl;

    @PostConstruct
    public void init() {
        defaultPath = defaultPath + "product/";
    }

    @Override
    @Transactional
    public Long create(ProductCreateForm form) {
        String formThumb = form.getThumb();
        MultipartFile image = form.getImage();

        if (formThumb == null || image == null) {
            throw new IllegalArgumentException("thumb를 입력해주세요.");
        }

        if (isSupportExtension(image) == false) {
            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
        }

        String uuidBaseName = generateUUIDBaseName();
        String extension = extractExtension(image);
        String fileName = uuidBaseName + "." + extension;

        Long prodId = productCommandService.create(form.toDto(uuidBaseName));

        s3Handler.uploadFile(new UploadFileRequest(bucketName, defaultPath, fileName, image));

        return prodId;
    }

    @Override
    public void updatePrice(ProductUpdateForm form) {
        Integer price = form.getPrice();

        if (price == null) {
            throw new IllegalArgumentException("price를 입력해주세요.");
        }

        productCommandService.update(form.toDto(price, null));
    }

    @Override
    @Transactional
    public void updateThumb(ProductUpdateForm form) {
        Long productId = form.getProductId();
        String thumb = form.getThumb();
        MultipartFile image = form.getImage();

        if (productId == null) {
            throw new IllegalArgumentException("productId를 입력해주세요.");
        }

        if (thumb == null) {
            throw new IllegalArgumentException("thumb를 입력해주세요.");
        }

        if (image == null) {
            throw new IllegalArgumentException("image를 입력해주세요.");
        }

        ProductDto dto = getProductDto(productId);
        String deleteThumb = dto.getThumb();

        String uuidBaseName = generateUUIDBaseName();
        String extension = extractExtension(image);
        String fileName = uuidBaseName + "." + extension;

        productCommandService.update(form.toDto(null, uuidBaseName));

        s3Handler.uploadFile(new UploadFileRequest(bucketName, defaultPath, fileName, image));

        String deleteMessage = UploadImgMessage.delete(bucketName, defaultPath, new String[] { deleteThumb });
        try {
            sqsHandler.sendMessage(sqsUrl,
                    deleteMessage, 3);
        } catch (Exception e) {
            e.printStackTrace();
            errorComposeService.compose(deleteMessage);
        }
    }

    @Override
    public void setQuantity(Long id, int quantity) {
        productCommandService.setQuantity(id, quantity);
    }

    @Override
    public List<ProductDto> get(PageOption pageOption) {
        return productQueryService.get(pageOption);
    }

    @Override
    public Optional<ProductDto> getByProductId(Long productId) {
        return productQueryService.getByProductId(productId);
    }

    private ProductDto getProductDto(Long productId) {
        return productQueryService.getByProductId(productId).orElseThrow(ProductNotFoundException::new);
    }
}

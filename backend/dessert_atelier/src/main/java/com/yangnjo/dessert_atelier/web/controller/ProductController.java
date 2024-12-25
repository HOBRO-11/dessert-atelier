package com.yangnjo.dessert_atelier.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yangnjo.dessert_atelier.common.file_util.FileUtil;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ProductNotFoundException;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductDto;
import com.yangnjo.dessert_atelier.service.aws.S3Service;
import com.yangnjo.dessert_atelier.service.aws.SqsService;
import com.yangnjo.dessert_atelier.service.aws.dto.UploadFileRequest;
import com.yangnjo.dessert_atelier.service.aws.dto.UploadImgMessage;
import com.yangnjo.dessert_atelier.service.product.ProductService;
import com.yangnjo.dessert_atelier.service.product.dto.ProductCreateForm;
import com.yangnjo.dessert_atelier.service.product.dto.ProductUpdateForm;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    @Value("${cloud.aws.s3.bucket}")
    String bucketName;

    @Value("${cloud.aws.s3.default-path}")
    String defaultPath;

    @Value("${cloud.aws.sqs.img-handler.url}")
    String queueUrl;

    private final ProductService productService;
    private final S3Service s3Service;
    private final SqsService sqsService;

    @PostConstruct
    public void init() {
        defaultPath = defaultPath + "product/";
    }

    @PostMapping
    public ResponseEntity<String> createProduct(ProductCreateForm form, @RequestParam("image") MultipartFile file) {
        String baseName = getFileBaseName(file);
        String extension = geFileExtension(file);
        form.setThumb(baseName);

        productService.create(form);
        String filename = baseName + "." + extension;
        String errorMessage = uploadFile(file, filename);

        if (errorMessage.isEmpty()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @GetMapping("{productId}")
    public ResponseEntity<List<ProductDto>> getProductDto(
            @PathVariable(value = "productId", required = true) Long productId) {
        // return all products
        if (productId == 0L) {
            List<ProductDto> body = productService.get(new PageOption(0, 50, null));
            return ResponseEntity.ok(body);
        }

        // return product by productId
        Optional<ProductDto> prodOptional = productService.getByProductId(productId);

        if (prodOptional.isPresent()) {
            return ResponseEntity.ok(List.of(prodOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("{productId}")
    @Transactional
    public ResponseEntity<String> updateProduct(@PathVariable(value = "productId", required = true) Long productId,
            ProductUpdateForm form, MultipartFile file) {
        ProductDto productDto = null;
        String baseName = getFileBaseName(file);
        String extension = geFileExtension(file);

        if (baseName.isEmpty() == false) {
            productDto = productService.getByProductId(productId).orElseThrow(ProductNotFoundException::new);
            form.setThumb(baseName);
        }

        form.setProductId(productId);
        productService.update(form);

        // 썸네일 변경
        if (file != null) {
            String errorMessage = null;
            String filename = baseName + "." + extension;
            errorMessage = uploadFile(file, filename);

            // 업로드 성공, 이전 썸네일 삭제
            if (errorMessage.isEmpty()) {
                String deleteErrorMessage = deleteFile(productDto.getThumb());
                if (deleteErrorMessage.isEmpty() == false) {
                    errorMessage = deleteErrorMessage.isEmpty() ? errorMessage : deleteErrorMessage;
                }
            }

            if (errorMessage.isEmpty() == false) {
                return ResponseEntity.badRequest().body(errorMessage);
            }
        }

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/quantity/{productId}")
    public ResponseEntity<String> updateProductQuantity(
            @PathVariable(value = "productId", required = true) Long productId,
            @RequestParam(value = "quantity", required = true) Integer quantity) {

        productService.setQuantity(productId, quantity);
        return ResponseEntity.ok().build();
    }

    private String getFileBaseName(MultipartFile file) {
        if (file == null) {
            return "";
        }

        String newFileName = FileUtil.generateUUIDFileName();
        return newFileName;
    }

    private String geFileExtension(MultipartFile file) {
        if (file == null) {
            return "";
        }
        return FileUtil.extractExtension(file.getOriginalFilename());
    }

    private String deleteFile(String objectKey) {
        try {
            sqsService.sendMessage(queueUrl,
                    UploadImgMessage.delete(bucketName, defaultPath, new String[] { objectKey }), 3);
            return "";
        } catch (Exception e) {
            System.out.println("del");
            return e.getLocalizedMessage();
        }
    }

    private String uploadFile(MultipartFile file, String objectKey) {
        boolean isSuccess = s3Service.uploadFile(new UploadFileRequest(bucketName, defaultPath, objectKey, file));

        try {
            if (isSuccess) {
                sqsService.sendMessage(queueUrl,
                        UploadImgMessage.create(bucketName, defaultPath, new String[] { objectKey }), 3);
            } else {
                sqsService.sendMessage(queueUrl,
                        UploadImgMessage.delete(bucketName, defaultPath, new String[] { objectKey }),
                        3);
            }
            return "";
        } catch (Exception e) {
            System.out.println("up");
            return e.getLocalizedMessage();
        }
    }
}

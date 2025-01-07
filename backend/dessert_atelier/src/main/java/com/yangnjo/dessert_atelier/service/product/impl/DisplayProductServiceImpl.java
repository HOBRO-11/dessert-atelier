package com.yangnjo.dessert_atelier.service.product.impl;

import static com.yangnjo.dessert_atelier.common.file_util.FileUtil.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProductStatus;
import com.yangnjo.dessert_atelier.domain_service.product.DisplayProductCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.DisplayProductQueryService;
import com.yangnjo.dessert_atelier.domain_service.product.exception.DisplayProductNotFountException;
import com.yangnjo.dessert_atelier.handler.aws.S3Handler;
import com.yangnjo.dessert_atelier.handler.aws.SqsHandler;
import com.yangnjo.dessert_atelier.handler.aws.dto.UploadFileRequest;
import com.yangnjo.dessert_atelier.handler.aws.dto.UploadImgMessage;
import com.yangnjo.dessert_atelier.repository.product.dto.DpDto;
import com.yangnjo.dessert_atelier.repository.product.dto.DpSimpleDto;
import com.yangnjo.dessert_atelier.service.aws.ErrorComposeService;
import com.yangnjo.dessert_atelier.service.product.DisplayProductService;
import com.yangnjo.dessert_atelier.service.product.dto.DisplayProductEntityCreateForm;
import com.yangnjo.dessert_atelier.service.product.dto.DisplayProductEntityUpdateForm;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DisplayProductServiceImpl implements DisplayProductService {

    private final DisplayProductCommandService dpCommandService;
    private final DisplayProductQueryService dpQueryService;
    private final ErrorComposeService errorComposeService;
    private final SqsHandler sqsHandler;
    private final S3Handler s3Handler;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.s3.default-path}")
    private String defaultPath;

    @Value("${cloud.aws.sqs.img-handler.url}")
    private String sqsUrl;

    @PostConstruct
    public void init() {
        defaultPath = defaultPath + "display_product/";
    }

    @Override
    @Transactional
    public Long create(DisplayProductEntityCreateForm form) {
        List<String> formThumb = form.getThumb();
        List<String> formDesc = form.getDesc();
        List<String> formDescImageBaseName = getDescImage(formDesc);
        List<MultipartFile> formThumbImages = form.getThumbImages();
        List<MultipartFile> formDescImages = form.getDescImages();

        if (formThumb == null || formThumb.isEmpty()) {
            throw new IllegalArgumentException("thumb를 입력해주세요.");
        }

        if (formDesc == null || formDesc.isEmpty()) {
            throw new IllegalArgumentException("desc를 입력해주세요.");
        }

        if (formThumb.size() != formThumbImages.size()) {
            throw new IllegalArgumentException("thumb의 수와 이미지 수가 맞지 않습니다.");
        }

        if (formDescImageBaseName.size() != formDescImages.size()) {
            throw new IllegalArgumentException("desc의 수와 이미지 수가 맞지 않습니다.");
        }

        List<String> thumbUuid = new ArrayList<>();
        List<UploadFileRequest> uploadThumbFileReqs = getUploadFileReqs(formThumbImages, Collections.emptyList(),
                thumbUuid);

        List<String> descUuid = new ArrayList<>();
        List<UploadFileRequest> uploadDescFileReqs = getUploadFileReqs(formDescImages, Collections.emptyList(),
                descUuid);
        List<String> saveDesc = descUuid.stream().map(uuid -> "<cus>" + uuid + "</cus>").collect(Collectors.toList());

        if (formThumbImages.size() != uploadThumbFileReqs.size()) {
            throw new IllegalArgumentException("지원하지 않는 이미지가 썸네일에 포함되어 있습니다.");
        }

        if (formDescImages.size() != uploadDescFileReqs.size()) {
            throw new IllegalArgumentException("지원하지 않는 이미지가 설명에 포함되어 있습니다.");
        }

        Long dpId = dpCommandService.create(form.toDto(thumbUuid, saveDesc));

        List<UploadFileRequest> uploadFileReqs = new ArrayList<>(uploadThumbFileReqs);
        uploadFileReqs.addAll(uploadDescFileReqs);

        s3Handler.uploadFileParallel(uploadFileReqs);

        return dpId;
    }

    @Override
    public Page<DpSimpleDto> getAllSimpleByDpStatus(DisplayProductStatus displayProductStatus, PageOption pageOption) {
        return dpQueryService.getAllSimpleByDpStatus(displayProductStatus, pageOption);
    }

    @Override
    public Page<DpSimpleDto> getAllSimpleByExceptDpStatus(DisplayProductStatus displayProductStatus,
            PageOption pageOption) {
        return dpQueryService.getAllSimpleByExceptDpStatus(displayProductStatus, pageOption);
    }

    @Override
    public Optional<DpDto> getById(Long id) {
        return dpQueryService.getById(id);
    }

    @Override
    public void updateDisplayProductStatus(Long dpId, DisplayProductStatus saleStatus) {
        dpCommandService.updateDisplayProductStatus(dpId, saleStatus);
    }

    @Override
    @Transactional
    public void updateThumbs(DisplayProductEntityUpdateForm form) {
        DpDto dpDto = getDpDto(form);
        List<String> formsThumb = form.getThumb();
        List<MultipartFile> images = form.getImages();

        if (formsThumb == null || formsThumb.isEmpty()) {
            throw new IllegalArgumentException("thumb를 입력해주세요.");
        }

        if (images == null || images.isEmpty()) {
            throw new IllegalArgumentException("images를 입력해주세요.");
        }

        List<String> savedThumb = dpDto.getThumb();
        List<String> deleteThumb = getDeleteBaseNames(formsThumb, savedThumb);
        List<String> uploadThumb = getUploadBaseNames(formsThumb, savedThumb);

        Map<String, String> uploadThumbsBaseNameAndUuidMap = new HashMap<>();
        List<UploadFileRequest> uploadFileReqs = new ArrayList<>();

        if (uploadThumb.size() > 0) {
            uploadFileReqs = getUploadFileReqs(images, savedThumb, uploadThumbsBaseNameAndUuidMap);

            if (uploadThumb.size() != uploadThumbsBaseNameAndUuidMap.size()) {
                throw new IllegalArgumentException("지원하지 않는 이미지가 포함되어 있습니다.");
            }
        }

        List<String> updateThumb = formsThumb.stream()
                .map(t -> {
                    String uuid = uploadThumbsBaseNameAndUuidMap.get(t);
                    return uuid != null ? uuid : t;
                })
                .collect(Collectors.toList());

        dpCommandService.update(form.toDto(updateThumb, null, null));

        if (uploadThumb.size() > 0) {
            s3Handler.uploadFileParallel(uploadFileReqs);
        }

        String deleteMessage = UploadImgMessage.delete(bucketName, defaultPath, deleteThumb.toArray(String[]::new));
        try {
            if (deleteThumb.size() > 0) {
                sqsHandler.sendMessage(sqsUrl, deleteMessage, 3);
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorComposeService.compose(deleteMessage);
        }
    }

    @Override
    @Transactional
    public void updateDescription(DisplayProductEntityUpdateForm form) {
        DpDto dpDto = getDpDto(form);
        List<String> formDesc = form.getDesc();
        List<String> formDescImage = getDescImage(formDesc);
        List<MultipartFile> images = form.getImages();

        if (formDesc == null || formDesc.isEmpty()) {
            throw new IllegalArgumentException("description를 입력해주세요.");
        }

        if (formDescImage.size() != images.size()) {
            throw new IllegalArgumentException("누락된 이미지가 있습니다.");
        }

        List<String> savedImageBaseName = getDescImage(dpDto.getDescription());
        List<String> deleteDescImages = getDeleteBaseNames(formDescImage, savedImageBaseName);
        List<String> addDescImages = getUploadBaseNames(formDescImage, savedImageBaseName);

        Map<String, String> baseNameAndUuidMap = new HashMap<>();
        List<UploadFileRequest> uploadFileReqs = getUploadFileReqs(images, savedImageBaseName, baseNameAndUuidMap);

        if (addDescImages.size() != uploadFileReqs.size()) {
            throw new IllegalArgumentException("지원하지 않는 이미지가 설명에 포함되어 있습니다.");
        }

        List<String> uuidFormDesc = getUuidFromFormDesc(formDesc, baseNameAndUuidMap);

        dpCommandService.update(form.toDto(null, uuidFormDesc, null));

        s3Handler.uploadFileParallel(uploadFileReqs);

        String deleteMessage = UploadImgMessage.delete(bucketName, defaultPath, deleteDescImages.toArray(String[]::new));
        try {
            sqsHandler.sendMessage(sqsUrl,
                    deleteMessage, 3);
        } catch (Exception e) {
            e.printStackTrace();
            errorComposeService.compose(deleteMessage);
        }

    }

    

    @Override
    public void updateOptionHeaderIds(DisplayProductEntityUpdateForm form) {
        if(form.getDisplayProductId() == null) {
            throw new IllegalArgumentException("dpId를 입력해주세요.");
        }

        if(form.getOptionHeaderIds() == null || form.getOptionHeaderIds().isEmpty()) {
            throw new IllegalArgumentException("optionHeaderIds를 입력해주세요.");
        }
        dpCommandService.update(form.toDto(null, null, form.getOptionHeaderIds()));
        
    }

    private List<String> getUuidFromFormDesc(List<String> formDesc, Map<String, String> baseNameAndUuidMap) {
        return formDesc.stream().map(desc -> {
            String key = "";
            if (desc.startsWith("<cus>") && desc.endsWith("</cus>")) {
                key = desc.replace("<cus>", "").replace("</cus>", "");
            } else {
                return desc;
            }

            String value = baseNameAndUuidMap.get(key);
            if (value != null) {
                return "<cus>" + value + "</cus>";
            }
            return desc;
        }).collect(Collectors.toList());
    }

    private List<String> getDescImage(List<String> formDesc) {
        return formDesc.stream()
                .filter(d -> d.startsWith("<cus>") && d.endsWith("</cus>"))
                .map(d -> d.replace("<cus>", "").replace("</cus>", ""))
                .collect(Collectors.toList());
    }

    private List<String> getUploadBaseNames(List<String> newBaseNames, List<String> oldBaseNames) {
        List<String> uploadThumb = new ArrayList<>(newBaseNames);
        uploadThumb.removeAll(oldBaseNames);
        return uploadThumb;
    }

    private List<String> getDeleteBaseNames(List<String> newBaseNames, List<String> oldBaseNames) {
        List<String> deleteThumb = new ArrayList<>(oldBaseNames);
        deleteThumb.removeAll(newBaseNames);
        return deleteThumb;
    }

    private List<UploadFileRequest> getUploadFileReqs(List<MultipartFile> images, List<String> exceptionBaseNameList,
            Map<String, String> baseNameAndUuidMap) {

        return images.stream()
                .filter(image -> isSupportExtension(image))
                .filter(image -> exceptionBaseNameList.contains(extractBaseName(image)) == false)
                .map(image -> {
                    String baseName = extractBaseName(image);
                    String uuidBaseName = generateUUIDBaseName();
                    baseNameAndUuidMap.put(baseName, uuidBaseName);
                    return new UploadFileRequest(bucketName,
                            defaultPath, uuidBaseName + "." + extractExtension(image),
                            image);
                })
                .collect(Collectors.toList());
    }

    private List<UploadFileRequest> getUploadFileReqs(List<MultipartFile> images, List<String> exceptionBaseNameList,
            List<String> uuidList) {

        return images.stream()
                .filter(image -> isSupportExtension(image))
                .filter(image -> exceptionBaseNameList.contains(extractBaseName(image)) == false)
                .map(image -> {
                    String uuidBaseName = generateUUIDBaseName();
                    uuidList.add(uuidBaseName);
                    return new UploadFileRequest(bucketName,
                            defaultPath, uuidBaseName + "." + extractExtension(image),
                            image);
                })
                .collect(Collectors.toList());
    }

    private DpDto getDpDto(DisplayProductEntityUpdateForm form) {
        Long dpId = form.getDisplayProductId();
        if (dpId == null) {
            throw new IllegalArgumentException("dpId를 입력해주세요.");
        }
        return dpQueryService.getById(dpId).orElseThrow(DisplayProductNotFountException::new);
    }
}

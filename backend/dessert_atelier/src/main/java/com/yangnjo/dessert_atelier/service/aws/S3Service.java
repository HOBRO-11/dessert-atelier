package com.yangnjo.dessert_atelier.service.aws;

import java.util.List;

import com.yangnjo.dessert_atelier.service.aws.dto.UploadFileRequest;

public interface S3Service {
    
    /*
     * 병렬 업로드, 성공시 true, 실패시 false
     */
    boolean uploadFileParallel(List<UploadFileRequest>  uploadFileRequests);

    /*
     * 단 건 업로드, 성공시 true, 실패시 false
     */
    boolean uploadFile(UploadFileRequest uploadFileRequest);
}

package com.yangnjo.dessert_atelier.service.aws;

import com.yangnjo.dessert_atelier.common.web_util.MultipartParserContext;
import com.yangnjo.dessert_atelier.handler.aws.SQSMessage;

public interface AwsImageUploadService {

    boolean processImgStream(MultipartParserContext mpc, SQSMessage successMessage, SQSMessage failMessage);

}
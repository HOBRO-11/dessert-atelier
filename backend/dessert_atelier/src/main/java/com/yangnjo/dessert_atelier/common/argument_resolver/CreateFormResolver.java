package com.yangnjo.dessert_atelier.common.argument_resolver;

import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangnjo.dessert_atelier.common.web_util.MultipartParser;
import com.yangnjo.dessert_atelier.common.web_util.MultipartParserContext;
import com.yangnjo.dessert_atelier.web.requestDto.CreateForm;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CreateFormResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return CreateForm.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Class<?> parameterType = parameter.getParameterType();
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            throw new IllegalArgumentException("HttpServletRequest is required");
        }

        // extract textMap from inputStream and convert it to the specified type
        MultipartParserContext mpc = new MultipartParserContext(request);

        MultipartParser.parseText(mpc);
        Map<String, String> textMap = mpc.getTextMap();

        // JavaType을 생성하여 정확한 타입 정보를 전달

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CreateForm createFrom = (CreateForm) objectMapper.convertValue(textMap, parameterType);
            createFrom.setMultipartParserContext(mpc);
            return createFrom;
        } catch (IllegalArgumentException e) {
            log.debug("Failed to convert textMap");
            throw e;
        }
    }

}

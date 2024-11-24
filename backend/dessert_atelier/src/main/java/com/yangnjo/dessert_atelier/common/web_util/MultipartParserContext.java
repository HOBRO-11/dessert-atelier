package com.yangnjo.dessert_atelier.common.web_util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultipartParserContext  {

    @Getter
    private byte[] boundaryBytes;
    @Getter
    private boolean isClosed = false;
    private boolean isExpectContentDisposition = false;
    private StringBuilder fieldNameBuffer = new StringBuilder();
    @Getter
    private WrappedInputStream wrappedInputStream = null;
    @Getter
    Map<String, String> textMap = new HashMap<>();
    @Getter
    List<ImgMetadata> imgMetadatas;

    public MultipartParserContext(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        inputStream.available();
        setBoundaryBytes(request);
        this.wrappedInputStream = new WrappedInputStream() {
            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
        };
    }

    public void close() throws IOException {
        wrappedInputStream.close();
    }

    public void available() {
        if (this.isClosed == true && this.wrappedInputStream == null) {
            throw new IllegalStateException("이미 닫힘이 있습니다.");
        }
        try {
            this.wrappedInputStream.available();
        } catch (IOException e) {
            log.debug("available stream already closed!!");
            throw new IllegalStateException(e);
        }
    }

    public void appendFieldName(String fieldName) {
        this.fieldNameBuffer.append(fieldName);
    }

    public String getFieldName() {
        return this.fieldNameBuffer.toString();
    }

    public void cleanFieldName() {
        this.fieldNameBuffer.setLength(0);
    }

    public void expectContentDisposition() {
        this.isExpectContentDisposition = true;
    }

    public void cleanExpectContentDisposition() {
        this.isExpectContentDisposition = false;
    }

    /**
     * Jump to the end of the data section of empty line (CRLF)
     * 
     * @throws IOException
     */
    public void skipDataSection() throws IOException {
        this.wrappedInputStream.skip(2);
    }

    /**
     * Content-Disposition 이 있는 경우 true
     * Content-Disposition 에서 name 를 추출하기 위해 true
     */
    public boolean isExpectContentDisposition() {
        return this.isExpectContentDisposition;
    }

    private void setBoundaryBytes(HttpServletRequest request) {
        String boundary = request.getContentType().split("boundary=")[1];
        this.boundaryBytes = boundary.getBytes(StandardCharsets.UTF_8);
    }

    public void addTextMap(String key, String value) {
        if (key.equals("metadata") && (StringUtils.isEmpty(value.trim()) == false)) {
            try {
                this.imgMetadatas = new ObjectMapper().readValue(value, new TypeReference<List<ImgMetadata>>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException("메타데이터 파싱 실패", e);
            }
        }else{
            this.textMap.put(key, value);
        }
    }
}

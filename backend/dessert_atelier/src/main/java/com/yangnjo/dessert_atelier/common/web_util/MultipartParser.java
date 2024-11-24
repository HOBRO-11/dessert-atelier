package com.yangnjo.dessert_atelier.common.web_util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.services.s3.internal.InputSubstream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultipartParser {

    public static void parseText(MultipartParserContext mpc) {
        final String FILE_NAME = "filename=\"";
        final String NAME = "name=\"";

        byte[] lineBuffer = new byte[1024]; // 임시로 한 줄을 저장할 버퍼
        int lineBufferIndex = 0;

        WrappedInputStream cis = mpc.getWrappedInputStream();

        try {
            for (int byteData = 0; (byteData = cis.read()) != -1;) {

                // 버퍼가 가득 찬 경우, 버퍼를 초기화
                if (lineBufferIndex >= lineBuffer.length) {
                    lineBuffer = new byte[1024];
                    lineBufferIndex = 0;
                }

                // 버퍼에 현재 바이트 추가
                lineBuffer[lineBufferIndex++] = (byte) byteData;

                // `\r\n`(CRLF) 발견 시 줄 끝 처리
                if (checkCRLF(lineBuffer, lineBufferIndex)) {

                    // 데이터 타입 설정
                    if (mpc.isExpectContentDisposition()) {
                        mpc.cleanExpectContentDisposition();

                        String line = new String(lineBuffer, StandardCharsets.UTF_8);

                        if (line.contains(FILE_NAME)) {
                            mpc.cleanFieldName();
                            mpc.appendFieldName(extractFileName(line));
                            return;
                        } else if (line.contains(NAME)) {
                            mpc.cleanFieldName();
                            mpc.appendFieldName(extractName(line));
                            mpc.skipDataSection();
                            textProcess(mpc);
                        } else {
                            throw new IllegalArgumentException();
                        }
                    } else if (checkBoundary(lineBuffer, lineBufferIndex, mpc.getBoundaryBytes())) {
                        mpc.expectContentDisposition();
                    }

                    lineBufferIndex = 0;
                    lineBuffer = new byte[1024];
                }
            }

            cis.closeQuietly();
        } catch (IOException e) {
            cis.closeQuietly();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public static ImgStreamProperties extractImgStreamProps(MultipartParserContext mpc)
            throws IOException {

        if (mpc == null) {
            throw new IOException();
        }

        List<ImgMetadata> imgMetadatas = mpc.getImgMetadatas();
        WrappedInputStream wis = mpc.getWrappedInputStream();

        try {
            Long size = -1L;
            ImageType fileExtension = null;

            wis.available();

            if (imgMetadatas == null || imgMetadatas.isEmpty()) {
                log.debug("imgMetadatas is null or empty");
                return null;
            }

            String extractedName = moveFileStartAndExtractFileName(mpc);
            String newName = null;

            for (ImgMetadata m : imgMetadatas) {
                String originName = m.originName();
                String normalizedName = Normalizer.normalize(originName, Form.NFC);
                if (normalizedName.equals(extractedName)) {
                    size = m.size();
                    newName = m.newName();
                    fileExtension = assertAndExtractFileExtension(m.type());
                    break;
                }
            }

            if (size == -1) {
                throw new IllegalStateException("size is -1");
            }

            InputSubstream iss = new InputSubstream(wis, 0, size, false);
            return new ImgStreamProperties(newName, fileExtension, size, iss);
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    private static void textProcess(MultipartParserContext mpc)
            throws IOException {

        if (mpc == null) {
            throw new IOException();
        }

        int lineBufferIndex = 0;
        byte[] lineBuffer = new byte[1024];
        int contentBufferIndex = 0;
        byte[] contentBuffer = new byte[1024];

        WrappedInputStream cis = mpc.getWrappedInputStream();

        for (int byteData = 0; (byteData = cis.read()) != -1;) {

            // 버퍼가 가득 찬 경우, 버퍼를 초기화
            if (lineBufferIndex >= lineBuffer.length) {
                lineBuffer = new byte[1024];
                lineBufferIndex = 0;
            }

            lineBuffer[lineBufferIndex++] = (byte) byteData;

            // `\r\n`(CRLF) 발견 시 줄 끝 처리
            if (checkCRLF(lineBuffer, lineBufferIndex)) {

                if (mpc.isExpectContentDisposition()) {
                    mpc.cleanExpectContentDisposition();
                    mpc.cleanFieldName();
                    mpc.appendFieldName(extractName(new String(lineBuffer, StandardCharsets.UTF_8)));
                } else if (checkBoundary(lineBuffer, lineBufferIndex, mpc.getBoundaryBytes())) {
                    mpc.expectContentDisposition();

                    String content = new String(contentBuffer, StandardCharsets.UTF_8);
                    mpc.addTextMap(mpc.getFieldName(), content.trim());
                    // mpc.cleanFieldName();

                    break;
                }

                for (int i = 0; i < lineBufferIndex; i++) {
                    contentBuffer[contentBufferIndex++] = lineBuffer[i];
                }
                // 새로운 줄 시작, 위치 갱신
                lineBufferIndex = 0;
                lineBuffer = new byte[1024];
            }

        }
    }

    private static String moveFileStartAndExtractFileName(MultipartParserContext mpc) throws IOException {
        if (mpc == null) {
            throw new IOException();
        }

        int lineBufferIndex = 0;
        byte[] lineBuffer = new byte[1024];

        WrappedInputStream cis = mpc.getWrappedInputStream();

        mpc.available();

        for (int byteData = 0; (byteData = cis.read()) != -1;) {

            // 버퍼가 가득 찬 경우, 버퍼를 초기화
            if (lineBufferIndex >= lineBuffer.length) {
                lineBuffer = new byte[1024];
                lineBufferIndex = 0;
            }

            lineBuffer[lineBufferIndex++] = (byte) byteData;

            // `\r\n`(CRLF) 발견 시 줄 끝 처리
            if (checkCRLF(lineBuffer, lineBufferIndex)) {

                if (mpc.isExpectContentDisposition()) {
                    mpc.cleanExpectContentDisposition();
                    mpc.cleanFieldName();
                    mpc.appendFieldName(extractFileName(new String(lineBuffer, StandardCharsets.UTF_8)));
                } else if (checkContentTypeLine(lineBuffer, lineBufferIndex)) {
                    mpc.skipDataSection();
                    String fieldName = mpc.getFieldName();
                    // mpc.cleanFieldName();
                    return fieldName;
                } else if (checkBoundary(lineBuffer, lineBufferIndex, mpc.getBoundaryBytes())) {
                    mpc.expectContentDisposition();
                }

                // 새로운 줄 시작, 위치 갱신
                lineBufferIndex = 0;
                lineBuffer = new byte[1024];
            }

        }
        return null;
    }

    private static boolean checkContentTypeLine(byte[] lineBuffer, int lineBufferIndex) {
        final String CONTENT_TYPE = "Content-Type:";
        final byte[] CONTENT_TYPE_PREFIX_PATTERN = CONTENT_TYPE.getBytes(StandardCharsets.UTF_8);
        return checkPatternInBuffer(lineBuffer, lineBufferIndex, CONTENT_TYPE_PREFIX_PATTERN);
    }

    private static boolean checkCRLF(byte[] lineBuffer, int lineBufferIndex) {
        return lineBufferIndex > 1 && lineBuffer[lineBufferIndex - 1] == '\n'
                && lineBuffer[lineBufferIndex - 2] == '\r';
    }

    private static boolean checkBoundary(byte[] buffer, int length, byte[] boundaryBytes) {
        return checkPatternInBuffer(buffer, length, boundaryBytes);
    }

    private static boolean checkPatternInBuffer(byte[] buffer, int length, byte[] pattern) {
        for (int i = 0; i <= length - pattern.length; i++) {
            boolean found = true;
            for (int j = 0; j < pattern.length; j++) {
                if (buffer[i + j] != pattern[j]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return true; // 패턴 시작 위치 반환
            }
        }
        return false; // 패턴을 찾지 못한 경우
    }

    private static ImageType assertAndExtractFileExtension(String fileName) {
        // 파일 이름이 null이거나 .이 없는 경우
        if (fileName == null) {
            throw new IllegalArgumentException("fileName is null");
        }

        switch (fileName.toLowerCase()) {
            case "image/jpg":
                return ImageType.JPG;
            case "image/jpeg":
                return ImageType.JPEG;
            case "image/png":
                return ImageType.PNG;
            case "image/heif":
            case "image/heic":
                return ImageType.HEIF;
            default:
                throw new IllegalArgumentException("fileName is not supported");
        }
    }

    private static String extractFileName(String line) {
        final String FILE_NAME_PATTERN = "filename=\"(.*?)\"";
        Pattern pattern = Pattern.compile(FILE_NAME_PATTERN);
        Matcher matcher = pattern.matcher(line);
        return matcher.find() ? matcher.group(1) : null;
    }

    private static String extractName(String line) {
        final String TEXT_NAME_PATTERN = "name=\"(.*?)\"";
        Pattern pattern = Pattern.compile(TEXT_NAME_PATTERN);
        Matcher matcher = pattern.matcher(line);
        return matcher.find() ? matcher.group(1) : null;
    }

}

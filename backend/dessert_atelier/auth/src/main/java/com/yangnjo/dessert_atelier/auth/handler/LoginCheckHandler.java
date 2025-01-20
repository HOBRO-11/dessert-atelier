package com.yangnjo.dessert_atelier.auth.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginCheckHandler {

    @Value("${login-checker.key-prefix}")
    private String keyPrefix;

    @Value("${login-checker.partition}")
    private int partition;

    private final StringRedisTemplate stringRedisTemplate;

    public boolean checkLogin(Long memberId) {
        return getBit(memberId);
    }

    public void login(Long memberId) {
        setBit(memberId, true);
    }

    public void logout(Long memberId) {
        setBit(memberId, false);
    }

    private boolean getBit(Long memberId) {
        PartitionAndOffset pas = getPartitionAndOffset(memberId);
        long partitionNumber = pas.getPartitionNumber();
        long offset = pas.getOffset();
        return stringRedisTemplate.opsForValue().getBit(keyPrefix + String.valueOf(partitionNumber), offset);
    }

    private void setBit(Long value, boolean isTrue) {
        PartitionAndOffset pas = getPartitionAndOffset(value);
        long partitionNumber = pas.getPartitionNumber();
        long offset = pas.getOffset();
        stringRedisTemplate.opsForValue().setBit(keyPrefix + String.valueOf(partitionNumber), offset, isTrue);
    }

    private PartitionAndOffset getPartitionAndOffset(Long memberId) {
        long partitionNumber = memberId / Long.valueOf(partition);
        long offset = memberId % Long.valueOf(partition);
        if (partitionNumber > -1) {
            return new PartitionAndOffset(partitionNumber, offset);
        }
        throw new IllegalArgumentException("memberId 이 잘못되었습니다. 현재 memberId 는 " + memberId);
    }

    @Getter
    private static class PartitionAndOffset {
        private final long partitionNumber;
        private final long offset;

        public PartitionAndOffset(long partitionNumber, long offset) {
            this.partitionNumber = partitionNumber;
            this.offset = offset;
        }
    }
}

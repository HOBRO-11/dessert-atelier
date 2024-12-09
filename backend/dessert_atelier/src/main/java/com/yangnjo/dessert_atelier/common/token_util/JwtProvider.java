package com.yangnjo.dessert_atelier.common.token_util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nimbusds.jose.util.StandardCharset;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtProvider {

    private String secretKey;
    private Integer duration;
    private ChronoUnit timeUnit;

    public String create(String subject, final Map<String, Object> claims, List<String> valuesToApplyHash) {
        String id = subject;
        Date now = new Date();
        Date expirationDate = Date.from(Instant.now().plus(duration, timeUnit));
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String expDate = javaDateToString(expirationDate);

        JwtBuilder builder = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(id).setIssuedAt(now).setExpiration(expirationDate);

        if (claims != null && (claims.isEmpty() == false)) {
            builder.addClaims(claims);
        }

        if (valuesToApplyHash != null && (valuesToApplyHash.isEmpty() == false)) {
            String hashCode = getHashCode(valuesToApplyHash, expDate);
            builder.claim("hashCode", hashCode);
        }

        return builder.compact();

    }

    public Claims validate(String jwt, List<String> valuesToApplyHash){
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String expDate = getExp(jwt, key);

        checkHash(jwt, valuesToApplyHash, key, expDate);

        return getJwtClaims(jwt, key);
    }

    public LocalDateTime getExpDate(String jwt, Key key) {
        Long exp = null;
        try {
            exp = getJwtClaims(jwt, key)
                    .get("exp", Long.class);

            return LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(exp),
                    ZoneId.systemDefault());
        } catch (Exception e) {
            throw new TokenNotAvailableException(e);
        }
    }

    private String getExp(String jwt, Key key) {
        Long exp = null;
        try {
            exp = getJwtClaims(jwt, key)
                    .get("exp", Long.class);
        } catch (Exception e) {
            throw new TokenNotAvailableException(e);
        }

        long now = (new Date().getTime()) / 1000;

        if (exp < now) {
            throw new TokenIsExpiredException();
        }

        return jwtDateToString(new Date(exp));
    }

    private void checkHash(String jwt, List<String> valuesToApplyHash, Key key, String expDate) {
        try {
            if (valuesToApplyHash != null && (valuesToApplyHash.isEmpty() == false)) {
                String hashCode = getJwtClaims(jwt, key)
                        .get("hashCode", String.class);

                String generatedHashCode = getHashCode(valuesToApplyHash, expDate);

                if (hashCode == null || hashCode.equals(generatedHashCode) == false) {
                    throw new TokenNotAvailableException("조작된 토큰 입니다.");
                }
            }
        } catch (Exception e) {
            throw new TokenNotAvailableException(e);
        }
    }

    private Claims getJwtClaims(String jwt, Key key) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            throw new TokenNotAvailableException(e);
        }
    }

    private String javaDateToString(Date date) {
        return String.valueOf(date.getTime() / 1000);
    }

    private String jwtDateToString(Date date) {
        return String.valueOf(date.getTime());
    }

    private String getHashCode(List<String> valuesToApplyHash, String expirationDate) {

        try {
            if (valuesToApplyHash == null || valuesToApplyHash.isEmpty()) {
                return null;
            }

            StringBuilder sb = new StringBuilder(expirationDate);
            for (String value : valuesToApplyHash) {
                sb.append(value);
            }

            byte[] bytes = sb.toString().getBytes(StandardCharset.UTF_8);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] code = md.digest(bytes);
            String hashCode = DatatypeConverter.printHexBinary(code);
            return hashCode;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

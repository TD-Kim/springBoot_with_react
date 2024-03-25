package org.zerock.mallapi.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

public class JWTUtil {
    // key 값은 다른값을 써도 되지만 짧으면 안된다
    private static String key = "1234567890123456789012345678901234567890";

    // jwt 문자열을 만드는 메소드
    public static String generateToken(Map<String, Object> valueMap, int min) {
        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        String jwtStr = Jwts.builder()
                .setHeader(Map.of("type", "JWT"))
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                .compact();

        return jwtStr;
    }

    public static Map<String, Object> validateToken(String token) {
        Map<String, Object> claim = null;

        try{
            SecretKey key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));

            claim = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token) // 파싱 및 검증, 실패시 에러
                    .getBody();
        }catch (MalformedJwtException malformedJwtException) {
            throw new CustomJWTException("MalFormed");
        }catch (ExpiredJwtException expiredJwtException) {
            throw new CustomJWTException("Expired");
        }catch (InvalidClaimException invalidClaimException) {
            throw new CustomJWTException("Invalid");
        }catch (JwtException jwtException) {
            throw new CustomJWTException("JWTError");
        }catch (Exception e) {
            throw new CustomJWTException("Error");
        }
        return claim;
    }
}

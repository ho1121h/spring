package goraebob.diary.handler;


import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
// JsonWebToken 핸들러
@Component //개발자가 직접 작성한 Class를 Bean으로 등록하기 위한 Annotation // 빈 ->
public class JwtHandler {
    private String type = "Bearer "; // "Bearer"는 토큰의 타입을 나타냄

    public String createToken(String encodedKey, String subject, long maxAgeSeconds) { //인코딩된 key 값을 받고 토큰에 저장될 데이터 subject, 만료기간등을 초단위로 받아서 토큰을 만들어줌
        Date now = new Date();
        return type + Jwts.builder()    // 토큰 빌드
                .setSubject(subject)    // 토큰에 저장될 데이터 지정
                .setIssuedAt(now)       // 토큰 발급일 지정
                .setExpiration(new Date(now.getTime() + maxAgeSeconds * 1000L)) // 토큰 만료일을 지정
                .signWith(SignatureAlgorithm.HS256, encodedKey) // 해당 암호와 알고리즘 사용
                .compact(); // 토큰을 생성
    }
    public String extractSubject(String encodedKey, String token) { // 토큰을 파싱 기타 등등 인증
        return parse(encodedKey, token).getBody().getSubject();
    }

    public boolean validate(String encodedKey, String token) { // 토큰의 유효성 검증
        try {
            parse(encodedKey, token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private Jws<Claims> parse(String key, String token) { //토큰을 파싱하는 과정임
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(untype(token));
    }

    private String untype(String token) { // 언타입으로 제거
        return token.substring(type.length());
    }
}
/**
 * jwt 장점: 중앙의 인증서버, 데이터 스토어에 대한 의존성이없고 시스템 수평확장이 유리하다.
 * 단점은 트래픽 부담
 */
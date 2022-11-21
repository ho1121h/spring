package goraebob.diary.handler;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
class JwtHandlerTest {
    JwtHandler jwtHandler = new JwtHandler(); //인스턴스 생성

    @Test
    void createToken() { // 토큰 생성 | 정상적으로 토큰생성 -> Bearer 타입이 덧붙여짐
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = createToken(encodedKey, "subject", 60L);

        // then
        assertThat(token).contains("Bearer ");
    }

    @Test
    void extractSubject() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String subject = "subject";
        String token = createToken(encodedKey, subject, 60L);

        // when
        String extractedSubject = jwtHandler.extractSubject(encodedKey, token);

        // then
        assertThat(extractedSubject).isEqualTo(subject);
    }

    @Test
    void validate() { // 올바른 토큰인지 검증
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = createToken(encodedKey, "subject", 60L);

        // when
        boolean isValid = jwtHandler.validate(encodedKey, token);

        // then
        assertThat(isValid).isTrue();
    }
    @Test
    void invalidateByInvalidKeyTest() { // 토큰 생성했던 key 외에 다른 키를 사용하면 토큰은 유효 x
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = createToken(encodedKey, "subject", 60L);

        // when
        boolean isValid = jwtHandler.validate("invalid", token);

        // then
        assertThat(isValid).isFalse();
    }
    @Test
    void invalidateByExpiredTokenTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = createToken(encodedKey, "subject", 0L); // 토큰의 유효시간 0초 설정

        // when
        boolean isValid = jwtHandler.validate(encodedKey, token);

        // then
        assertThat(isValid).isFalse();
    }

    private String createToken(String encodedKey, String subject, long maxAgeSeconds) {
        return jwtHandler.createToken(
                encodedKey,
                subject,
                maxAgeSeconds);
    }
}
package goraebob.diary.service.sign;


import goraebob.diary.handler.JwtHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
// 우선 이 코드를 작성하기 전에 application.yml을 설정하셔야 합니다. yml 에 접근해서 토큰을 만들게 됩니다.
@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtHandler jwtHandler;
    @Value("${jwt.max-age.access}") // 1 @Value: properties에서 값을 가져와 적용할 때 사용한다
    private long accessTokenMaxAgeSeconds;

    @Value("${jwt.max-age.refresh}") // 2
    private long refreshTokenMaxAgeSeconds;

    @Value("${jwt.key.access}") // 3
    private String accessKey;

    @Value("${jwt.key.refresh}") // 4
    private String refreshKey;

    public String createAccessToken(String subject) {
        return jwtHandler.createToken(accessKey, subject, accessTokenMaxAgeSeconds);
    }

    public String createRefreshToken(String subject) {
        return jwtHandler.createToken(refreshKey, subject, refreshTokenMaxAgeSeconds);
    }
    public boolean validateAccessToken(String token) {
        return jwtHandler.validate(accessKey, token);
    }

    public boolean validateRefreshToken(String token) {
        return jwtHandler.validate(refreshKey, token);
    }

    public String extractAccessTokenSubject(String token) {
        return jwtHandler.extractSubject(accessKey, token);
    }

    public String extractRefreshTokenSubject(String token) {
        return jwtHandler.extractSubject(refreshKey, token);
    }
}
/**
 *  1~ 4 @Value 어노테이션을 이용하여 설정파일 대로 작성된 내용을 가져옴 (application.yml)-> 설정파일
 *  이 설정과 JwtHandler를 이용하여 accessRoken 과 refreshToken 을 발급해줌
 *  액세스 토큰고 리프레쉬토큰은 각각 다른 키와 만료시간을 갖게됨
 */
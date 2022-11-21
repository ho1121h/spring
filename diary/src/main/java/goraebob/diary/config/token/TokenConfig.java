package goraebob.diary.config.token;

import goraebob.diary.handler.JwtHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@RequiredArgsConstructor
public class TokenConfig { // TokenHelper는 기존의 TokenService의 역할을 수행한다고 보면 됩

    private final JwtHandler jwtHandler;

    @Bean
    public TokenHelper accessTokenHelper(
            @Value("${jwt.key.access}") String key,
            @Value("${jwt.max-age.access}") long maxAgeSeconds) {
        return new TokenHelper(jwtHandler, key, maxAgeSeconds);
    }

    @Bean
    public TokenHelper refreshTokenHelper(
            @Value("${jwt.key.refresh}") String key,
            @Value("${jwt.max-age.refresh}") long maxAgeSeconds) {
        return new TokenHelper(jwtHandler, key, maxAgeSeconds);
    }
}
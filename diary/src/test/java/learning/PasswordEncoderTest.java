package learning;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

public class PasswordEncoderTest {

    // 패스워드 테스트 // PasswordEncoderFactories.createDelegatingPasswordEncoder 팩토리 메서드로 PasswordEncoder 의 구현체를 생성
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    void encodeWithBcryptTest() { // encoder 수행
        // given 패스워드가 들어오는상황이면
        String password = "password";

        // when 실행
        String encodedPassword = passwordEncoder.encode(password);

        // then 검증
        assertThat(encodedPassword).contains("bcrypt");
    }

    @Test
    void matchTest() { // encode 된 문자열을 어떻게 검증하는지 테스트
        // given
        String password = "password";
        String encodedPassword = passwordEncoder.encode(password);

        // when
        boolean isMatch = passwordEncoder.matches(password, encodedPassword);

        // then
        assertThat(isMatch).isTrue();
    }
}
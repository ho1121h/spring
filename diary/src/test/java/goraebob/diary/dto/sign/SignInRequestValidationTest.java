package goraebob.diary.dto.sign;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

class SignInRequestValidationTest {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator(); // 1 검증 작업을 수행하기위한 빌드

    @Test
    void validateTest() {
        // given
        SignInRequest req = createRequest();

        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req); // 2 검증수행

        // then
        assertThat(validate).isEmpty(); // 3 제약 조건을 모두 지키고 검증
    }

    @Test
    void invalidateByNotFormattedEmailTest() {
        // given
        String invalidValue = "email";
        SignInRequest req = createRequestWithEmail(invalidValue);

        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty(); // 4 제약조건 위반시 응답결과는 비어있지않음
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidValue); // 5 제약조건을 위반한 객체를 꺼내 확인
    }

    @Test
    void invalidateByEmptyEmailTest() {
        // given
        String invalidValue = null;
        SignInRequest req = createRequestWithEmail(invalidValue);

        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidValue);
    }

    @Test
    void invalidateByBlankEmailTest() {
        // given
        String invalidValue = " ";
        SignInRequest req = createRequestWithEmail(invalidValue);

        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidValue);
    }

    @Test
    void invalidateByEmptyPasswordTest() {
        // given
        String invalidValue = null;
        SignInRequest req = createRequestWithPassword(invalidValue);

        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidValue);
    }

    @Test
    void invalidateByBlankPasswordTest() {
        // given
        String invalidValue = " ";
        SignInRequest req = createRequestWithPassword(" ");

        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(v -> v.getInvalidValue()).collect(toSet())).contains(invalidValue);
    }

    private SignInRequest createRequest() { // 6 전달 받은 필드를 요청객체를 생성
        return new SignInRequest("email@email.com", "123456a!");
    }

    private SignInRequest createRequestWithEmail(String email) { // 7
        return new SignInRequest(email, "123456a!");
    }

    private SignInRequest createRequestWithPassword(String password) { // 8
        return new SignInRequest("email@email.com", password);
    }
}
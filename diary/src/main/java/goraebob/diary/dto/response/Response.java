package goraebob.diary.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL) // null 값을 가지는 필드는, JSON 응답에 포함되지 않도록
@AllArgsConstructor(access = AccessLevel.PRIVATE) //스태틱 펙토리 메소드(4~6)를 이용하여 인스턴스를 생성하므로, 생성자의 접근 제어 레벨은 private으로 설정
@Getter
public class Response {
    private boolean success;
    private int code;
    private Result result;

    public static Response success() { // 4
        return new Response(true, 0, null);
    }

    public static <T> Response success(T data) { // 5
        return new Response(true, 0, new Success<>(data));
    }

    public static Response failure(int code, String msg) { // 6
        return new Response(false, code, new Failure(msg));
    }
}


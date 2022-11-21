package goraebob.diary.controller.sign;


import goraebob.diary.dto.response.Response;
import goraebob.diary.dto.sign.SignInRequest;
import goraebob.diary.dto.sign.SignUpRequest;
import goraebob.diary.service.sign.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static goraebob.diary.dto.response.Response.success;
// 로그인 구현 페이지 / postman 실행 -> post에서  http://localhost:8080/api/sign-in 하고 json 파일 형식으로 작성하고 send
// true시 성공!

@Api(value = "Sign Controller", tags = "Sign") // swagger 로 인해 api 문서가 작성될 것
@RestController
@RequiredArgsConstructor
public class SignController {//Response 응답 객체와 SignService를 이용해서 컨트롤러를 작성
     private final SignService signService;

     @ApiOperation(value = "회원가입", notes = "회원가입을 한다.")
     @PostMapping("/api/sign-up")
     @ResponseStatus(HttpStatus.CREATED)
     public Response signUp(@Valid @RequestBody SignUpRequest req) { // 주어진 api 오퍼레이션에 대한 설명을 입력
         signService.signUp(req);
         return success();
     }

     @ApiOperation(value = "로그인", notes = "로그인을 한다.")
     @PostMapping("/api/sign-in")
     @ResponseStatus(HttpStatus.OK)
     public Response signIn(@Valid @RequestBody SignInRequest req) { // 3
         return success(signService.signIn(req));
     }

    @ApiOperation(value = "토큰 재발급", notes = "리프레시 토큰으로 새로운 액세스 토큰을 발급 받는다.")
    @PostMapping("/api/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public Response refreshToken(@RequestHeader(value = "Authorization") String refreshToken) { //요청에 포함되는 Authorization 헤더는 이미 전역적으로 지정되도록 설정해두었기 때문에, 해당 API에 필요한 요청 헤더는 @ApiIgnore를 선언
        return success(signService.refreshToken(refreshToken));
    }
}


package goraebob.diary.factory.dto;

import goraebob.diary.dto.sign.SignInResponse;

public class SignInResponseFactory {    public static SignInResponse createSignInResponse(String accessToken, String refreshToken) {
    return new SignInResponse(accessToken, refreshToken);
    }
}

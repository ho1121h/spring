package goraebob.diary.factory.dto;

import goraebob.diary.dto.sign.RefreshTokenResponse;

public class RefreshTokenResponseFactory {
    public static RefreshTokenResponse createRefreshTokenResponse(String accessToken) {
        return new RefreshTokenResponse(accessToken);
    }
}
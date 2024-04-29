package com.stns.crudapi.advice;

public class AuthenticationResponse {

    private final String token;
    private final String refreshToken;
    private final String message;

    public AuthenticationResponse(String token, String refreshToken, String message) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getMessage() {
        return message;
    }
}

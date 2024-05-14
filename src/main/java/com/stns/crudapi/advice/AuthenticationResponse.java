package com.stns.crudapi.advice;

import java.util.List;

public class AuthenticationResponse {

    private final String token;
    private final String refreshToken;
    private final String message;

    private final List<String> roles;

    public AuthenticationResponse(String token, String refreshToken, String message, List<String> roles) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.message = message;
        this.roles = roles;
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

    public List<String> getRoles() {
        return roles;
    }
}

package com.nwabundo.mkobo.dto;

import lombok.Getter;

@Getter
public class JWTResponse {
    public JWTResponse(final String token, final String tokenPrefix, final long duration) {
        this.token = token;
        this.tokenPrefix = tokenPrefix;
        this.duration = duration;
    }

    public JWTResponse(final String token) {
        this.token = token;
    }

    private final String token;
    private String tokenPrefix = "Bearer";
    private long duration = 21;

    public void setTokenPrefix(final String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public void setDuration(final long duration) {
        this.duration = duration;
    }
}

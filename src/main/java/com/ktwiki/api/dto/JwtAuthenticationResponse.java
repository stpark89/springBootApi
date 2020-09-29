package com.ktwiki.api.dto;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 7142936709097668088L;

    @NonNull
    private String token;
    @NonNull
    private String refreshToken;
}

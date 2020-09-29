package com.ktwiki.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
@Builder
public class JwtAuthenticationRequest implements Serializable {


    private static final long serialVersionUID = -7163937626262041591L;

    @NonNull
    private String username;

    @NonNull
    private String password;


}

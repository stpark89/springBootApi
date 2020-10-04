package com.ktwiki.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
public class JwtAuthenticationRequest implements Serializable {


    private static final long serialVersionUID = -7163937626262041591L;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @JsonCreator
    public JwtAuthenticationRequest(@JsonProperty("username") String username, @JsonProperty("password") String password){
        this.username = username;
        this.password = password;
    }


}

package com.ktwiki.api.security;

import com.ktwiki.api.dto.JwtAuthenticationResponse;
import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.List;

public interface JwtUtil {

    // 토큰에서 아이디
    String getIdFromToken(String token);

    // 토큰에서 유저 아이디
    Long getUserIdFromToken(String token);

    // 토큰에서 유저명
    String getUsernameFromToken(String token);

    // 토큰에서 유저 역할
    List getRolesFromToken(String token);

    // 토큰에서 가입일
    Date getCreatedDateFromToken(String token);

    // 만기일
    Date getExpirationDateFromToken(String token);


    Claims getClaimsFromToken(String token);

    Boolean isTokenExpired(String token);

    Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset);

    JwtAuthenticationResponse generateToken(String username);

    JwtAuthenticationResponse refreshToken(String token);

    Boolean validateToken(String token);

}

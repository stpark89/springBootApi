package com.ktwiki.api.security;

import java.io.IOException;
import java.io.Serializable;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


/**
 * 인증되지 않았을 경우(비로그인)
 *  AuthenticationEntryPoint 부분에서 AuthenticationException 발생
 *
 * 비로그인 상태에서 인증실패 시,
 *  AuthenticationEntryPoint 로 핸들링되어 이곳에서 처리
 */
@Named
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -2410453506287054623L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response
                .sendError(HttpServletResponse.SC_UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }
}

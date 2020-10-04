package com.ktwiki.api.filter;

import com.google.common.base.Strings;
import com.ktwiki.api.security.JwtUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.inject.Named;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;


/**
 * JWT 담당 필터.
 * 헤더.내용.서명으로 구성되어 있음.
 *  헤더
 *    - type : " JWT"
 *    - alg : "HS256"  - 해싱 알고리즘 지정. 검증할때 사용되는 signature 부분에서 사용됨.
 */
@Log
@Named
@Component
@WebFilter
@Order(1)
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private JwtUtil jwtTokenUtil;

    @Value("${login-url:/api/sign-in}")
    private String loginUrl;

    // 어플리케이션 프로퍼티 내부 데이터 없으면 디폴트로 Authorization 으로 사용함
    @Value("${jwt.header.name:Authorization}")
    private String tokenHeader;
    @Value("${jwt.refresh.header:RefreshAuthorization}")
    private String refreshTokenHeader;
    @Value("${jwt.header.prefix:BEARER }")
    private String tokenPrefix;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtAuthenticationTokenFilter.doFilterInternal");

        if(!loginUrl.equals(request.getRequestURI())){
            String token = "";
            try{

                token = Optional.ofNullable(request.getHeader(this.tokenHeader)).orElse("");
                if (!token.toUpperCase().startsWith(this.tokenPrefix.toUpperCase())) {
                    throw new RuntimeException("illegal authorization header value passed");
                }
                token = token.substring(this.tokenPrefix.length()).trim();

            }catch (Exception e){
                logger.error(e.getMessage(), e);
                token = "";
            }

            if(!Strings.isNullOrEmpty(token)){

                if (this.jwtTokenUtil.isTokenExpired(token)) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    OutputStream os = response.getOutputStream();
                    os.flush();
                    os.close();
                    return;
                }

                if (this.jwtTokenUtil.validateToken(token)) {
                    String username = this.jwtTokenUtil.getUsernameFromToken(token);
                    List<GrantedAuthority> autorities = this.jwtTokenUtil.getRolesFromToken(token);

                    logger.info("checking Validity of JWT for user ");
                    logger.info("checking authentication for user " + username);

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, autorities);
                        authentication.setDetails((new WebAuthenticationDetailsSource().buildDetails(request)));

                        log.info("authenticated user " + username + " , setting security context");
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }

        filterChain.doFilter(request,response);
    }
}

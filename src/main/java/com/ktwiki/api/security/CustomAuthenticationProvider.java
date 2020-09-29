package com.ktwiki.api.security;

import com.ktwiki.api.service.JwtUserDetailsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Log
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    // 비밀번호 암호화
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("authenticate.authenticate - id : " + authentication.getName() + " // pw : " +authentication.getCredentials().toString());
        JwtUser user = jwtUserDetailsService.loadUserByUsername(authentication.getName());
        log.info(user.toString());

        // 디비 조회해서 비밀번호 검증 필요함. match
        log.info(passwordEncoder.encode(user.getPassword().toString()));


        return new UsernamePasswordAuthenticationToken(user.getUsername(),passwordEncoder.encode(user.getPassword().toString()), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}

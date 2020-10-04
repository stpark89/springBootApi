package com.ktwiki.api.controller;

import com.ktwiki.api.dto.ApiResponse;
import com.ktwiki.api.dto.JwtAuthenticationRequest;
import com.ktwiki.api.security.JwtUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@Log
@RequestMapping(value="/api")
@RestController
public class DataController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("sign-in")
    public ResponseEntity signIn(@RequestBody @Validated JwtAuthenticationRequest request) {

        log.info("처음 로그인 탔을 경우 ---");

        try {
            final Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            log.info("DataController --- Sigin-in");
            log.info("DataControler --- : "+authentication.getName() + " // " + authentication.getCredentials() + " // " +authentication.getAuthorities());
            log.info("DataController --- Sigin-in");


            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(e));
        }
        return ResponseEntity.ok(ApiResponse.success(this.jwtUtil
                .generateToken(request.getUsername())));
    }

}

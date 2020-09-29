package com.ktwiki.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PlainUserService {

    private PasswordEncoder passwordEncoder;

    @Inject
    private void setPasswordEncoder(PasswordEncoder bcryptPasswordEncoder) {
        this.passwordEncoder = bcryptPasswordEncoder;
    }
}

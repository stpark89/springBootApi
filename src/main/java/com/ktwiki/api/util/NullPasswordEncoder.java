package com.ktwiki.api.util;

import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log
public class NullPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        log.warning("null password encoder used! don`t use this!");
        return String.valueOf(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        log.warning("null password encoder used! don`t use this!");
        return rawPassword.equals(encodedPassword);
    }

}

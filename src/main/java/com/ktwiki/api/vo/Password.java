package com.ktwiki.api.vo;

import com.ktwiki.api.util.NullPasswordEncoder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class Password {

    private PasswordEncoder passwordEncoder = new NullPasswordEncoder();

    private String password;

    public static Password create(String value, PasswordEncoder passwordEncoder) {
        Password next = new Password();
        next.passwordEncoder = passwordEncoder;
        next.setPassword(value);
        return next;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String value) {
        this.password = this.passwordEncoder.encode(value);
    }


}

package com.ktwiki.api.service;

import com.ktwiki.api.security.JwtUser;
import com.ktwiki.api.vo.NautilusUser;
import com.ktwiki.api.vo.PlainUser;
import lombok.extern.java.Log;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
@Named
public class JwtUserDetailsService implements UserDetailsService {

    private PlainUserService plainUserService;

    @Inject
    private void setPlainUserService(PlainUserService plainUserService) {
        this.plainUserService = plainUserService;
    }

    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("JwtUserDetailsService.loadUserByUserName ----------- " + username);

        List<String> roleList = new ArrayList<>();
        roleList.add("USER");

        JwtUser returnVo = new JwtUser("hongGilDong", username, "1004", makeGrantedAuthority(roleList), true );
        return returnVo;
    }

    private List<GrantedAuthority> makeGrantedAuthority(List<String> roles){
        List<GrantedAuthority> list = new ArrayList<>();
        roles.forEach(role -> list.add(new SimpleGrantedAuthority("ROLE_" + role)));
        return list;
    }

}

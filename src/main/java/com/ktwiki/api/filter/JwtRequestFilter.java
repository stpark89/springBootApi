/**
package com.ktwiki.api.filter;

import com.google.common.base.Strings;
import com.ktwiki.api.security.JwtUtil;
import com.ktwiki.api.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
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
        filterChain.doFilter(request,response);
    }
}
**/
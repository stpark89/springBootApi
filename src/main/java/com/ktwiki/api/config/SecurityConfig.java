package com.ktwiki.api.config;

import com.ktwiki.api.filter.JwtAuthenticationTokenFilter;
import com.ktwiki.api.security.CustomAuthenticationProvider;
import com.ktwiki.api.security.JwtAuthenticationEntryPoint;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.inject.Inject;
import javax.inject.Named;

@Log
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private JwtAuthenticationEntryPoint unauthorizedHandler;

    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    // 에러 발생시
    @Inject
    public void setUnauthorizedHandler(JwtAuthenticationEntryPoint unauthorizedHandler) {
        log.info("SecurityConfig.setUnauthorizedHandler");
        this.unauthorizedHandler = unauthorizedHandler;
    }

    // 비밀번호 암호화
    @Inject
    public void setPasswordEncoder(PasswordEncoder bcryptPasswordEncoder) {
        log.info("SecurityConfig.setPasswordEncoder");
        this.passwordEncoder = bcryptPasswordEncoder;
    }

    // UserDetailService
    @Inject
    public void setUserDetailsService(UserDetailsService jwtUserDetailsService) {
        log.info("SecurityConfig.setUserDetailsService");
        this.userDetailsService = jwtUserDetailsService;
    }
    @Inject
    public void setJwtAuthenticationTokenFilter(
            JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter) {
        log.info("SecurityConfig.setJwtAuthenticationTokenFilter");
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    }

    // AuthenticationManagerBuilder : 스프링 시큐리티의 인증에 대한 지원을 설정
    @Inject
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
        log.info("SecurityConfig.configureAuthentication");

        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(this.passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs","/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .httpBasic().disable()
                .cors().and()
                .csrf().disable()
                .headers().cacheControl().disable().frameOptions().disable().and()                              // 캐시 처리
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()                        // 에러 처리
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()               // (JWT) 세션 처리 스프링시큐리티가 생성하지도않고 기존것을 사용하지도 않음
                   .authorizeRequests()
                       .mvcMatchers("/api/sign-in").permitAll()
                       .antMatchers("/swagger-ui.html").permitAll()
                       .antMatchers("/h2-console/**").permitAll()
                       .antMatchers("/actuator/**").permitAll()
                       .antMatchers("/api/connectionTest").permitAll()
                       .anyRequest().authenticated()
                       .and()
                            .formLogin().disable();                                                             // Security Login Form 미사용


        httpSecurity.addFilterBefore(this.jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class); // 커스텀한 시큐리티 필터 추가
    }

    /**
     * Manager은 쉽게 말해서 공장 안에서 작업 처리를 지시하는 매니저라 생각하자.
     * Provider은 Manager가 시켜서 일하는 작업자라 생각하자.
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Named
    @Override
    public AuthenticationManager authenticationManagerBean() throws  Exception{
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("SecurityConfig.configure(AuthenticationManagerBuilder auth)");
        try {
            auth.authenticationProvider(authenticationProvider);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.stamptour.finalstamp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
public class SecurityConfig {

    @Bean
    public DefaultCookieSerializer cookieSerializer() {

        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("JSESSIONID"); // 쿠키 이름 설정
        serializer.setDomainName("tmdstamptour.netlify.app"); // 도메인 설정
        serializer.setCookiePath("/"); // 경로 설정
        serializer.setUseHttpOnlyCookie(false); // HttpOnly 설정
        serializer.setUseSecureCookie(true); // Secure 설정
        serializer.setSameSite("None"); // SameSite 설정
        return serializer;
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver(DefaultCookieSerializer cookieSerializer) {
        // CookieHttpSessionIdResolver 를 생성하고 DefaultCookieSerializer 를 설정합니다.
        CookieHttpSessionIdResolver resolver = new CookieHttpSessionIdResolver();
        resolver.setCookieSerializer(cookieSerializer);
        return resolver;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 비활성화
                .csrf(csrf -> csrf.disable())

                // 세션 관리 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                        .expiredUrl("/api/login")
                )

                // URL 에 따른 권한 부여 정책 설정
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

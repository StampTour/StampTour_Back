package com.stamptour.finalstamp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class WebConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 인증 정보 허용

        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:63342",
                "http://localhost:3000",
                "http://localhost:8080",
                "https://tmdstamptour.netlify.app",
                "https://tmdstamptour.netlify.app/Gwangjin",
                "https://tmdstamptour.netlify.app/"
        ));

        config.addAllowedHeader(CorsConfiguration.ALL);

        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.POST);

        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}

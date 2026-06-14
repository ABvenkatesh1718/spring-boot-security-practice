package com.venkatesh.main.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class MyConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(
                        auth->auth
                                .requestMatchers("/v1/public/**").permitAll()
                                .anyRequest().authenticated())
                .httpBasic(withDefaults());
        return http.build();
    }

}

package com.platzi.pizza.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BasicAuth {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                customized -> {
                    customized
                            .requestMatchers(HttpMethod.GET,"/api/pizzas/**").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/api/pizzas/**").denyAll()
                            .anyRequest()
                            .authenticated();
                }
        );
        http.csrf().disable();
        http.httpBasic(
                Customizer.withDefaults()
        );
        return http.build();
    }
}

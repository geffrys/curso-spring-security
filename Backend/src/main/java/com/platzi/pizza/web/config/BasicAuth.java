package com.platzi.pizza.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BasicAuth {
    private final String PIZZAS_ENDPOINT = "/api/pizzas/**";
    private final String ORDERS_ENDPOINT = "/api/orders/**";
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                customized -> {
                    customized
                            .requestMatchers(HttpMethod.GET, PIZZAS_ENDPOINT).hasAnyRole("ADMIN","CUSTOMER")
                            .requestMatchers(HttpMethod.POST, PIZZAS_ENDPOINT).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, PIZZAS_ENDPOINT).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, ORDERS_ENDPOINT).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, ORDERS_ENDPOINT ).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, ORDERS_ENDPOINT).hasRole("ADMIN")
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

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        UserDetails customer = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("customer"))
//                .roles("CUSTOMER")
//                .build();
//        return new InMemoryUserDetailsManager(admin, customer);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

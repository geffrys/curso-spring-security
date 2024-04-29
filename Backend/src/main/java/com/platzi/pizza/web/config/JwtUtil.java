package com.platzi.pizza.web.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private static final String secret = "my_s3cr3t";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(secret);

    public String create(String username){
        return JWT.create()
                .withSubject(username)
                .withIssuer("SERVER")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
                .sign(ALGORITHM);
    }


}

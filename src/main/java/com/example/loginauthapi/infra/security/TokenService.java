package com.example.loginauthapi.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.loginauthapi.domain.user.User;
import com.example.loginauthapi.dto.TokenData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(user.getEmail())
                    .withClaim("app", user.getDescriptionApp())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while authenticating");
        }
    }

    public TokenData validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var decoded = JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token);

            String email = decoded.getSubject();
            String app = decoded.getClaim("app").asString();

            return new TokenData(email, app);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}

package com.forumhub.security;

import com.forumhub.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}") // em milissegundos
    private Long expiration;

    public String gerarToken(Usuario usuario) {
        Date hoje = new Date();
        Date exp = new Date(hoje.getTime() + expiration);

        return JWT.create()
                .withIssuer("forumhub-api")
                .withSubject(usuario.getId().toString())
                .withIssuedAt(hoje)
                .withExpiresAt(exp)
                .sign(Algorithm.HMAC256(secret));
    }

    public Long getUserId(String token) {
        return Long.parseLong(
                JWT.require(Algorithm.HMAC256(secret))
                        .withIssuer("forumhub-api")
                        .build()
                        .verify(token)
                        .getSubject()
        );
    }
}

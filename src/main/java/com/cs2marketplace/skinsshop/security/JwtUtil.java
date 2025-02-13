package com.cs2marketplace.skinsshop.security;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "jjhqj1xc8ee0kxcrpst9065v2qnqwpi4";
    private final long EXPIRATION_TIME = 86400000;

    // Obtém a chave secreta para assinar o JWT
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Gera o Token JWT
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Definindo o email como o "subject" (usuário)
                .setIssuedAt(new Date()) // Definindo a data de criação
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Definindo tempo de expiração
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Assinando com a chave secreta e algoritmo
                .compact(); // Compacta o JWT
    }

    // Valida o Token JWT
    public boolean validateToken(String token) {
        try {

            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // Extrai o email do Token JWT
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) //
                .build()
                .parseClaimsJws(token) //
                .getBody()
                .getSubject(); //
    }
}

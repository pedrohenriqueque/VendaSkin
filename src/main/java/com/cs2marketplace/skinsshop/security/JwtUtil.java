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

    private final String SECRET_KEY = ">?NPq&.GX8-Z5Senay\"=c:kxRtCAvmM\n" +
            "bx=w}t'`Nm$]/GB3WP:Rc&8D^Sz!frLT\n" +
            "wRgz/;8KhcmdUP3]FxSs*&H>^B2t$JQX\n" +
            "K.A)p`]C!e57V'Rh4rv[%9L>Ya#kJU<w\n" +
            "ad25K=B3eX/j!7z6Vt<p%A-}n.:J+D${";
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

package com.example.demo.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.stereotype.Component;

@Component
public class JWTTokenProvider {

    private static final String key = "secretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecret";

    public boolean ValidateToken(String jwt) {

        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key.getBytes())).build().parseClaimsJws(jwt);
            return true;
        } catch (ExpiredJwtException e) {
            System.err.println("Expired token");
        } catch (UnsupportedJwtException e) {
            System.err.println("Unsupported jwt token");
        } catch (MalformedJwtException e) {
            System.err.println("Malformed Exception");
        } catch (SignatureException e) {
            System.err.println("Signature Exception");
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument exception");
        } catch (WeakKeyException e) {
            System.err.println("weak Key");
        }
        return false;
    }

}

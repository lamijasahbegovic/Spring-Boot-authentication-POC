package com.lamija.authenticationapp.security.services;

import com.lamija.authenticationapp.authentication.exceptions.AuthenticationExpiredException;
import com.lamija.authenticationapp.authentication.exceptions.AuthenticationInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class TokenUtilityService {

    protected String create(String email, long expiration, String secret) {
        Date now = new Date();
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date((now.getTime() + expiration)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Claims extractAllClaims(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private <T> T extractSpecificClaim(
            String token, Function<Claims, T> claimsTFunction, String secret) {
        try {
            return claimsTFunction.apply(extractAllClaims(token, secret));
        } catch (ExpiredJwtException exception) {
            throw new AuthenticationExpiredException();
        } catch (JwtException exception) {
            throw new AuthenticationInvalidException();
        }
    }

    protected String extractSubject(String token, String secret) {
        return extractSpecificClaim(token, Claims::getSubject, secret);
    }
}

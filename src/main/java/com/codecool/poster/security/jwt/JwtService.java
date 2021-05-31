package com.codecool.poster.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtService {
    @Value("${security.jwt.token.secret.key:secureSecretKey}")
    private String secret;

    @Value("${security.jwt.token.expire-length:604800000}")
    private int validForMilliseconds;

    private final String rolesFieldName = "roles";
    private final String idFieldName = "id";

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(long id, String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(idFieldName, id);
        claims.put(rolesFieldName, roles);
        Date now = new Date();
        Date validUntil = new Date(now.getTime() + validForMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validUntil)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Might change with frontend token storage
    public String getTokenFromRequest(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

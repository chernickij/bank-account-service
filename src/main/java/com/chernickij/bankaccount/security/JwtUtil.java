package com.chernickij.bankaccount.security;

import com.chernickij.bankaccount.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long expiration;

    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(final String token, final Function<Claims, T> function) {
        final Claims claims = extractAllClaims(token);
        return function.apply(claims);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isNotTokenValid(final String token, final UserDetails userDetails) {
        return !isTokenValid(token, userDetails);
    }

    public boolean isTokenValid(final String token, final UserDetails userDetails) {
        return (!isTokenExpired(token));
    }

    private boolean isTokenExpired(final String token) {
        return getExpiryDate(token).before(new Date());
    }

    private Date getExpiryDate(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(final UserDetails userDetails, final String email) {
        final Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("email", email);
            claims.put("role", customUserDetails.getRole());
        }
        return generateToken(claims, userDetails, email);
    }

    public String generateToken(final Map<String, Object> extraClaims, final UserDetails userDetails, final String email) {
        return buildToken(extraClaims, userDetails, email);
    }

    private String buildToken(final Map<String, Object> extraClaims, final UserDetails userDetails, final String email) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key getSignInKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

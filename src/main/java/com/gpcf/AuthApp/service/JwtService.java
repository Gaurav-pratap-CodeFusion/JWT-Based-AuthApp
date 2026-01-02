package com.gpcf.AuthApp.service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    public String generateJwt(UserDetails user){

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());

        List<String> collect = new ArrayList<>();

        for (GrantedAuthority role : user.getAuthorities()) {
            String authority = role.getAuthority();
            collect.add(authority);
        }


        claims.put("roles",collect);


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*2))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token){
       return Jwts.parserBuilder()
               .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return (List<String>) claims.get("roles");

    }


    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(UserDetails us, String token){
        return extractUsername(token).equals(us.getUsername()) && !isTokenExpired(token);
    }


}

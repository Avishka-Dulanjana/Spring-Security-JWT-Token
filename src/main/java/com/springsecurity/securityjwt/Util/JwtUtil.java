package com.springsecurity.securityjwt.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "12345678"; // token id
    private static final int TOLKEN_VALIDITY = 3600 * 5; // Token validity time

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getUserNameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){  // DATA/PAYLOAD Checking and ( USERNAME = TOKEN USERNAME ) CLAIMS CHECKING
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = getUserNameFromToken(token);
//        if(username.equals(userDetails.getUsername())){
//            return true;
//        }else{
//            return false;
//        }
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); //IF condition in single line :: TOKEN VALIDITY CHECK
    }

    public boolean isTokenExpired(String token){ // TOKEN VALIDITY
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date()); // Today Date != Expire Date
    }

    public String generateToken(UserDetails userDetails){    // NEW Token Generation
        Map<String , Object> claims = new HashMap<>();

        return  Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOLKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }
}

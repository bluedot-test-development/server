package develop.bluedot.server.entity.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {

    private Key key;

    public JwtUtil(String secret){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

//   토큰생성
    public String createToken(long userId, String name){

        String token = Jwts.builder()
                .claim("userId", userId)
                .claim("name", name)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();


        return token;


//        JwtBuilder builder = Jwts.builder()
//                .claim("userId", userId)
//                .claim("name", name);
//
//        return builder
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//
////        return "header.payload.signature";
    }


    public Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
}

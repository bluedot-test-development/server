package develop.bluedot.server.entity.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;

@Slf4j
public class JwtUtil {

    private Key key;

    public JwtUtil(String secret){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

//   토큰생성
    public String createToken(long userId, String name){


        return Jwts.builder()
                .claim("userId", userId)
                .claim("name", name)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();


//        JwtBuilder builder = Jwts.builder()
//                .claim("userId", userId)
//                .claim("name", name);
//
//        return builder
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//
    }


    public Claims getClaims(String token){
        log.info(""+ token);
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();


    }
}

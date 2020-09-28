package develop.bluedot.server.entity.utils;

import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class JwtUtilTest {

    private static final String SECRET = "12345678901234567890123456789012";

    private JwtUtil jwtUtil;

    @Before
    public void setUp(){
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    public void createToken(){
        String token = jwtUtil.createToken(39L,"test");

        assertThat(token,containsString("."));
    }

    @Test
    public void getClaims(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjM4LCJuYW1lIjoidGVzdCJ9.XbqtJ3z_d6_JW3X4f-L_oX0PT_Ni9wPLk8sSsa3FU_s";
        Claims claims =jwtUtil.getClaims(token);

        assertThat(claims.get("userId",Long.class),is(38));
        assertThat(claims.get("name"),is("test"));
    }


}
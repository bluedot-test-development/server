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
        String token = jwtUtil.createToken(55L,"jon2g");

        assertThat(token,containsString("."));
    }
//
    @Test
    public void getClaims(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjU1LCJuYW1lIjoiam9uMmcifQ.GN86nyRsRlJX4REtTMlbp1GSZFEngTn0lfvKp2W2XVE";
        Claims claims =jwtUtil.getClaims(token);

        assertThat(claims.get("userId",Long.class),is(55L));
        assertThat(claims.get("name"),is("jon2g"));
    }


}
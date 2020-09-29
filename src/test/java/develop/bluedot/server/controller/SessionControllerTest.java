package develop.bluedot.server.controller;

import develop.bluedot.server.application.EmailNotExistedException;
import develop.bluedot.server.application.PasswordWrongException;
import develop.bluedot.server.entity.User;
import develop.bluedot.server.entity.utils.JwtUtil;
import develop.bluedot.server.service.SessionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private SessionService sessionService;


    @Test
    public void create() throws Exception{
        //given

        String name = "test";
        Long id = 39L;
        String email = "test@example.com";
        String password = "test";

        //when
        User mockUser = User.builder().id(id).name(name).build();

        given(sessionService.authenticate(email, password)).willReturn(mockUser);

        given(jwtUtil.createToken(id, name))
                .willReturn("header.payload.signature");

        //then
        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@example.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/session"))
                .andExpect(content().string(containsString("{\"accessToken\":\"header.payload.signature\"}")));

        verify(sessionService).authenticate(eq(email), eq(password));
     }


    //SessionErrorAdvice Test
    @Test
    public void createWithNotExistedEmail() throws Exception {

        given(sessionService.authenticate("x@example.com", "test"))
                .willThrow(EmailNotExistedException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"x@example.com\",\"password\":\"test\"}"))
                .andExpect(status().isBadRequest());

        verify(sessionService).authenticate(eq("x@example.com"), eq("test"));
    }

    //SessionErrorAdvice Test
    @Test
    public void createWithWrongPassword() throws Exception {

        given(sessionService.authenticate("tester@example.com", "x"))
                .willThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\",\"password\":\"x\"}"))
                .andExpect(status().isBadRequest());

        verify(sessionService).authenticate(eq("tester@example.com"), eq("x"));
    }

}







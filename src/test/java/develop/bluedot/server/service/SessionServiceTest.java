package develop.bluedot.server.service;

import develop.bluedot.server.application.EmailNotExistedException;
import develop.bluedot.server.application.PasswordWrongException;
import develop.bluedot.server.entity.User;
import develop.bluedot.server.entity.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class SessionServiceTest {

    private SessionService sessionService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        sessionService = new SessionService(userRepository, passwordEncoder);
    }





    //  인증
    @Test
    public void authenticateWithValidAttributes() {
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder()
                .email(email).build();
        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(),any())).willReturn(true);

        User user = sessionService.authenticate(email, password);

        assertThat(user.getEmail(),is(email));
    }

    //  이메일 존재 x
    @Test
    public void authenticateWithNotExistedEmail() {

        String email = "x@example.com";
        String password = "test";

        given(userRepository.findByEmail(email))
                .willReturn(Optional.empty());


        assertThatThrownBy(() -> {
            sessionService.authenticate(email,password);
        }).isInstanceOf(EmailNotExistedException.class);

    }

    //  패스워드 오류
    @Test
    public void authenticateWithWrongPassword() {
        String email = "tester@example.com";
        String password = "x";

        User mockUser = User.builder().email(email).build();

        given(userRepository.findByEmail(email))
                .willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(false);

        assertThatThrownBy(() -> {
            sessionService.authenticate(email, password);
        }).isInstanceOf(PasswordWrongException.class);
    }
}
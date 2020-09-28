package develop.bluedot.server.controller;


import develop.bluedot.server.application.SessionRequestDto;
import develop.bluedot.server.application.SessionResponseDto;
import develop.bluedot.server.entity.User;
import develop.bluedot.server.entity.utils.JwtUtil;
import develop.bluedot.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
public class SessionController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(
            @RequestBody SessionRequestDto resource
    ) throws URISyntaxException {


        String email = resource.getEmail();
        String password = resource.getPassword();

        User user = userService.authenticate(email,password);

        String accessToken = jwtUtil.createToken(user.getId(),user.getName());

        String url = "/session";

        return ResponseEntity.created(new URI(url)).body(
                SessionResponseDto.builder()
                .accessToken(accessToken)
                .build());
    }

}

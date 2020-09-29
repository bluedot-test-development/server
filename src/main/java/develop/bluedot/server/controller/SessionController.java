package develop.bluedot.server.controller;


import develop.bluedot.server.network.request.SessionApiRequest;
import develop.bluedot.server.network.response.SessionApiResponse;
import develop.bluedot.server.entity.User;
import develop.bluedot.server.entity.utils.JwtUtil;
import develop.bluedot.server.service.SessionService;
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
    private SessionService sessionService;


    //TODO : 반환값 token으로 변경 (created url해도 되나?)
    @PostMapping("/session")
    public ResponseEntity<SessionApiResponse> create(
            @RequestBody SessionApiRequest resource
    ) throws URISyntaxException {


        String email = resource.getEmail();
        String password = resource.getPassword();

        User user = sessionService.authenticate(email,password);

        String accessToken = jwtUtil.createToken(user.getId(),user.getName());

        String url = "/session";


        return ResponseEntity.created(new URI(url)).body(
                SessionApiResponse.builder()
                .accessToken(accessToken)
                .build());
    }

}

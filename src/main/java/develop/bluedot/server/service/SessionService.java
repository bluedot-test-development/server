package develop.bluedot.server.service;

import develop.bluedot.server.application.EmailNotExistedException;
import develop.bluedot.server.entity.User;
import develop.bluedot.server.entity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SessionService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SessionService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 인증
     */
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotExistedException(email));
        //TODO : 패스워드 예외처리
//        if(!passwordEncoder.matches(password, user.getPassword())){
//            throw new PasswordWrongException();
//        }
        return user;
    }
}

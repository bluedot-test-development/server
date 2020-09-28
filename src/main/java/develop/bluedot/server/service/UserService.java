package develop.bluedot.server.service;

import develop.bluedot.server.application.EmailExistedException;
import develop.bluedot.server.application.EmailNotExistedException;
import develop.bluedot.server.application.PasswordWrongException;
import develop.bluedot.server.entity.User;
import develop.bluedot.server.entity.repository.UserRepository;
import develop.bluedot.server.network.Header;
import develop.bluedot.server.network.Pagination;
import develop.bluedot.server.network.request.UserApiRequest;
import develop.bluedot.server.network.response.UserApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
<<<<<<< Updated upstream
import org.springframework.security.core.parameters.P;
=======
>>>>>>> Stashed changes
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService extends BaseService<UserApiRequest,UserApiResponse,User> {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 아이디 생성
     */
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {
        UserApiRequest userData = request.getData();

        //존재하는 이메일 예외처리
        Optional<User> existed = userRepository.findByEmail(userData.getEmail());
        if(existed.isPresent()){
            throw new EmailExistedException(userData.getEmail());
        }

         String encodedPassword = passwordEncoder.encode(userData.getPassword());
        log.info("" + encodedPassword);

        User newUser = User.builder()
                .email(userData.getEmail())
                .password(encodedPassword)
                .genre(userData.getGenre())
                .name(userData.getName())
                .build();

        User returnData = baseRepository.save(newUser);

        return response(returnData);
    }



    /**
     * 페이징처리

    public Header<List<UserApiResponse>> search(Pageable pageable){
        Page<User> users = baseRepository.findAll(pageable);

        List<UserApiResponse> returnData = users.stream().map(user->
                responseForPageable(user)
        ).collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(users.getTotalPages())
                .currentPage(users.getNumber())
                .totalElements(users.getTotalElements())
                .currentElements(users.getNumberOfElements())
                .build();

        return Header.OK(returnData,pagination);
    }
     */


    @Override
    public Header<UserApiResponse> read(Long id) {
        Optional<User> findUser = baseRepository.findById(id);

        return findUser.map(user ->
                response(user)
        ).orElseGet(() -> Header.ERROR("에러"));

    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {

        Optional<User> findUser = baseRepository.findById(id);

        return findUser.map(user->{
            baseRepository.delete(user);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("에러"));

    }


//    인증
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotExistedException(email));


        //TODO : 패스워드 예외처리
//        if(!passwordEncoder.matches(password, user.getPassword())){
//            throw new PasswordWrongException();
//        }
        return user;
    }


    public UserApiResponse responseForPageable(User user){
        return null;
    }

    public Header<UserApiResponse> response(User user) {

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .genre(user.getGenre())
                .build();


        return Header.OK(userApiResponse);
    }


}
